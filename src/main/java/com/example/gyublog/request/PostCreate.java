package com.example.gyublog.request;

import com.example.gyublog.domain.EmbeddedPost;
import com.example.gyublog.domain.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PostCreate {
    @NotBlank(message = "타이틀 값은 필수 입니다.")
    private String title;
    @NotBlank(message = "컨텐츠 값을 입력 해 주세요.")
    private String content;

    @Builder
    public PostCreate(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Post toDao() {
        Post post = Post.builder()
                .title(this.title)
                .content(this.content)
                .build();
        EmbeddedPost embeddedPost = EmbeddedPost.builder().postId(post.getId()).postTitle(post.getTitle()).postContent(post.getContent()).build();
        post.getEmbeddedPostList().add(embeddedPost);
        return post;
    }

    // 빌더의 장점
    // - 가독성이 좋다 (값 생성에 대한 유연함)
    // - 필요한 값만 받을 수 있다. -> 오버로딩 가능한 조건
    
}
