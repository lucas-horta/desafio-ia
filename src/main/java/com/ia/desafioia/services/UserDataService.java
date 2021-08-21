package com.ia.desafioia.services;

import com.ia.desafioia.dto.UserDto;
import com.ia.desafioia.dto.UserResponseDto;
import com.ia.desafioia.dto.UserUpdateDto;
import com.ia.desafioia.models.User;
import com.ia.desafioia.repositories.interfaces.IUserRepository;
import com.ia.desafioia.services.interfaces.IUserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    public User create(UserDto dto) {

        if(repository.findAllByLogin(dto.getLogin()).size()!=0 ||
                repository.findAllByEmail(dto.getEmail()).size()!=0)
            return null;

        User user = new User(dto);
        user.setCreatedDate(new Date());

        return this.repository.save(user);
    }

    @Override
    public User update(String id, UserUpdateDto dto) {
        User user = this.repository.findById(id).get();
        user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail());
        user.setLogin(dto.getLogin());
        user.setName(dto.getName());
        user.setGitHubProfile(dto.getGitHubProfile());
        user.setUpdatedDate(new Date());
        return this.repository.save(user);
    }

    @Override
    public Boolean delete(String id) {
        if(!this.repository.existsById(id)){
            return false;
        }
        this.repository.deleteById(id);
        return !this.repository.existsById(id);
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

    @Override
    public String iddqd(String id) {
        User user = this.repository.findById(id).get();
        user.setAdmin(true);
        this.repository.save(user);
        return "God mode activated for user id "+id;
    }
}
