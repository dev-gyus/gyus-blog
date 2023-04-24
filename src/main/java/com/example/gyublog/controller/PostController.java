package com.example.gyublog.controller;

import com.example.gyublog.request.PostCreate;
import com.example.gyublog.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    // Http Methods = GET, POST, DELETE, PUT, PATCH, OPTIONS, CONNECT, HEAD
    @PostMapping("/posts")
    public Map<String, String> post(@Valid @RequestBody PostCreate request) {
        postService.write(request);
        return Map.of();
    }
}
