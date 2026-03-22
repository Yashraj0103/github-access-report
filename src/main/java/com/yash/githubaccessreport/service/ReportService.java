package com.yash.githubaccessreport.service;

import com.yash.githubaccessreport.client.GitHubClient;
import com.yash.githubaccessreport.dto.RepoDTO;
import com.yash.githubaccessreport.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReportService {

    @Autowired
    private GitHubClient client;

    public Map<String, Set<String>> generateReport(String org) {

        Map<String, Set<String>> userRepoMap = new HashMap<>();

        System.out.println("🚀 Starting report generation for org: " + org);

        List<RepoDTO> repos = client.getRepos(org);

        // ✅ SAFE CHECK
        if (repos == null || repos.isEmpty()) {
            System.out.println("⚠️ No repositories found for org: " + org);
            return userRepoMap;
        }

        repos.parallelStream().forEach(repo -> {

            // ✅ NULL CHECK FOR REPO
            if (repo == null || repo.getName() == null) {
                System.out.println("⚠️ Skipping null repo");
                return;
            }

            System.out.println("📦 Processing repo: " + repo.getName());

            List<UserDTO> users = client.getCollaborators(org, repo.getName());

            // ✅ SAFE CHECK FOR USERS
            if (users == null || users.isEmpty()) {
                System.out.println("⚠️ No users found for repo: " + repo.getName());
                return;
            }

            for (UserDTO user : users) {

                // ✅ NULL CHECK FOR USER
                if (user == null || user.getLogin() == null) {
                    System.out.println("⚠️ Skipping null user in repo: " + repo.getName());
                    continue;
                }

                System.out.println("👤 User: " + user.getLogin() + " -> Repo: " + repo.getName());

                synchronized (userRepoMap) {
                    userRepoMap
                            .computeIfAbsent(user.getLogin(), k -> new HashSet<>())
                            .add(repo.getName());
                }
            }
        });

        System.out.println("✅ Report generation completed!");

        return userRepoMap;
    }
}