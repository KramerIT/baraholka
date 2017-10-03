package com.kramar.data.service.impl;

import com.kramar.data.converter.UserConverter;
import com.kramar.data.dbo.UserDbo;
import com.kramar.data.dto.ChangePasswordDto;
import com.kramar.data.dto.UserDto;
import com.kramar.data.exception.ConflictException;
import com.kramar.data.exception.ErrorReason;
import com.kramar.data.service.UserService;
import com.kramar.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserConverter userConverter;

    @Override
    public Page<UserDto> getAllUser(final Pageable pageable) {
        return userRepository.findAll(pageable).map(userConverter::transform);
    }

    @Override
    public UserDto createUser(final UserDto userDto) {
        userDto.setId(null);
        UserDbo userDbo = userConverter.transform(userDto);
        userDbo = userRepository.save(userDbo);
        return userConverter.transform(userDbo);
    }

    @Override
    public UserDto getUserById(final UUID id) {
        final UserDbo userDbo = userRepository.getById(id);
        return userConverter.transform(userDbo);
    }

    @Override
    public void deleteUserById(final UUID id) {
        final UserDbo userDbo = userRepository.getById(id);
        userRepository.delete(userDbo);
    }

    @Override
    public UserDto modifyUserById(final UUID id, final UserDto userDto) {
        final UserDbo oldUser = userRepository.getById(id);
        userDto.setId(oldUser.getId());
        UserDbo userDbo = userConverter.transform(userDto);
        userDbo = userRepository.save(userDbo);
        return userConverter.transform(userDbo);
    }

    @Override
    public void changePassword(final UUID id, final ChangePasswordDto passwordDto) {
        final UserDbo userDbo = userRepository.getById(id);
        if (userDbo.getPassword().equals(passwordDto.getOldPassword())) {
            userDbo.setPassword(passwordDto.getNewPassword());
            userRepository.save(userDbo);
        } else {
            throw new ConflictException(ErrorReason.INVALID_PASSWORD, "changePassword");
        }
    }
}
