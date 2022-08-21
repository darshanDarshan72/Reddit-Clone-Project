package com.reddit.clone.repository;

import java.util.Optional;

// import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reddit.clone.model.Subreddit;

@Repository
public interface SubredditRepository extends JpaRepository<Subreddit,Long>{


    Optional<Subreddit> findBySubredditName(String subredditName);
    
}
