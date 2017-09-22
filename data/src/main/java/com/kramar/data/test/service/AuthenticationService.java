package com.kramar.data.test.service;

import com.kramar.data.dbo.UserDbo;

import java.util.UUID;

public interface AuthenticationService {

    UUID getCurrentUserId();

    UserDbo getCurrentUser();

}
