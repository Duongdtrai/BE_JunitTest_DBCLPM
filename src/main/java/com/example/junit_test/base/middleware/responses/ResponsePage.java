package com.example.junit_test.base.middleware.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePage<T> {
    private T data;
    private MetaData metadata;

    public ResponsePage(Page<T> page) {
        this.data = (T) page.getContent();
        this.metadata = new MetaData((int) page.getTotalElements(), page.getTotalPages(), page.getSize(), page.getNumber());
    }
}

