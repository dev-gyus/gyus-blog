package com.example.gyublog.domain;

import lombok.Builder;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@ToString
public class EmbeddedPost {
    @Id
    private ObjectId id;
    private Long postId;
    private String postTitle;
    private String postContent;

    @Builder
    public EmbeddedPost(Long postId, String postTitle, String postContent) {
        this.postId = postId;
        this.postTitle = postTitle;
        this.postContent = postContent;
    }
}
