package com.example.gyublog.service;

import com.example.gyublog.domain.Post;
import com.example.gyublog.repository.PostRepository;
import com.example.gyublog.request.PostCreate;
import com.example.gyublog.request.PostEdit;
import com.example.gyublog.request.PostSearch;
import com.example.gyublog.response.PostResponse;
import org.assertj.core.api.Assertions;
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
import static org.junit.jupiter.api.Assertions.assertEquals;


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
        List<PostResponse> posts = postService.getPosts(
                PostSearch.builder()
                        .page(1)
                        .size(10)
                        .build()
        );
        // then
        assertThat(posts.size()).isEqualTo(10);
        assertThat(posts.get(0).getTitle()).isEqualTo("규스 제목 - 0");
        assertThat(posts.get(9).getTitle()).isEqualTo("규스 제목 - 9");
    }

    @Test
    @DisplayName("글 제목 수정")
    void test4() {
        // given
        Post post = Post.builder()
                .title("규스")
                .content("코딩잘하고싶다")
                .build();
        // 글 30개 저장
        Post savedPost = postRepository.save(post);
        // when
        PostEdit postEdit = PostEdit.builder()
                .title("수정된 제목")
                .content("반포자이")
                .build();
        postService.edit(savedPost.getId(), postEdit);
        // then
        PostResponse updatePostResponse = postService.get(savedPost.getId());
        assertThat(updatePostResponse).isNotNull();
        assertEquals(updatePostResponse.getTitle(), postEdit.getTitle());
        assertEquals(updatePostResponse.getContent(), postEdit.getContent());
    }

    @Test
    @DisplayName("글 내용 수정")
    void test5() {
        // given
        Post post = Post.builder()
                .title("규스")
                .content("코딩잘하고싶다")
                .build();
        // 글 30개 저장
        Post savedPost = postRepository.save(post);
        // when
        PostEdit postEdit = PostEdit.builder()
                .content("반포자이")
                .build();
        postService.edit(savedPost.getId(), postEdit);
        // then
        PostResponse updatePostResponse = postService.get(savedPost.getId());
        assertThat(updatePostResponse).isNotNull();
        assertEquals(updatePostResponse.getTitle(), postEdit.getTitle());
        assertEquals(updatePostResponse.getContent(), postEdit.getContent());
    }

    @Test
    @DisplayName("게시글 삭제")
    void test6() {
        // given
        Post post = Post.builder()
                .title("규스")
                .content("코딩잘하고싶다")
                .build();
        // 글 30개 저장
        Post savedPost = postRepository.save(post);
        // when
        postService.delete(savedPost.getId());
        // then
        Assertions.assertThatThrownBy(() -> postService.get(savedPost.getId())).isInstanceOf(IllegalArgumentException.class);
    }

}