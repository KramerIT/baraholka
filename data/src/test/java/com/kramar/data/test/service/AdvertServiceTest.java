package com.kramar.data.test.service;

import com.kramar.data.converter.AdvertConverter;
import com.kramar.data.converter.UserConverter;
import com.kramar.data.dbo.AdvertDbo;
import com.kramar.data.dbo.UserDbo;
import com.kramar.data.dto.AdvertDto;
import com.kramar.data.repository.AdvertRepository;
import com.kramar.data.repository.ImageRepository;
import com.kramar.data.repository.UserRepository;
import com.kramar.data.service.AdvertService;
import com.kramar.data.service.AuthenticationService;
import com.kramar.data.service.ImageService;
import com.kramar.data.service.UserService;
import com.kramar.data.service.impl.AdvertServiceImpl;
import com.kramar.data.service.impl.ImageServiceImpl;
import com.kramar.data.service.impl.UserServiceImpl;
import com.kramar.data.test.utils.TestUtils;
import com.kramar.data.type.AdvertStatus;
import com.kramar.data.type.AdvertType;
import com.kramar.data.type.CurrencyType;
import com.kramar.data.type.UserStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.persistence.EntityManagerFactory;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = AdvertServiceTest.AdvertServiceTestContextConfiguration.class)
public class AdvertServiceTest {

    @Autowired
    private AdvertConverter advertConverter;
    @Autowired
    private AdvertRepository advertRepository;
    @Autowired
    private AdvertService advertService;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationService authenticationService;

    private AdvertDbo advertDbo;
    private UserDbo userDbo;
    private List<AdvertDbo> advertDbos;
    private List<AdvertDto> advertDtos;

    private static final String STRING = "String";
    private static final String DESCRIPTION = "Description";
    private static final UUID ID = UUID.fromString("00000000-0000-0000-0000-000000000000");
    private static final UUID INVALID_ID = UUID.fromString("00000000-0000-0000-0000-000000000001");

    @Before
    public void setUp() {
        userDbo = TestUtils.createUser();
        advertDbo = TestUtils.createAdvert(userDbo);
        advertDbos = Arrays.asList(advertDbo, TestUtils.createAdvert(userDbo), TestUtils.createAdvert(userDbo), TestUtils.createAdvert(userDbo));
        advertDtos = advertDbos.stream().map(advertConverter::transform).collect(Collectors.toList());


//getCurrentUser
        Mockito.when(userRepository.getById(any(UUID.class))).thenReturn(userDbo);
        Mockito.when(authenticationService.getCurrentUser()).thenReturn(userDbo);

//getAllAdvertsByPageable
        Mockito.when(advertRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl(advertDbos));

//getAllAdvertsByOwner
        Mockito.when(advertRepository.findByOwner(any(), any())).thenReturn(new PageImpl(advertDbos));
    }

    @Test
    public void getAllAdvertsTest() {
        ArgumentCaptor<Pageable> pageArgument = ArgumentCaptor.forClass(Pageable.class);
        Page<AdvertDto> allAdverts = advertService.getAllAdverts(pageArgument.capture());
        assertNotNull(allAdverts);
        assertTrue(advertDtos.size() == allAdverts.getContent().size());
    }

    // TODO: 20/09/17 not work! ((
    @Test
    public void getAllAdvertsByUserTest() {
        ArgumentCaptor<Pageable> pageArgument = ArgumentCaptor.forClass(Pageable.class);
        Page<AdvertDto> allAdverts = advertService.getAllAdvertsByUser(pageArgument.capture());
        assertNotNull(allAdverts);
        assertTrue(advertDtos.size() == allAdverts.getContent().size());
    }

        @Configuration
    static class AdvertServiceTestContextConfiguration {
        @Bean
        public AuthenticationService authenticationService() {
            return mock(AuthenticationService.class);
        }

        @Bean
        public ImageService imageService() {
            return new ImageServiceImpl();
        }

        @Bean
        public AdvertConverter advertConverter() {
            return new AdvertConverter();
        }

        @Bean
        public AdvertService advertService() {
            return new AdvertServiceImpl();
        }

        @Bean
        public UserService userService() {
            return new UserServiceImpl();
        }

        @Bean
        public UserConverter userConverter() {
            return new UserConverter();
        }

        @Bean
        public AdvertRepository advertRepository() {
            return mock(AdvertRepository.class);
        }

        @Bean
        public ImageRepository imageRepository() {
            return mock(ImageRepository.class);
        }

        @Bean
        public UserRepository userRepository() {
            return mock(UserRepository.class);
        }

        @Bean
        public EntityManagerFactory entityManagerFactory() {
            return mock(EntityManagerFactory.class);
        }

    }
}
