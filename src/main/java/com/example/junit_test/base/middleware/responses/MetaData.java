package com.example.junit_test.base.middleware.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetaData {
  private int totalElements;
  private int totalPages;
  private int size;
  private int page;
}