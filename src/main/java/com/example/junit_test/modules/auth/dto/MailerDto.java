package com.example.junit_test.modules.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailerDto {
    private String to;
    private String subject;
    private String content;
}
