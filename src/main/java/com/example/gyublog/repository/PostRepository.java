package com.example.gyublog.repository;

import com.example.gyublog.domain.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PostRepository extends MongoRepository<Post, Long>, PagingAndSortingRepository<Post, Long> {
}
