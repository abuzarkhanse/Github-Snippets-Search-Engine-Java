package com.snippetsearch.service;

import com.snippetsearch.model.Snippet;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class GitHubService {

    private final String GITHUB_TOKEN = "******************************************************************";
    private final String GITHUB_API = "https://api.github.com/********************;

    public List<Snippet> searchSnippets(String query, String language) {
        RestTemplate restTemplate = new RestTemplate();

        String fullUrl = GITHUB_API + query + "+language:" + language + "&per_page=5";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "token " + GITHUB_TOKEN);
        headers.set("Accept", "application/vnd.github.v3+json");

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(fullUrl, HttpMethod.GET, entity, String.class);

        List<Snippet> resultList = new ArrayList<>();
        JSONObject jsonResponse = new JSONObject(response.getBody());
        JSONArray items = jsonResponse.getJSONArray("items");

        for (int i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);
            Snippet snippet = new Snippet();
            snippet.setFilename(item.getString("name"));
            snippet.setLanguage(language);
            snippet.setRepoUrl(item.getJSONObject("repository").getString("html_url"));
            snippet.setCode("// Click 'View on GitHub' to see full code"); // placeholder
            resultList.add(snippet);
        }

        return resultList;
    }
}