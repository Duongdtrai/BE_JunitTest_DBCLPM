package com.example.junit_test.base.middleware.in;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Paging<T> {
    private int size;

    private int page;

    private int totalPage;

    private int totalRecord;

    private T data;
}
