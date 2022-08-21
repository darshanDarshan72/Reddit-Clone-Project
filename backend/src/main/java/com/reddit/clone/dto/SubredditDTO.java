package com.reddit.clone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubredditDTO {

    private Long subredditId;
    private String SubredditName;
    private String description;
    private Integer numberOfPosts;
    
}
