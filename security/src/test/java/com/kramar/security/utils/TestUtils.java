package com.kramar.security.utils;

import com.kramar.data.dbo.OAuthClient;
import com.kramar.data.dbo.UserDbo;
import com.kramar.data.type.UserRole;
import com.kramar.data.type.UserStatus;

import java.util.Collections;
import java.util.UUID;

public class TestUtils {

    private static final String ANY_WORD = "String";
    private static final UUID ID = UUID.fromString("00000000-0000-0000-0000-000000000000");

    public static OAuthClient createOAuthClient() {
        final OAuthClient oAuthClient = new OAuthClient();
        oAuthClient.setClientId("web");
        oAuthClient.setClientSecret("web");
        oAuthClient.setResources("authresource,webresource");
        return oAuthClient;
    }

    public static UserDbo createUser() {
        final UserDbo userDbo = new UserDbo();
        userDbo.setId(ID);
        userDbo.setEmail(ANY_WORD);
        userDbo.setUserName(ANY_WORD);
        userDbo.setUserSurname(ANY_WORD);
        userDbo.setPassword("$2a$10$oJD.FohR7Jhn.CHVh83KGOpsp.JNKuBjryHioPuR33F0U0yQ9Kqt2");
        userDbo.setStatus(UserStatus.ACTIVE);
        userDbo.setUserRoles(Collections.singletonList(UserRole.ADMIN));
        return userDbo;
    }

}
