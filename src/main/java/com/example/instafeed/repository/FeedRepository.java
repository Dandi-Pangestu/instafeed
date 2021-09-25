package com.example.instafeed.repository;

import com.example.instafeed.models.Feed;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface FeedRepository extends MongoRepository<Feed, String> {

    Feed findByIgId(String igId);
}
