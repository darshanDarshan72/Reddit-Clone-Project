package com.reddit.clone.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reddit.clone.dto.PostRequest;
import com.reddit.clone.dto.PostResponse;
import com.reddit.clone.service.PostService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    PostService postService;

    @PostMapping
    public ResponseEntity<PostRequest> savePost(@RequestBody PostRequest postRequest)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.postService.savePost(postRequest));

    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts()
    {
        return ResponseEntity.ok().body(this.postService.getPosts());
    }

    @GetMapping("/{post_id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long post_id)
    {
        return ResponseEntity.ok().body(this.postService.getPost(post_id));
    }
    
}
