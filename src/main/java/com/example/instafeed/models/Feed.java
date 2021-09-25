package com.example.instafeed.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "feeds")
public class Feed {

    @Id
    private String id;

    private String igId;
    private String mediaType;
    private String permalink;
    private String mediaUrl;
    private String caption;
    private String timestamp;

    public Feed() {

    }

    public Feed(String igId, String mediaType, String permalink, String mediaUrl, String caption, String timestamp) {
        this.igId = igId;
        this.mediaType = mediaType;
        this.permalink = permalink;
        this.mediaUrl = mediaUrl;
        this.caption = caption;
        this.timestamp = timestamp;
    }
}
