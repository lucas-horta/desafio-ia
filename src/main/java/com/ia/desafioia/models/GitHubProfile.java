package com.ia.desafioia.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GitHubProfile {
    @JsonProperty("id")
    protected String id;
    @JsonProperty("avatar_url")
    protected String avatarUrl;
    @JsonProperty("repositories")
    protected GitHubRepository[] repositories;

    public void setRepositories(GitHubRepository[] repositories) {
        this.repositories = repositories;
    }
}
