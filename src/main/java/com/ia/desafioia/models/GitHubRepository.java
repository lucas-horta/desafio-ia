package com.ia.desafioia.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GitHubRepository {
    @JsonProperty("id")
    protected Integer id;
    @JsonProperty("name")
    protected String name;
    @JsonProperty("html_url")
    protected String url;
}
