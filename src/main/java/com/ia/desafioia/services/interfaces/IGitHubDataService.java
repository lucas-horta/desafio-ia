package com.ia.desafioia.services.interfaces;

import com.ia.desafioia.models.GitHubProfile;
import com.ia.desafioia.models.GitHubRepository;

public interface IGitHubDataService {
    GitHubProfile github(String github);
    GitHubRepository[] repositories(String github);
}
