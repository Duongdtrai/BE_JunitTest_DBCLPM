package com.example.junit_test.base.middleware.exceptions;

import org.springframework.http.HttpStatus;

public class HttpErrorException extends Exception {
  private HttpStatus status;
  private String message;

  protected HttpErrorException(HttpStatus status, String message) {
    super(message);
    this.status = status;
    this.message = message;
  }

  private HttpErrorException(Exception e) {
    super(e);
    this.status = HttpStatus.INTERNAL_SERVER_ERROR;
    this.message = "Server error. Try again, please.";
  }

  public static HttpErrorException from(Exception e) {
    return new HttpErrorException(e);
  }

  public static HttpErrorException from(HttpStatus status, String message) {
    return new HttpErrorException(status, message);
  }

  public static HttpErrorException badRequest(String msg) {
    return from(HttpStatus.BAD_REQUEST, msg);
  }

  public static HttpErrorException conflict(String msg) {
    return from(HttpStatus.CONFLICT, msg);
  }

  public HttpStatus getStatus() {
    return status;
  }

  public void setStatus(HttpStatus status) {
    this.status = status;
  }

  @Override
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
