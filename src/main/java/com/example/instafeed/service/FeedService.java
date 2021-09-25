package com.example.instafeed.service;

import com.example.instafeed.dto.DataFeedDto;
import com.example.instafeed.dto.FeedDto;
import com.example.instafeed.models.Feed;
import com.example.instafeed.repository.FeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class FeedService {

    private final FeedRepository feedRepository;
    private final RestTemplate restTemplate;
    private final Environment env;

    @Autowired
    public FeedService(FeedRepository feedRepository, RestTemplate restTemplate, Environment env) {
        this.feedRepository = feedRepository;
        this.restTemplate = restTemplate;
        this.env = env;
    }

    @Scheduled(cron = "${ig.sync.interval}")
    public int syncOfTheFeed() {
        String igFeedUrl = this.env.getProperty("ig.feed.url");
        List<FeedDto> feedDtoList = this.fetchIgFeed(igFeedUrl);
        AtomicInteger counter = new AtomicInteger();

        feedDtoList.stream()
                .filter(feedDto -> this.feedRepository.findByIgId(feedDto.getId()) == null)
                .forEach(feedDto -> {
                    Feed feed = new Feed(
                            feedDto.getId(),
                            feedDto.getMediaType(),
                            feedDto.getPermalink(),
                            feedDto.getMediaUrl(),
                            feedDto.getCaption(),
                            feedDto.getTimestamp()
                    );
                    this.feedRepository.save(feed);
                    counter.getAndIncrement();
                });

        return counter.get();
    }

    public List<FeedDto> fetchIgFeed(String url) {
        return Objects.requireNonNull(this.restTemplate.getForObject(url, DataFeedDto.class)).getData();
    }
}
