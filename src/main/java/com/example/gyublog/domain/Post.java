package com.example.gyublog.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@ToString
@Table(name = "post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String content;

    @Builder
    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void edit(PostEditor postEditor) {
        this.title = Objects.isNull(postEditor.getTitle()) ? this.title : postEditor.getTitle();
        this.content = Objects.isNull(postEditor.getContent()) ? this.content : postEditor.getContent();
    }
}
