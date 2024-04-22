package com.example.junit_test.config;

import com.example.junit_test.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@RequiredArgsConstructor
@Slf4j
public class ApplicationConfig {
  private final UserRepository userRepository;

  @Bean
  public UserDetailsService userDetailsService() { // hàm này khởi tạo khi chạy project
    log.warn("2: User Details Service");
    return email -> {
      log.warn("2 Username: " + email);
      UserDetails userDetails = userRepository.findByEmail(email)
              .orElseThrow(() -> new UsernameNotFoundException("User not found"));
      return userDetails;
    };
  }

  @Bean // duoc su dung trong EnableWebSecurity
  public AuthenticationProvider authenticationProvider() { // hàm này khởi tạo khi chạy project
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService());
    authProvider.setPasswordEncoder(passwordEncoder());
    log.warn("3 AuthenticationProvider: " + authProvider);
    return authProvider;
  }

  @Bean // duoc su dung trong viec
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception { // hàm này khởi tạo khi chạy project
    log.warn("4 AuthenticationManager: " + config.getAuthenticationManager());
    return config.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
