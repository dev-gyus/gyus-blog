package com.example.gyublog.controller;

import com.example.gyublog.domain.Post;
import com.example.gyublog.repository.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class PostControllerAdviceTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    PostRepository postRepository;

    @BeforeEach
    void deleteAll() {
        postRepository.deleteAll();
    }

    // Content-Type=application/json
    @Test
    @DisplayName("/posts 요청시 Hello World를 출력한다.")
    void test() throws Exception {
        // expected
        String title = "글 제목입니다";
        String content = "글내용입니다.";
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
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

    @Test
    @DisplayName("/posts 요청시 title값은 필수다.")
    void test2() throws Exception {
        // expected
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":null, \"content\":\"글내용입니다\"}")
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
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
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

}