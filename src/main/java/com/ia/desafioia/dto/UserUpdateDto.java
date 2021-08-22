package com.ia.desafioia.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ia.desafioia.models.User;

public class UserUpdateDto {
    @JsonProperty("name")
    protected String name;
    @JsonProperty("email")
    protected String email;
    @JsonProperty("login")
    protected String login;
    @JsonProperty("password")
    protected String password;
    @JsonProperty("github")
    protected String gitHubProfile;

    public UserUpdateDto(String name, String email, String login, String gitHubProfile, String password) {
        this.name = name;
        this.email = email;
        this.login = login;
        this.gitHubProfile = gitHubProfile;
        this.password = password;
    }

    public UserUpdateDto(User user){
        this(user.getName(), user.getEmail(), user.getLogin(), user.getGitHubProfile(), user.getPassword());
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getGitHubProfile() {
        return gitHubProfile;
    }
}
