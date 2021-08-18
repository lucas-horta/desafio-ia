package com.ia.desafioia.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GitHubProfile {
    @JsonProperty("id")
    protected String id;
    @JsonProperty("avatar_url")
    protected String avatarUrl;
    @JsonProperty("repositories")
    protected List<String> repositories;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public List<String> getRepositories() {
        return repositories;
    }

    public void setRepositories(List<String> repositories) {
        this.repositories = repositories;
    }
}
