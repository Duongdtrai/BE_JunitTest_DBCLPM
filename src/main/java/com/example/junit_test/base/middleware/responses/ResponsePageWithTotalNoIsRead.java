package com.example.junit_test.base.middleware.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePageWithTotalNoIsRead<T> {
  private T data;
  private CustomMetadata metadata;

  public ResponsePageWithTotalNoIsRead(Page<T> page, int totalNoIsRead) {
    this.data = (T) page.getContent();
    this.metadata = new CustomMetadata(
            (int) page.getTotalElements(),
            page.getTotalPages(),
            page.getSize(),
            page.getNumber(),
            totalNoIsRead
    );
  }
}


@Data
@NoArgsConstructor
@AllArgsConstructor
class CustomMetadata extends MetaData {
  private int totalNoIsRead;

  public CustomMetadata(int totalElements, int totalPages, int size, int page, int totalNoIsRead) {
    super(totalElements, totalPages, size, page);
    this.totalNoIsRead = totalNoIsRead;
  }
}