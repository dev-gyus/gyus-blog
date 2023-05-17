package com.example.gyublog.service;

import com.example.gyublog.domain.Post;
import com.example.gyublog.repository.PostRepository;
import com.example.gyublog.request.PostCreate;
import com.example.gyublog.response.PostResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;


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
        assertThat(postRepository.count()).isEqualTo(1L);
        Post post = postRepository.findAll().get(0);
        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getContent()).isEqualTo(content);
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
        assertThat(post).isNotNull();
        assertThat(post.getTitle()).isEqualTo(savedPost.getTitle());
        assertThat(post.getContent()).isEqualTo(savedPost.getContent());
    }

    @Test
    @DisplayName("글 1 페이지 조회")
    void test3() {
        // given
        // 글 30개 생성
        List<Post> requestPosts = IntStream.range(0, 30).mapToObj(idx ->
            Post.builder()
                    .title("규스 제목 - " + idx)
                    .content("규스 내용 - " + idx)
                    .build()
        ).collect(Collectors.toList());
        // 글 30개 저장
        postRepository.saveAll(requestPosts);
        // when
        List<PostResponse> posts = postService.getPosts(1, 10);
        // then
        assertThat(posts.size()).isEqualTo(10);
        assertThat(posts.get(0).getTitle()).isEqualTo("규스 제목 - 19");
        assertThat(posts.get(9).getTitle()).isEqualTo("규스 제목 - 10");
    }

}