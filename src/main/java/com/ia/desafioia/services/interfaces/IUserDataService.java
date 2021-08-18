package com.ia.desafioia.services.interfaces;

import com.ia.desafioia.dto.UserDto;
import com.ia.desafioia.dto.UserResponseDto;
import com.ia.desafioia.models.User;

import java.util.List;

public interface IUserDataService {
    User create(UserDto userDto);
    List<UserResponseDto> findAll();
    List<UserResponseDto> findAll(int page, int size);
    UserResponseDto findById(String id);
}
