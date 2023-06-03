package com.example.gyublog.service;

import com.example.gyublog.domain.Post;
import com.example.gyublog.domain.PostEditor;
import com.example.gyublog.repository.PostRepository;
import com.example.gyublog.request.PostCreate;
import com.example.gyublog.request.PostEdit;
import com.example.gyublog.request.PostSearch;
import com.example.gyublog.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    @Transactional
    public void write(PostCreate postCreate) {
        Post post = postCreate.toDao();
        log.info("post = {}", post);
        postRepository.save(post);
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
        log.info("post = {}", post);
        // response build
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }

    /**
     * 게시글 목록 페이징 조회
     * @param postSearch    검색에 필요한 데이터 저장 객체
     * @return              응답 목록
     */
    public List<PostResponse> getPosts(PostSearch postSearch) {
        return postRepository.getList(postSearch)
                .stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void edit(Long id, PostEdit postEdit) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));
        log.info("post={}", post);
        PostEditor.PostEditorBuilder postEditorBuilder = postEdit.toEditor();
        post.edit(postEditorBuilder.build());
    }

    public void delete(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));
        postRepository.delete(post);
    }

    /**
     * Architecture Layer
     * PostController -> WebPostService (클라이언트 응답을 목적으로 두는 서비스) -> Repository
     *                -> PostService (외부 서비스와 통신할때를 목적으로 두는 서비스)
     */
}
