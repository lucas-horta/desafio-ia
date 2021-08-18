package com.ia.desafioia.models;

import com.ia.desafioia.dto.UserDto;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.Date;

@Data
@RedisHash(value="user")
public class User {

    @Id
    @Indexed
    protected String id;
    protected String name;
    protected String login;
    protected String password;
    protected Date createdDate = new Date();
    protected Date updatedDate;
    protected String githubProfile;
    protected String email;
    protected boolean admin;

    public User() {
    }

    public User(String name, String login, String password, String githubProfile, String email) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.githubProfile = githubProfile;
        this.email = email;
    }

    public User(UserDto userDto) {
        this(userDto.getName(), userDto.getLogin(), userDto.getPassword(), userDto.getGitHubProfile(), userDto.getEmail());
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

    public String getGithubProfile() {
        return githubProfile;
    }

    public void setGithubProfile(String githubProfile) {
        this.githubProfile = githubProfile;
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