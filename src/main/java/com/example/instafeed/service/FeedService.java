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

@Service
public class FeedService {

    @Autowired
    private FeedRepository feedRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Environment env;

    @Scheduled(cron = "${ig.sync.interval}")
    public void syncOfTheFeed() {
        String igFeedUrl = this.env.getProperty("ig.feed.url");
        List<FeedDto> feedDtoList = this.fetchIgFeed(igFeedUrl);

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
                });
    }

    public List<FeedDto> fetchIgFeed(String url) {
        return Objects.requireNonNull(this.restTemplate.getForObject(url, DataFeedDto.class)).getData();
    }
}
