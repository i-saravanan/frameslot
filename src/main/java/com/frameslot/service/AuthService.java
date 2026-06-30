package com.frameslot.service;

import com.frameslot.config.JwtService;
import com.frameslot.domain.Role;
import com.frameslot.domain.User;
import com.frameslot.repository.UserRepository;
import com.frameslot.web.ApiException;
import com.frameslot.web.dto.AuthDtos.AuthResponse;
import com.frameslot.web.dto.AuthDtos.LoginRequest;
import com.frameslot.web.dto.AuthDtos.RegisterRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepo;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepo, PasswordEncoder encoder, JwtService jwtService) {
        this.userRepo = userRepo;
        this.encoder = encoder;
        this.jwtService = jwtService;
    }

    public AuthResponse register(RegisterRequest req) {
        if (userRepo.existsByEmail(req.email()))
            throw new ApiException(HttpStatus.CONFLICT, "Email already registered");

        if (req.role() == Role.ADMIN)
            throw new ApiException(HttpStatus.FORBIDDEN, "Cannot register as ADMIN");

        User user = new User(req.name(), req.email(), encoder.encode(req.password()), req.phone(), req.role());
        userRepo.save(user);

        String token = jwtService.generateToken(user.getId(), user.getEmail(), user.getRole().name());
        return new AuthResponse(token, user.getId(), user.getName(), user.getEmail(), user.getRole());
    }

    public AuthResponse login(LoginRequest req) {
        User user = userRepo.findByEmail(req.email())
                .orElseThrow(() -> new ApiException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));

        if (!encoder.matches(req.password(), user.getPassword()))
            throw new ApiException(HttpStatus.UNAUTHORIZED, "Invalid credentials");

        String token = jwtService.generateToken(user.getId(), user.getEmail(), user.getRole().name());
        return new AuthResponse(token, user.getId(), user.getName(), user.getEmail(), user.getRole());
    }
}
