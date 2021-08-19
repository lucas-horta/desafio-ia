package com.ia.desafioia.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ia.desafioia.models.User;

import java.util.Date;

public class UserResponseDto {
    @JsonProperty("id")
    protected String id;
    @JsonProperty("name")
    protected String name;
    @JsonProperty("email")
    protected String email;
    @JsonProperty("login")
    protected String login;
    @JsonProperty("github")
    protected String gitHubProfile;
    @JsonProperty("created_date")
    protected Date createdDate;

    public UserResponseDto(String name, String email, String login, String gitHubProfile, Date createdDate, String id) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.login = login;
        this.gitHubProfile = gitHubProfile;
        this.createdDate = createdDate;
    }

    public UserResponseDto(User user){
        this(user.getName(), user.getEmail(), user.getLogin(), user.getGitHubProfile(), user.getCreatedDate(), user.getId());
    }
}
