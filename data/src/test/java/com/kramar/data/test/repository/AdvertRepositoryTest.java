package com.kramar.data.test.repository;

import com.kramar.data.dbo.AdvertDbo;
import com.kramar.data.dbo.UserDbo;
import com.kramar.data.repository.AdvertRepository;
import com.kramar.data.repository.UserRepository;
import com.kramar.data.type.AdvertStatus;
import com.kramar.data.type.AdvertType;
import com.kramar.data.type.CurrencyType;
import com.kramar.data.type.UserStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;
import java.util.UUID;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class AdvertRepositoryTest {

    @Autowired
    private AdvertRepository advertRepository;
    @Autowired
    private UserRepository userRepository;

    private AdvertDbo advertDbo;
    private UserDbo userDbo;

    private static final String STRING = "String";
    private static final String DESCRIPTION = "Description";
    private static final UUID INVALID_ID = UUID.fromString("00000000-0000-0000-0000-000000000001");
    private UUID ADVERT_ID;

    @Before
    public void setUp() {
        userDbo = new UserDbo();
        userDbo.setEmail(STRING);
        userDbo.setUserName(STRING);
        userDbo.setUserSurname(STRING);
        userDbo.setStatus(UserStatus.ACTIVE);
        userDbo = userRepository.save(userDbo);

        advertDbo = new AdvertDbo();
        advertDbo.setAdvertStatus(AdvertStatus.ACTIVE);
        advertDbo.setAdvertType(AdvertType.SALE);
        advertDbo.setHeadLine(STRING);
        advertDbo.setPrice(99.99);
        advertDbo.setCurrencyType(CurrencyType.USD);
        advertDbo.setDescription(DESCRIPTION);
        advertDbo.setOwner(userDbo);
        advertDbo = advertRepository.save(advertDbo);
        ADVERT_ID = advertDbo.getId();
    }

    @After
    public void del() {
        advertRepository.delete(advertDbo.getId());
        userRepository.delete(userDbo.getId());
    }

    @Test
    public void findByHeadLineTest() {
        Optional<AdvertDbo> byHeadLine = advertRepository.findByHeadLine(advertDbo.getHeadLine());
        assertTrue(byHeadLine.isPresent());
        assertTrue(byHeadLine.get().getHeadLine().equals(STRING));

        byHeadLine = advertRepository.findByHeadLine("abra_cadabra");
        assertFalse(byHeadLine.isPresent());
    }

    @Test
    public void findByIdTest() {
        Optional<AdvertDbo> byId = advertRepository.findById(ADVERT_ID);
        assertTrue(byId.isPresent());
        assertTrue(byId.get().getHeadLine().equals(STRING));

        byId = advertRepository.findById(INVALID_ID);
        assertFalse(byId.isPresent());
    }

    @Test
    public void findByOwnerTest() {
        Page<AdvertDbo> byOwner = advertRepository.findByOwner(userDbo, null);
        assertFalse(byOwner.getContent().isEmpty());
        assertTrue(byOwner.getContent().get(0).getHeadLine().equals(STRING));
        UserDbo userDbo = new UserDbo();
        userDbo = userRepository.save(userDbo);
        byOwner = advertRepository.findByOwner(userDbo, null);
        assertTrue(byOwner.getContent().isEmpty());
    }

}
