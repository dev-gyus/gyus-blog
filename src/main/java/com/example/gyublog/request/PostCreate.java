package com.example.gyublog.request;

import com.example.gyublog.domain.Post;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PostCreate {
    @NotBlank(message = "타이틀 값은 필수 입니다.")
    private String title;
    @NotBlank(message = "컨텐츠 값을 입력 해 주세요.")
    private String content;

    public Post toDao() {
        return new Post(null, this.title, this.content);
    }
}
