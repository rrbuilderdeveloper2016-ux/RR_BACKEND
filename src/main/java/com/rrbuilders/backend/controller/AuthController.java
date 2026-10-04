package com.rrbuilders.backend.controller;

import com.rrbuilders.backend.dto.ApiResponse;
import com.rrbuilders.backend.dto.ChangePasswordRequest;
import com.rrbuilders.backend.dto.LoginRequest;
import com.rrbuilders.backend.dto.LoginResponse;
import com.rrbuilders.backend.entity.AdminUser;
import com.rrbuilders.backend.repository.AdminUserRepository;
import com.rrbuilders.backend.security.JwtUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final AdminUserRepository adminUserRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername().trim(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        AdminUser admin = adminUserRepository.findByUsernameOrEmail(userDetails.getUsername(), userDetails.getUsername())
                .orElseThrow(() -> new IllegalStateException("User found in authentication but not in database"));

        admin.setLastLoginAt(LocalDateTime.now());
        adminUserRepository.save(admin);

        String token = jwtUtils.generateToken(admin.getUsername(), admin.getRole());

        LoginResponse response = LoginResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .expiresIn(jwtUtils.getExpirationMs())
                .username(admin.getUsername())
                .email(admin.getEmail())
                .fullName(admin.getFullName())
                .role(admin.getRole())
                .build();

        return ResponseEntity.ok(ApiResponse.success("Admin authenticated successfully", response));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<AdminUser>> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails userDetails)) {
            return ResponseEntity.status(401).body(ApiResponse.error("Not authenticated"));
        }

        AdminUser admin = adminUserRepository.findByUsernameOrEmail(userDetails.getUsername(), userDetails.getUsername())
                .orElseThrow(() -> new IllegalStateException("Admin user record not found"));

        // Hide password in response
        admin.setPassword("[PROTECTED]");
        return ResponseEntity.ok(ApiResponse.success("Current user profile retrieved", admin));
    }

    @PostMapping("/change-password")
    public ResponseEntity<ApiResponse<Void>> changePassword(@Valid @RequestBody ChangePasswordRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails userDetails)) {
            return ResponseEntity.status(401).body(ApiResponse.error("Not authenticated"));
        }

        AdminUser admin = adminUserRepository.findByUsernameOrEmail(userDetails.getUsername(), userDetails.getUsername())
                .orElseThrow(() -> new IllegalStateException("Admin user record not found"));

        if (!passwordEncoder.matches(request.getCurrentPassword(), admin.getPassword())) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Current password does not match"));
        }

        admin.setPassword(passwordEncoder.encode(request.getNewPassword()));
        adminUserRepository.save(admin);

        return ResponseEntity.ok(ApiResponse.success("Password changed successfully", null));
    }
}
