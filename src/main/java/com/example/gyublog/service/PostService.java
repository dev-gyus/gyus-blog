package com.example.gyublog.service;

import com.example.gyublog.domain.Post;
import com.example.gyublog.repository.PostRepository;
import com.example.gyublog.repository.PostTempleRepository;
import com.example.gyublog.request.PostCreate;
import com.example.gyublog.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.domain.Sort.Direction.DESC;


@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostTempleRepository postTempleRepository;
    @Transactional
    public void write(PostCreate postCreate) {
//        postRepository.save(postCreate.toDao());
        postTempleRepository.update();
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
     * @param page  페이지 번호
     * @param limit 페이징 개수
     * @return          응답 목록
     */
    public List<PostResponse> getPosts(int page, int limit) {
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by(DESC, "id"));
        return postRepository.findAll(pageRequest).stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * Architecture Layer
     * PostController -> WebPostService (클라이언트 응답을 목적으로 두는 서비스) -> Repository
     *                -> PostService (외부 서비스와 통신할때를 목적으로 두는 서비스)
     */
}
