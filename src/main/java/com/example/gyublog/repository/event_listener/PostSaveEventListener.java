package com.example.gyublog.repository.event_listener;

import com.example.gyublog.domain.Post;
import com.example.gyublog.repository.EmbeddedPostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mapping.callback.EntityCallback;

@Slf4j
@RequiredArgsConstructor
public class PostSaveEventListener implements EntityCallback<Post> {
    private final EmbeddedPostRepository embeddedPostRepository;



//    @Override
//    public void onBeforeConvert(BeforeConvertEvent<Post> event) {
//        super.onBeforeConvert(event);
//        log.info("before convert = {}", event.getSource());
//    }
//
//    @Override
//    public void onBeforeSave(BeforeSaveEvent<Post> event) {
//        super.onBeforeSave(event);
//        log.info("before save = {}", event.getSource());
//    }
//
//    @Override
//    public void onAfterSave(AfterSaveEvent<Post> event) {
//        super.onAfterSave(event);
//        if (TransactionSynchronizationManager.isActualTransactionActive()) {
//            Post savePost = event.getSource();
//            log.info("after save = {}", savePost);
//            EmbeddedPost embeddedPostDao = EmbeddedPost.builder()
//                    .postId(savePost.getId())
//                    .postContent(savePost.getContent())
//                    .build();
//            EmbeddedPost savedEmbeddedPost = embeddedPostRepository.save(embeddedPostDao);
//            log.info("embeddedPost = {}", savedEmbeddedPost);
//        }
//    }
//
//    @Override
//    public void onAfterLoad(AfterLoadEvent<Post> event) {
//        super.onAfterLoad(event);
//        log.info("after load = {}", event.getSource());
//    }
}
