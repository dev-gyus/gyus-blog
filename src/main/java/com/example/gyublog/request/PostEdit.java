package com.example.gyublog.request;

import com.example.gyublog.domain.PostEditor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PostEdit {

    @NotBlank(message = "타이틀 값은 필수 입니다.")
    private String title;
    @NotBlank(message = "컨텐츠 값을 입력 해 주세요.")
    private String content;

    @Builder
    public PostEdit(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public PostEditor.PostEditorBuilder toEditor() {
        return PostEditor
                .builder()
                .title(title)
                .content(content);
    }
}
