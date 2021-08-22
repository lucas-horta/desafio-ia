package com.ia.desafioia.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDto {

    @JsonProperty("id")
    protected String id;
    @JsonProperty("name")
    protected String name;
    @JsonProperty("login")
    protected String login;
    @JsonProperty("password")
    protected String password;
    @JsonProperty("github")
    protected String gitHubProfile;
    @JsonProperty("email")
    protected String email;


    public UserDto(String name, String login, String password, String gitHubProfile, String email) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.gitHubProfile = gitHubProfile;
        this.email = email;
    }

    public String getName() {
        return name;
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

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public void setLogin(String login) {
        this.login = login;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setGitHubProfile(String gitHubProfile) {
        this.gitHubProfile = gitHubProfile;
    }
}
