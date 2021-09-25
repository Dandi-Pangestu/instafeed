package com.example.instafeed.unit.service;

import com.example.instafeed.dto.DataFeedDto;
import com.example.instafeed.dto.FeedDto;
import com.example.instafeed.models.Feed;
import com.example.instafeed.repository.FeedRepository;
import com.example.instafeed.service.FeedService;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class FeedServiceTest {

    private FeedRepository feedRepository = Mockito.mock(FeedRepository.class);
    private RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
    private Environment env = Mockito.mock(Environment.class);
    private FeedService feedService;
    private DataFeedDto dataFeedDto;
    private final Faker faker = new Faker();

    @BeforeEach
    void init() {
        feedService = new FeedService(feedRepository, restTemplate, env);

        FeedDto dto1 = new FeedDto();
        dto1.setId(String.valueOf(faker.number().randomNumber()));
        dto1.setCaption(faker.lorem().sentence());
        dto1.setMediaType(faker.lorem().word());
        dto1.setMediaUrl(faker.internet().url());
        dto1.setPermalink(faker.internet().url());
        dto1.setTimestamp(faker.date().toString());

        FeedDto dto2 = new FeedDto();
        dto2.setId(String.valueOf(faker.number().randomNumber()));
        dto2.setCaption(faker.lorem().sentence());
        dto2.setMediaType(faker.lorem().word());
        dto2.setMediaUrl(faker.internet().url());
        dto2.setPermalink(faker.internet().url());
        dto2.setTimestamp(faker.date().toString());

        dataFeedDto = new DataFeedDto(new ArrayList<>(Arrays.asList(dto1, dto2)));
    }

    @Test
    void testFetchIgFeed_shouldReturnListFeedDtoWhenGetSuccessfulResponse() {
        when(restTemplate.getForObject(anyString(), any())).thenReturn(dataFeedDto);

        List<FeedDto> returnedFeedDtoList = feedService.fetchIgFeed(faker.internet().url());

        assertThat(returnedFeedDtoList).hasSize(2);
    }

    @Test
    void testFetchIgFeed_shouldThrownExceptionWhenGetErrorResponse() {
        when(restTemplate.getForObject(anyString(), any())).thenReturn(null);

        Assertions.assertThrows(NullPointerException.class, () -> {
           feedService.syncOfTheFeed();
        });
    }

    @Test
    void testSyncOfTheFeed_shouldReturnTwoWhenGivenTwoUnsavedFeedDto() {
        String fakeUrl = faker.internet().url();
        when(env.getProperty(anyString())).thenReturn(fakeUrl);
        FeedService feedServiceMocked = Mockito.spy(feedService);
        Mockito.doReturn(dataFeedDto.getData()).when(feedServiceMocked).fetchIgFeed(fakeUrl);
        when(feedRepository.findByIgId(anyString())).thenReturn(null);

        int sizeOfSavedFeed = feedServiceMocked.syncOfTheFeed();

        assertThat(sizeOfSavedFeed).isEqualTo(2);
    }

    @Test
    void testSyncOfTheFeed_shouldReturnOneWhenGivenOneUnsavedFeedDto() {
        String fakeUrl = faker.internet().url();
        when(env.getProperty(anyString())).thenReturn(fakeUrl);
        FeedService feedServiceMocked = Mockito.spy(feedService);
        Mockito.doReturn(dataFeedDto.getData()).when(feedServiceMocked).fetchIgFeed(fakeUrl);

        when(feedRepository.findByIgId(dataFeedDto.getData().get(0).getId())).thenReturn(null);
        when(feedRepository.findByIgId(dataFeedDto.getData().get(1).getId()))
                .thenReturn(new Feed(
                        dataFeedDto.getData().get(1).getId(),
                        dataFeedDto.getData().get(1).getMediaType(),
                        dataFeedDto.getData().get(1).getPermalink(),
                        dataFeedDto.getData().get(1).getMediaUrl(),
                        dataFeedDto.getData().get(1).getCaption(),
                        dataFeedDto.getData().get(1).getTimestamp()
                ));

        int sizeOfSavedFeed = feedServiceMocked.syncOfTheFeed();

        assertThat(sizeOfSavedFeed).isEqualTo(1);
    }
}
