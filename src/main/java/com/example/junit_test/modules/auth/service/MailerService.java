package com.example.junit_test.modules.auth.service;

import com.example.junit_test.modules.auth.dto.MailerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailerService {
  @Autowired
  private JavaMailSender emailSender;
  @Value("${spring.mail.username}")
  private String emailFrom;

  public void sendMail(MailerDto mailer) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom(emailFrom);
    message.setTo(mailer.getTo());
    message.setSubject(mailer.getSubject());
    message.setText(mailer.getContent());
    emailSender.send(message);
  }
}
