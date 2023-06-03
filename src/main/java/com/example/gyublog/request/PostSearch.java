package com.example.gyublog.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PostSearch {
    @Builder.Default
    private int page = 1;
    @Builder.Default
    private int size = 10;

    public long getOffset() {
        return (long) Math.max(0, (this.page - 1)) * 10;
    }

}
