package com.bhargava.expense_tracker.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.bhargava.expense_tracker.dto.ProfileResponse;
import com.bhargava.expense_tracker.dto.UpdateProfileRequest;
import com.bhargava.expense_tracker.entity.User;
import com.bhargava.expense_tracker.repository.UserRepository;

@Service
public class ProfileService {

    private final UserRepository userRepository;

    public ProfileService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private User getLoggedInUser() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Get Profile
    public ProfileResponse getProfile() {

        User user = getLoggedInUser();

        return ProfileResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    // Update Profile
    public ProfileResponse updateProfile(UpdateProfileRequest request) {

        User user = getLoggedInUser();

        user.setName(request.getName());

        userRepository.save(user);

        return ProfileResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

}