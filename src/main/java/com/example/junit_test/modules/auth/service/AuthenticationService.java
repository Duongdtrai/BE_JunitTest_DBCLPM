package com.example.junit_test.modules.auth.service;

import com.example.junit_test.base.middleware.responses.Response;
import com.example.junit_test.base.middleware.responses.SystemResponse;
import com.example.junit_test.enums.Role;
import com.example.junit_test.modules.auth.dto.*;
import com.example.junit_test.modules.user.entities.Token;
import com.example.junit_test.modules.user.entities.User;
import com.example.junit_test.modules.user.repository.TokenRepository;
import com.example.junit_test.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    @Autowired
    private final UserRepository repository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private MailerService mailerService;


    public ResponseEntity<SystemResponse<SignUpResponseDto>> register(RegisterRequestDto request) {
        Optional<User> userExist = repository.findByEmail(request.getEmail());
        if (userExist.isPresent()) {
            return Response.badRequest("User is already exist");
        }
        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.STAFF)
                .status(true)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return Response.ok(SignUpResponseDto.builder().token(jwtToken).build());
    }

    public ResponseEntity<SystemResponse<AuthenticationResponseDto>> login(AuthenticationRequestDto request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        var user = repository.findByEmail(request.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("Email is not exist")
        );
        var accessToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateToken(user);

        // save token
        Token token = new Token();
        token.setToken(accessToken);
        token.setUserId(user.getId());
        tokenRepository.save(token);
        return Response.ok(new AuthenticationResponseDto(accessToken, refreshToken, user));
    }


    public ResponseEntity<SystemResponse<Boolean>> forgotPassword(ForgetPasswordDto data) {
        try {
            User user = this.repository.findByEmailAndStatus(data.getEmail(), true);
            // check email in system
            if (user == null) {
                return Response.badRequest(400, "User not found in system");
            }
            LocalDateTime currentDateTime = LocalDateTime.now();
            LocalDateTime newDateTime = currentDateTime.plusMinutes(5);
            Date expiredDate = Date.from(newDateTime.atZone(java.time.ZoneId.systemDefault()).toInstant());
            String otp = generateOTP();
            log.error("otp: " + otp);
            log.error("expiredDate: " + expiredDate);
            user.setOtp(otp);
            user.setExpiredDate(expiredDate);
            mailerService.sendMail(new MailerDto(data.getEmail(), "OTP", "Otp:" + otp));
            repository.save(user);
            return Response.ok(true);
        } catch (Exception e) {
            return Response.badRequest(500, e.getMessage());
        }
    }

    public ResponseEntity<SystemResponse<Boolean>> otp(OtpDto data) {
        try {
            User user = this.repository.findByEmailAndStatus(data.getEmail(), true);
            if (user == null) {
                return Response.badRequest(400, "User not found in system");
            }

            if (user.getOtp() == null || user.getOtp().isEmpty()) {
                return Response.badRequest(400, "User not otp in system");
            }
            Date currentDate = new Date();
            if (!user.getOtp().equals(data.getOtp()) || user.getExpiredDate().compareTo(currentDate) < 0) {
                user.setOtp(null);
                user.setExpiredDate(null);
                this.repository.save(user);
                return Response.badRequest(400, "Otp is expired or not exist in system");
            }
            String newPassword = this.generateRandomPassword();
            user.setPassword(passwordEncoder.encode(newPassword));
            user.setOtp(null);
            user.setExpiredDate(null);
            mailerService.sendMail(new MailerDto(data.getEmail(), "NEW PASSWORD", "Password: " + newPassword));
            this.repository.save(user);
            return Response.ok(true);
        } catch (Exception e) {
            return Response.badRequest(500, e.getMessage());
        }
    }


    private String generateOTP() {
        String digits = "0123456789";
        Random random = new Random();
        StringBuilder otpBuilder = new StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            int randomIndex = random.nextInt(digits.length());
            otpBuilder.append(randomIndex);
        }
        return otpBuilder.toString();
    }

    private String generateRandomPassword() {
        String character = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+";
        Random random = new SecureRandom();
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            int randomIndex = random.nextInt(character.length());
            char randomChar = character.charAt(randomIndex);
            password.append(randomChar);
        }
        return password.toString();
    }
}
