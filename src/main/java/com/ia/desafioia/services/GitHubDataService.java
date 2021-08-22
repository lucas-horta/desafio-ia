package com.ia.desafioia.services;

import com.ia.desafioia.models.GitHubProfile;
import com.ia.desafioia.models.GitHubRepository;
import com.ia.desafioia.repositories.interfaces.IUserRepository;
import com.ia.desafioia.services.interfaces.IGitHubDataService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

@Service
public class GitHubDataService implements IGitHubDataService {

    protected final WebClient gitHubWebClient;
    protected final IUserRepository repository;

    public GitHubDataService(IUserRepository repository) {
        this.repository = repository;
        gitHubWebClient = WebClient.create("https://api.github.com/users");
    }

    @Override
    public GitHubProfile github(String id) {
        String gitHubName = "";
        GitHubProfile gitHubProfile = new GitHubProfile();
        if(this.repository.findById(id).isPresent()){
            gitHubName = this.repository.findById(id).get().getGitHubProfile();
        }
        try {
            gitHubProfile = gitHubWebClient
                    .get()
                    .uri("/" + gitHubName)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(GitHubProfile.class)
                    .block();
        } catch (WebClientException e) {
            e.printStackTrace();
        }
        assert gitHubProfile != null;
        gitHubProfile.setRepositories(repositories(gitHubName));
        return gitHubProfile;
    }

    @Override
    public GitHubRepository[] repositories(String gitHub) {
        GitHubRepository[] repos = new GitHubRepository[0];
        try {
            repos = gitHubWebClient
                    .get()
                    .uri("/" + gitHub + "/repos")
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