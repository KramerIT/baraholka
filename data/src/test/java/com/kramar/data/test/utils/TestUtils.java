package com.kramar.data.test.utils;

import com.kramar.data.dbo.AdvertDbo;
import com.kramar.data.dbo.UserDbo;
import com.kramar.data.type.AdvertStatus;
import com.kramar.data.type.AdvertType;
import com.kramar.data.type.CurrencyType;
import com.kramar.data.type.UserStatus;

import java.math.BigDecimal;
import java.util.UUID;

public class TestUtils {

    private static final String STRING = "String";
    private static final String DESCRIPTION = "Description";
    private static final BigDecimal PRICE = BigDecimal.valueOf(99.99);
    private static final UUID ID = UUID.fromString("00000000-0000-0000-0000-000000000000");

    public static AdvertDbo createAdvert() {
        final AdvertDbo advertDbo = new AdvertDbo();
        advertDbo.setId(ID);
        advertDbo.setAdvertStatus(AdvertStatus.ACTIVE);
        advertDbo.setAdvertType(AdvertType.SALE);
        advertDbo.setHeadLine(STRING);
        advertDbo.setPrice(PRICE);
        advertDbo.setCurrencyType(CurrencyType.USD);
        advertDbo.setDescription(DESCRIPTION);
        advertDbo.setOwner(new UserDbo());
        return advertDbo;
    }

    public static AdvertDbo createAdvert(UserDbo userDbo) {
        final AdvertDbo advertDbo = createAdvert();
        advertDbo.setOwner(userDbo);
        return advertDbo;
    }

    public static UserDbo createUser() {
        final UserDbo userDbo = new UserDbo();
        userDbo.setId(ID);
        userDbo.setEmail(STRING);
        userDbo.setUserName(STRING);
        userDbo.setUserSurname(STRING);
        userDbo.setStatus(UserStatus.ACTIVE);
        return userDbo;
    }
}
