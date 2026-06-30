package com.frameslot.service;

import com.frameslot.domain.Role;
import com.frameslot.domain.User;
import com.frameslot.repository.UserRepository;
import com.frameslot.web.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {

    private final UserRepository userRepo;

    public CurrentUserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    /**
     * Resolves the currently authenticated user from the JWT SecurityContext.
     * The JWT filter stores the email as principal and userId as credentials.
     */
    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "Not authenticated");
        }

        // The principal is the email (set by JwtAuthenticationFilter)
        String email = (String) auth.getPrincipal();

        return userRepo.findByEmail(email)
                .orElseThrow(() -> new ApiException(HttpStatus.UNAUTHORIZED, "User not found"));
    }

    /**
     * Resolves a user by ID and verifies they have the expected role.
     * Used by services that need role-verified user lookup.
     */
    public User requireUser(Long userId, Role expectedRole) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "User not found"));
        if (user.getRole() != expectedRole) {
            throw new ApiException(HttpStatus.FORBIDDEN, "User does not have the required role: " + expectedRole);
        }
        return user;
    }
}
