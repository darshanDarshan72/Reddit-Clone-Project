package com.reddit.clone.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reddit.clone.dto.SubredditDTO;
import com.reddit.clone.service.SubredditService;

@RestController
@RequestMapping("/api/subreddit")
public class SubredditController {

    @Autowired
    SubredditService subredditService;

    @PostMapping
    public ResponseEntity<SubredditDTO> saveSubreddit(@RequestBody SubredditDTO SubredditDTO)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.subredditService.saveSubreddit(SubredditDTO));
    }

    @GetMapping
    public ResponseEntity<List<SubredditDTO>> getAllSubreddit()
    {
        return ResponseEntity.ok().body(this.subredditService.getAllSubreddit());
        
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubredditDTO> getSubreddit(@PathVariable Long id)
    {
        return ResponseEntity.status(HttpStatus.FOUND).body(this.subredditService.getSubreddit(id));
    }
    
}
