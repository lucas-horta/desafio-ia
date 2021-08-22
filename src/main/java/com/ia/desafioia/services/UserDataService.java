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
    protected final IUserRepository repository;

    @Autowired
    public UserDataService(IUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User create(UserDto dto) {
        //TODO: Remove bruteforce validation through @Valid for DTO, with @NotNull/@NotEmpty etc
        if (invalidUser(dto.getLogin(), dto.getEmail(), dto.getPassword(), dto.getGitHubProfile(), dto.getName()) ||
                repository.findAllByLogin(dto.getLogin()).size() != 0 ||
                repository.findAllByEmail(dto.getEmail()).size() != 0)
            return null;

        User user = new User(dto);
        user.setCreatedDate(new Date());
        return this.repository.save(user);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Override
    public User update(String id, UserUpdateDto dto, String login) {


        //TODO Remove bruteforce validations (valid user, repeat login, repeat email)

        if (invalidUser(dto.getLogin(), dto.getEmail(), dto.getPassword(), dto.getGitHubProfile(), dto.getName())){
            return null;
        }

        boolean loginAvailable = true, emailAvailable = true;
        if(repository.findAllByLogin(dto.getLogin()).size() != 0)
            loginAvailable = repository.findFirstByLogin(dto.getLogin()).getId().equals(id);
        if(repository.findAllByEmail(dto.getEmail()).size() != 0)
            emailAvailable = repository.findFirstByEmail(dto.getEmail()).getId().equals(id);
        if (!loginAvailable || !emailAvailable) return null;

        User loggedInUser = this.repository.findFirstByLogin(login);
        User user = this.repository.findById(id).get();

        boolean isChangingPassword = !dto.getPassword().equals(user.getPassword());
        boolean userCanChangePassword = !loggedInUser.isAdmin() || !loggedInUser.getId().equals(user.getId());

        if (isChangingPassword && !userCanChangePassword) {
            return null;
        }

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
        if (!this.repository.existsById(id)) {
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
    public UserResponseDto findById(String id) {
        return this.repository.findById(id).map(UserResponseDto::new).orElse(null);
    }

    @Override
    public String iddqd(String id) {
        if(this.repository.findById(id).isEmpty()) return "No user to cheat death";
        User user = this.repository.findById(id).get();
        user.setAdmin(true);
        this.repository.save(user);
        return "God mode activated for user id " + id;
    }

    private boolean invalidUser(String login, String email, String password, String gitHubProfile, String name) {
        return login == null || login.length() == 0 || email == null || email.length() == 0 ||
                password == null || password.length() == 0 || gitHubProfile == null ||
                gitHubProfile.length() == 0 || name == null || name.length() == 0;
    }

}
