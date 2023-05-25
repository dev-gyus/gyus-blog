package com.example.gyublog.config;

import com.example.gyublog.domain.Post;
import com.mongodb.client.model.changestream.ChangeStreamDocument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.mongodb.core.ChangeStreamOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.messaging.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
@RequiredArgsConstructor
public class MongoDbStreamConfig {
    private final MongoTemplate mongoTemplate;

    @PostConstruct
    public void init() {
        MessageListenerContainer container = new DefaultMessageListenerContainer(mongoTemplate);
        container.start();
        MessageListener<ChangeStreamDocument<Document>, Post> listener = (message) -> {
            log.info("post={}", message.getBody());
        };
        ChangeStreamRequest.ChangeStreamRequestOptions options = new ChangeStreamRequest.ChangeStreamRequestOptions("gyublog", "post", ChangeStreamOptions.empty());
        Subscription subscription = container.register(new ChangeStreamRequest<>(listener, options), Post.class);
    }
}
