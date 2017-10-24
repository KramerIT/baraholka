package com.kramar.jms_to_sql.jsm;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.jms.*;
import java.util.Date;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

@Slf4j
@Component
public class JmsProducer extends Thread implements AutoCloseable {

    private ActiveMQConnectionFactory connectionFactory;
    private Connection connection;
    private MessageProducer producer;
    private Session session;
    private Queue<String> messagesQueue;

    @Value("${jms.producer.enabled:true}")
    private boolean active;
    @Value("${jms.producer.url}")
    private String url;
    @Value("${jms.producer.queue}")
    private String queue;

    private MessageProducer initialize() throws JMSException {
        connectionFactory = new ActiveMQConnectionFactory(url);
        messagesQueue = new PriorityBlockingQueue<>();
        connection = connectionFactory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        final Destination dest = session.createQueue(queue);
        return session.createProducer(dest);
    }

    public void send(String line) {
        messagesQueue.add(line);
    }

    @PostConstruct
    public void init() {
        try {
            log.info("Init producer...");
            producer = initialize();
            log.info("Producer successfully initialized");
        } catch (JMSException e) {
            e.printStackTrace();
        }
        this.start();
    }

    @Override
    public void run() {
        try {
            while (active) {
                try {
                    String text = null;
                    while (active && (text = messagesQueue.poll()) != null) {
                        Message msg = session.createTextMessage(text);
                        msg.setObjectProperty("Created", (new Date()).toString());
                        producer.send(msg);
                        log.debug("Message " + msg.getJMSMessageID() + " was sent");
                    }
                } catch (JMSException e) {
                    e.printStackTrace();
                    session.close();
                    connection.close();
                    producer = initialize(); // trying to reconnect
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void close() {
        active = false;
        if (connection != null) {
            try {
                connection.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

}
