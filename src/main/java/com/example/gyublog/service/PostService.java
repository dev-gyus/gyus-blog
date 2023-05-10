package com.example.gyublog.service;

import com.example.gyublog.domain.Post;
import com.example.gyublog.repository.PostRepository;
import com.example.gyublog.request.PostCreate;
import com.example.gyublog.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    public void write(PostCreate postCreate) {
        postRepository.save(postCreate.toDao());
    }

    /**
     * 게시글 단건 조회
     * @param id    조회할 id
     * @return      조회 결과
     */
    public PostResponse get(Long id) {
        // find entity
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));
        // response build
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }

    /**
     * 게시글 목록 페이징 조회
     * @param pageable  페이징 객체
     * @return          응답 목록
     */
    public List<PostResponse> getPosts(Pageable pageable) {
        List<Post> postList = postRepository.findAll(pageable).getContent();
        return postList.stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * Architecture Layer
     * PostController -> WebPostService (클라이언트 응답을 목적으로 두는 서비스) -> Repository
     *                -> PostService (외부 서비스와 통신할때를 목적으로 두는 서비스)
     */
}
