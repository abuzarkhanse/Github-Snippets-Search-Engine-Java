package com.snippetsearch.controller;

import com.snippetsearch.model.Snippet;
import com.snippetsearch.service.GitHubService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/snippets")
@CrossOrigin(origins = "*")
public class SnippetController {

    private final GitHubService service;

    public SnippetController(GitHubService service) {
        this.service = service;
    }

    @GetMapping
    public List<Snippet> getSnippets(@RequestParam String query, @RequestParam String language) {
        return service.searchSnippets(query, language);
    }
}