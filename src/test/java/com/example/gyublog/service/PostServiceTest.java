package com.example.gyublog.service;

import com.example.gyublog.domain.Post;
import com.example.gyublog.repository.PostRepository;
import com.example.gyublog.request.PostCreate;
import com.example.gyublog.response.PostResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional
@SpringBootTest
class PostServiceTest {
    @Autowired
    private PostService postService;
    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void before() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성")
    void test1() {
        // given
        String title = "제목입니다.";
        String content = "내용입니다.";
        PostCreate postCreate = PostCreate.builder()
                .title(title)
                .content(content)
                .build();
        // when
        postService.write(postCreate);
        // then
        Assertions.assertThat(postRepository.count()).isEqualTo(1L);
        Post post = postRepository.findAll().get(0);
        Assertions.assertThat(post.getTitle()).isEqualTo(title);
        Assertions.assertThat(post.getContent()).isEqualTo(content);
    }

    @Test
    @DisplayName("글 한 개 조회")
    void test2() {
        // given
        Long postId = 1L;
        Post savePost = Post.builder()
                .title("foo")
                .content("bar")
                .build();
        Post savedPost = postRepository.save(savePost);
        // when
        PostResponse post = postService.get(savedPost.getId());
        // then
        Assertions.assertThat(post).isNotNull();
        Assertions.assertThat(post.getTitle()).isEqualTo(savedPost.getTitle());
        Assertions.assertThat(post.getContent()).isEqualTo(savedPost.getContent());
    }

    @Test
    @DisplayName("글 여러 개 조회")
    void test3() {
        // given
        Post post = Post.builder()
                .title("123456789012345")
                .content("bar")
                .build();
        Post post2 = Post.builder()
                .title("1234567890")
                .content("bar")
                .build();
        postRepository.saveAll(List.of(post, post2));
        // when
        List<PostResponse> posts = postService.getPosts(PageRequest.of(0, 10));
        // then
        Assertions.assertThat(posts.size()).isEqualTo(2);
    }

}