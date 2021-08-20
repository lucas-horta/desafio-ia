package com.ia.desafioia.services.interfaces;

import com.ia.desafioia.models.GitHubProfile;
import com.ia.desafioia.models.GitHubRepository;
import com.ia.desafioia.repositories.interfaces.IUserRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

import java.util.ArrayList;
import java.util.List;

@Service
public class GitHubDataService implements IGitHubDataService {

    protected WebClient gitHubWebClient;
    protected IUserRepository repository;

    public GitHubDataService(IUserRepository repository) {
        this.repository = repository;
        gitHubWebClient = WebClient.create("https://api.github.com/users");
    }

    @Override
    public GitHubProfile github(String id) {
        String github = "";
        GitHubProfile gitHubProfile = new GitHubProfile();
        if(this.repository.findById(id).isPresent()){
            github = this.repository.findById(id).get().getGitHubProfile();
        }
        try {
            gitHubProfile = gitHubWebClient
                    .get()
                    .uri("/" + github)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(GitHubProfile.class)
                    .block();
        } catch (WebClientException e) {
            e.printStackTrace();
        }
        gitHubProfile.setRepositories(repositories(github));
        return gitHubProfile;
    }

    @Override
    public GitHubRepository[] repositories(String github) {
        GitHubRepository[] repos = new GitHubRepository[0];
        try {
            repos = gitHubWebClient
                    .get()
                    .uri("/" + github + "/repos")
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(GitHubRepository[].class)
                    .block();
        } catch (WebClientException e) {
            e.printStackTrace();
        }
        return repos;
    }
}