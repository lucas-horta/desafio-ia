package com.ia.desafioia.services;

import com.ia.desafioia.dto.UserDto;
import com.ia.desafioia.dto.UserResponseDto;
import com.ia.desafioia.models.User;
import com.ia.desafioia.repositories.interfaces.IUserRepository;
import com.ia.desafioia.services.interfaces.IUserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDataService implements IUserDataService {
    protected IUserRepository repository;

    @Autowired
    public UserDataService(IUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User create(UserDto userDto) {
        return this.repository.save(new User(userDto));
    }

    @Override
    public List<UserResponseDto> findAll() {
        return this.repository.findAll().stream().map(UserResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public List<UserResponseDto> findAll(int page, int size) {
        return this.repository.findAll(PageRequest.of(page, size)).stream().map(UserResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public UserResponseDto findById(String id){
        return this.repository.findById(id).map(UserResponseDto::new).orElse(null);
    }


}
