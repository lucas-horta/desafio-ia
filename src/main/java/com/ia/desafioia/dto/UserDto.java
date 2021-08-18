package com.ia.desafioia.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDto {
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
}
