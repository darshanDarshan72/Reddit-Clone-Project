package com.reddit.clone.service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reddit.clone.dto.PostRequest;
import com.reddit.clone.dto.PostResponse;
import com.reddit.clone.model.Post;
import com.reddit.clone.model.Subreddit;
import com.reddit.clone.repository.PostRepository;
import com.reddit.clone.repository.SubredditRepository;


@Service
public class PostService {

    @Autowired
    PostRepository postRepository;
    
    @Autowired
    SubredditRepository subredditRepository;

    @Autowired
    AuthService authService;

    public PostRequest savePost(PostRequest postRequest) {

        Post post = this.postRepository.save(this.mapPostReqToPost(postRequest));
        postRequest.setPostId(post.getPostId());
        return postRequest;

    }

    private Post mapPostReqToPost(PostRequest postRequest) {
        Subreddit subreddit = this.subredditRepository.findBySubredditName(postRequest.getSubredditName()).orElse(new Subreddit());
        return Post.builder().postName(postRequest.getPostName()).description(postRequest.getDescription())
                .url(postRequest.getUrl()).createdData(Instant.now()).subreddit(subreddit)
                .user(this.authService.getCurrentUser())
                .build();
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPosts() {
        return this.postRepository.findAll().stream().map(this::mapPostToResponse).collect(Collectors.toList());
    }

    public PostResponse mapPostToResponse(Post post)
    {
        return PostResponse.builder()
                .postId(post.getPostId())
                .postName(post.getPostName())
                .url(post.getUrl())
                .subredditName(post.getSubreddit().getSubredditName())
                .username(post.getUser().getUsername())
                .description(post.getDescription())
                .build();

    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long post_id) {
        Post post = this.postRepository.findById(post_id).orElse(new Post());
        return this.mapPostToResponse(post);
    }
    
}
