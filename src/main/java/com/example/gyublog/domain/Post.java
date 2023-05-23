package com.example.gyublog.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//@Entity
@Document
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = 1L;
    private String title;
//    @Lob
//    @Basic(fetch = FetchType.LAZY)
    private String content;

    @Builder
    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // 엔티티에 서비스 정책관련 로직을 넣지 말것!!!!! 필수!!!
}
