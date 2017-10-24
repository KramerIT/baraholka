package com.kramar.jms_to_sql.jsm;

import com.kramar.jms_to_sql.sql.CustomJdbcCaller;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.jms.*;
import java.util.List;

@Slf4j
@Component
public class JmsConsumer implements MessageListener, AutoCloseable {

    private Connection connection;
    private Session session;

    @Value("${jms.consumer.url}")
    private String url;
    @Value("${jms.consumer.queue}")
    private String queue;

    @Autowired
    private CustomJdbcCaller customJdbcCaller;
    @Autowired
    private JmsProducer jmsProducer;

    @PostConstruct
    public void init() throws JMSException {
        log.info("Init consumer...");

        final ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(url);
        connection = activeMQConnectionFactory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        final Destination dest = session.createQueue(queue);
        final MessageConsumer consumer = session.createConsumer(dest);
        consumer.setMessageListener(this);

        log.info("Consumer successfully initialized");

    }

    @Override
    public void onMessage(Message msg) {
        if (msg instanceof TextMessage) {
            try {
                // used for hardcore sql
                StringBuilder text = new StringBuilder(((TextMessage) msg).getText());
                List<String[]> result = customJdbcCaller.getCustomResult(text.toString());
                for(String[] s : result) {
                    text.append("\n").append( String.join(" ", s));
                }
                jmsProducer.send(text.toString());
                log.debug("Received message: " + text);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else log.debug("Received message: " + msg.getClass().getName());
    }

    public void close() throws Exception {
        try {
            if (session != null) {
                session.close();
            }
        } catch (JMSException jmsEx) {
            jmsEx.printStackTrace();
        }
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
