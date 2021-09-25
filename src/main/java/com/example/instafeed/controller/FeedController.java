package com.example.instafeed.controller;

import com.example.instafeed.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feeds")
public class FeedController {

    @Autowired
    private FeedService feedService;

    @PostMapping(value = "/sync-ig-feed")
    public ResponseEntity<?> syncIgFeed() {
        this.feedService.syncOfTheFeed();
        return new ResponseEntity<>("Sync has been successfully", HttpStatus.OK);
    }
}
