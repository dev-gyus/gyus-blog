package com.example.gyublog.controller;

import com.example.gyublog.domain.Post;
import com.example.gyublog.repository.PostRepository;
import com.example.gyublog.request.PostCreate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


//@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    PostRepository postRepository;
    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void deleteAll() {
        postRepository.deleteAll();
    }

    // Content-Type=application/json
    @Test
    @DisplayName("/posts 요청시 Hello World를 출력한다.")
    void test() throws Exception {
        // given
        String title = "글 제목입니다";
        String content = "글 내용입니다.";
        PostCreate request = PostCreate
                .builder()
                .title(title)
                .content(content)
                .build();
        String convertedValue = objectMapper.writeValueAsString(request);
        // expected
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertedValue)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
        // then
//        Assertions.assertThat(postRepository.count()).isEqualTo(1L);
        List<Post> postList = postRepository.findAll();
        System.out.println(postList);
//        Assertions.assertThat(postList.size()).isEqualTo(1L);
//        Assertions.assertThat(postList.get(0).getTitle()).isEqualTo(title);
//        Assertions.assertThat(postList.get(0).getContent()).isEqualTo(content);
    }

    @Test
    @DisplayName("/posts 요청시 title값은 필수다.")
    void test2() throws Exception {
        PostCreate postCreate = PostCreate.builder()
                .title(null)
                .content("글 내용입니다")
                .build();
        // expected
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postCreate))
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청 입니다."))
                .andExpect(jsonPath("$.validation.title").value("타이틀 값은 필수 입니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("/posts 요청시 Hello World를 출력한다.")
    void test3() throws Exception {
        // expected
        String title = "글 제목입니다222";
        String content = "글내용입니다222.";
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"" + title + "\", \"content\":\"" + content  + "\"}")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
        // then
        Assertions.assertThat(postRepository.count()).isEqualTo(1L);
        List<Post> postList = postRepository.findAll();
        Assertions.assertThat(postList.size()).isEqualTo(1L);
        Assertions.assertThat(postList.get(0).getTitle()).isEqualTo(title);
        Assertions.assertThat(postList.get(0).getContent()).isEqualTo(content);
    }
//    @Test
//    @DisplayName("글 한 개 조회")
//    void test4() throws Exception {
//        // given
//        Post post = Post.builder()
//                .title("foo")
//                .content("bar")
//                .build();
//        postRepository.save(post);
//        // expected
//        mockMvc.perform(get("/posts/{postsId}", post.getId())
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(post)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.title").value(post.getTitle()))
//                .andExpect(jsonPath("$.content").value(post.getContent()))
//                .andDo(print());
//    }

    @Test
    @DisplayName("글 한 개 조회")
    void test4() throws Exception {
        // given
        Post post = Post.builder()
                .title("123456789012345")
                .content("bar")
                .build();
        postRepository.save(post);
        // expectedasdf
        // post의 응답값의 title은 10글자만 해달라는 요구사항이 온 경우
        mockMvc.perform(get("/posts/{postsId}", post.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(post)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(post.getTitle().length() < 10 ? post.getTitle() : post.getTitle().substring(0, 10)))
                .andExpect(jsonPath("$.content").value(post.getContent()))
                .andDo(print());
    }

    @Test
    @DisplayName("글 여러 개 조회")
    void test5() throws Exception {
        // given
        // 글 30개 생성
        List<Post> requestPosts = IntStream.range(0, 30).mapToObj(idx ->
                Post.builder()
                        .title("규스 제목 - " + idx)
                        .content("규스 내용 - " + idx)
                        .build()
        ).collect(Collectors.toList());
        postRepository.saveAll(requestPosts);
        // expected
        // post의 응답값의 title은 10글자만 해달라는 요구사항이 온 경우
        mockMvc.perform(get("/posts?page=1&limit=10", 1, 10)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("글 하나 수정")
    void test6() throws Exception {
        // given
        // 글 30개 생성
        Post post = Post.builder()
                .title("규스 제목 - ")
                .content("규스 내용 - ")
                .build();
        Post savedPost = postRepository.save(post);
        // expected
        // post의 응답값의 title은 10글자만 해달라는 요구사항이 온 경우
        mockMvc.perform(put("/post/" + savedPost.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }



}