package com.example.instafeed.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DataFeedDto {

    @JsonProperty("data")
    private List<FeedDto> data = new ArrayList<>();

    public DataFeedDto() {

    }

    public DataFeedDto(List<FeedDto> data) {
        this.data = data;
    }
}
