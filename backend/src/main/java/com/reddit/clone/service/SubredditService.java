package com.reddit.clone.service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reddit.clone.dto.SubredditDTO;
import com.reddit.clone.model.Subreddit;
import com.reddit.clone.repository.SubredditRepository;


@Service
public class SubredditService {

    @Autowired
    SubredditRepository subredditRepository;

    @Autowired
    AuthService authService;

    // @Autowired
    // SubredditMapper subredditMapper

    @Transactional
    public SubredditDTO saveSubreddit(SubredditDTO subredditDTO)
    {
        Subreddit subreddit =  this.subredditRepository.save(mapRedditDto(subredditDTO));
        subredditDTO.setSubredditId(subreddit.getSubredditId());
        return subredditDTO;

    }

    private Subreddit mapRedditDto(SubredditDTO subredditDTO) {
        return Subreddit.builder().subredditName(subredditDTO.getSubredditName())
        .description(subredditDTO.getDescription())
        .createdDate(Instant.now())
        .user(authService.getCurrentUser())
        .build();
    }

    public SubredditDTO getSubreddit(Long id)
    {
        return this.subredditRepository.findAll().stream().map(this::mapToDTO).findFirst().orElse(new SubredditDTO());
    }

    

    @Transactional(readOnly = true)
    public List<SubredditDTO> getAllSubreddit()
    {
        return this.subredditRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private SubredditDTO mapToDTO(Subreddit subreddit)
    {
        return SubredditDTO.builder().subredditId(subreddit.getSubredditId())
                    .SubredditName(subreddit.getSubredditName())
                    .description(subreddit.getDescription())
                    .numberOfPosts(subreddit.getPosts().size())
                    .build();
    }
    
}
