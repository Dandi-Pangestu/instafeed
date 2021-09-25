package com.example.instafeed.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FeedDto {

    @JsonProperty("id")
    private String id;

    @JsonProperty("media_type")
    private String mediaType;

    @JsonProperty("permalink")
    private String permalink;

    @JsonProperty("media_url")
    private String mediaUrl;

    @JsonProperty("caption")
    private String caption;

    @JsonProperty("timestamp")
    private String timestamp;
}
