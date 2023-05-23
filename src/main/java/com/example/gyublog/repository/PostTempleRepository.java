package com.example.gyublog.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
@RequiredArgsConstructor
public class PostTempleRepository {
    private final MongoOperations mongoOperations;

    public void update() {
        mongoOperations.updateFirst(Query.query(where("_id").is(1)), Update.update("content", "수정된 글 내용"), "post");
    }
}
