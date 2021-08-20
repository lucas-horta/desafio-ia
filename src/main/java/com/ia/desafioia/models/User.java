package com.ia.desafioia.models;

import com.ia.desafioia.dto.UserDto;
import com.ia.desafioia.dto.UserUpdateDto;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.Date;

@Data
@RedisHash("user")
public class User {

    @Id
    @Indexed
    protected String id;
    protected String login;
    protected String name;
    protected String password;
    protected Date createdDate;
    protected Date updatedDate;
    protected String gitHubProfile;
    protected String email;
    protected boolean admin;

    public User() {
    }

    public User(String name, String login, String password, String githubProfile, String email, String id) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.gitHubProfile = githubProfile;
        this.email = email;
        this.id = id;
    }

    public User(String name, String email, String login, String gitHubProfile, String password) {
        this.name = name;
        this.email = email;
        this.login = login;
        this.gitHubProfile = gitHubProfile;
        this.password = password;
    }

    public User(UserDto userDto) {
        this(userDto.getName(), userDto.getLogin(), userDto.getPassword(), userDto.getGitHubProfile(), userDto.getEmail(), userDto.getId());
    }

    public User(UserUpdateDto dto){
        this(dto.getName(),dto.getEmail(), dto.getLogin(), dto.getGitHubProfile(), dto.getPassword());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getGitHubProfile() {
        return gitHubProfile;
    }

    public void setGitHubProfile(String gitHubProfile) {
        this.gitHubProfile = gitHubProfile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}