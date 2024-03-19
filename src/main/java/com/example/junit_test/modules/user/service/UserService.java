package com.example.junit_test.modules.user.service;

import com.example.junit_test.base.middleware.responses.Response;
import com.example.junit_test.base.middleware.responses.ResponsePage;
import com.example.junit_test.base.middleware.responses.SystemResponse;
import com.example.junit_test.modules.user.dto.ChangePasswordDto;
import com.example.junit_test.modules.user.dto.UpdateDto;
import com.example.junit_test.modules.user.entities.User;
import com.example.junit_test.modules.user.repository.TokenRepository;
import com.example.junit_test.modules.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
//    @Value("${aws.s3.url}")
//    private String s3Url;
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
//    private final S3Service s3Service;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<SystemResponse<ResponsePage<User>>> list(int page, int size) {
        try {
            Pageable paging = PageRequest.of(page, size);
            Page<User> userPage = repository.findAll(paging);
            return Response.ok(new ResponsePage<User>(userPage));
        } catch (Exception e) {
            return Response.badRequest(500, e.getMessage());
        }
    }

    public ResponseEntity<SystemResponse<User>> retrieveBy() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) authentication.getPrincipal();
            return Response.ok(user);
        } catch (Exception e) {
            return Response.badRequest(500, e.getMessage());
        }
    }

    public ResponseEntity<SystemResponse<User>> update(UpdateDto data) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) authentication.getPrincipal();
            repository.save(user);
            return Response.ok(user);
        } catch (Exception e) {
            return Response.badRequest(500, e.getMessage());
        }
    }

    public ResponseEntity<SystemResponse<Boolean>> delete() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) authentication.getPrincipal();
            user.setStatus(false);
            repository.save(user);
            return Response.ok(true);
        } catch (Exception e) {
            return Response.badRequest(500, e.getMessage());
        }
    }


    public ResponseEntity<SystemResponse<Boolean>> changePassword(ChangePasswordDto data) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) authentication.getPrincipal();
            user.setPassword(passwordEncoder.encode(data.getPassword()));
            repository.save(user);
            return Response.ok(true);
        } catch (Exception e) {
            return Response.badRequest(500, e.getMessage());
        }
    }
    public ResponseEntity<SystemResponse<Boolean>> logout(HttpServletRequest request, Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            String token = request.getHeader("Authorization").substring(7);
            tokenRepository.deleteByUserIdAndToken(user.getId(), token);
            return Response.ok(true);
        } catch (Exception e) {
            return Response.badRequest(500, e.getMessage());
        }
    }
}
