package com.example.gyublog.repository;

import com.example.gyublog.domain.Post;
import com.example.gyublog.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {
    List<Post> getList(PostSearch page);
}
