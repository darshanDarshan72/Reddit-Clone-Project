package com.reddit.clone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponse {
    
    private Long postId;
    private String postName;
    private String url;
    private String description;
    private Integer voteCount;
    private String username;
    private String subredditName;
    private Integer commentCount;
    private String duration;
    private boolean upVote;
    private boolean downVote;
}
