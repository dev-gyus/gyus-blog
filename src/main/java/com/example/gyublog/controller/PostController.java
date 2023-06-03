package com.example.gyublog.controller;

import com.example.gyublog.request.PostCreate;
import com.example.gyublog.request.PostEdit;
import com.example.gyublog.request.PostSearch;
import com.example.gyublog.response.PostResponse;
import com.example.gyublog.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    // Http Methods = GET, POST, DELETE, PUT, PATCH, OPTIONS, CONNECT, HEAD
    @PostMapping("/posts")
    public void post(@Valid @RequestBody PostCreate request) {
        postService.write(request);
    }

    // 글 단건 조회
    @GetMapping("/posts/{postId}")
    public PostResponse getPost(@PathVariable Long postId) {
        // Request 클래스
        // Response 클래스 분리
        return postService.get(postId);
    }

    // 글 목록 조회
    @GetMapping("/posts")
    public List<PostResponse> getPosts(@ModelAttribute PostSearch postSearch) {
        // Request 클래스
        // Response 클래스 분리
        return postService.getPosts(postSearch);
    }

    @PutMapping("/posts/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public void updatePost(@PathVariable Long postId, @RequestBody PostEdit postEdit) {
        // Request 클래스
        // Response 클래스 분리
        postService.edit(postId, postEdit);
    }

    @DeleteMapping("/posts/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePost(@PathVariable Long postId) {
        // Request 클래스
        // Response 클래스 분리
        postService.delete(postId);
    }
}
