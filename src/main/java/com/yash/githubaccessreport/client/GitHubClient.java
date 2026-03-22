package com.yash.githubaccessreport.client;

import com.yash.githubaccessreport.dto.RepoDTO;
import com.yash.githubaccessreport.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Component
public class GitHubClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${github.api.url}")
    private String baseUrl;

    public List<RepoDTO> getRepos(String org) {
    try {
        String url = baseUrl + "/orgs/" + org + "/repos";

        ResponseEntity<RepoDTO[]> response =
                restTemplate.getForEntity(url, RepoDTO[].class);

        return response.getBody() != null
                ? Arrays.asList(response.getBody())
                : new ArrayList<>();

    } catch (Exception e) {
        System.out.println("Error fetching repos: " + e.getMessage());
        return new ArrayList<>();
    }
}

public List<UserDTO> getCollaborators(String org, String repo) {
    try {
        String url = baseUrl + "/repos/" + org + "/" + repo + "/contributors";

        ResponseEntity<UserDTO[]> response =
                restTemplate.getForEntity(url, UserDTO[].class);

        return response.getBody() != null
                ? Arrays.asList(response.getBody())
                : new ArrayList<>();

    } catch (Exception e) {
        System.out.println("Error fetching collaborators: " + e.getMessage());
        return new ArrayList<>();
    }
}
}