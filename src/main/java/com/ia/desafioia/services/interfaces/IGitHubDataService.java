package com.ia.desafioia.services.interfaces;

import com.ia.desafioia.models.GitHubProfile;
import com.ia.desafioia.models.GitHubRepository;

public interface IGitHubDataService {
    GitHubProfile github(String gitHub);
    GitHubRepository[] repositories(String gitHub);
}
