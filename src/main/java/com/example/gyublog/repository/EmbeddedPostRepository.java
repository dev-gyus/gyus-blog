package com.example.gyublog.repository;

import com.example.gyublog.domain.EmbeddedPost;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmbeddedPostRepository extends MongoRepository<EmbeddedPost, ObjectId> {
}
