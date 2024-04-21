package com.example.junit_test.modules.user.controllers;

import com.example.junit_test.base.middleware.responses.ResponsePage;
import com.example.junit_test.base.middleware.responses.SystemResponse;
import com.example.junit_test.modules.user.dto.ChangePasswordDto;
import com.example.junit_test.modules.user.dto.UpdateDto;
import com.example.junit_test.modules.user.entities.User;
import com.example.junit_test.modules.user.service.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
@SecurityRequirement(name = "Authorization")
@Tag(name = "Users", description = "Users APIs")
@Hidden
public class UserController {
    @Autowired
    private final UserService service;

    @GetMapping("/list")
    public ResponseEntity<SystemResponse<ResponsePage<User>>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return service.list(page, size);
    }

    @GetMapping("/detail")
    public ResponseEntity<SystemResponse<User>> retrieveBy() {
        return service.retrieveBy();
    }

    @PatchMapping("/change-info")
    public ResponseEntity<SystemResponse<User>> update(@RequestBody UpdateDto data) {
        return service.update(data);
    }

    @DeleteMapping("/delete-account")
    public ResponseEntity<SystemResponse<Boolean>> delete() {
        return service.delete();
    }


    @PostMapping("/change-password")
    public ResponseEntity<SystemResponse<Boolean>> changePassword(@RequestBody ChangePasswordDto changePasswordDto) {
        return service.changePassword(changePasswordDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<SystemResponse<Boolean>> logout(HttpServletRequest request, Authentication authentication) {
        return service.logout(request, authentication);
    }
}
