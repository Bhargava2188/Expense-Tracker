package com.bhargava.expense_tracker.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bhargava.expense_tracker.dto.ProfileResponse;
import com.bhargava.expense_tracker.dto.UpdateProfileRequest;
import com.bhargava.expense_tracker.service.ProfileService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    // Get Logged-in User Profile
    @GetMapping
    public ResponseEntity<ProfileResponse> getProfile() {

        return ResponseEntity.ok(profileService.getProfile());

    }

    // Update Profile
    @PutMapping
    public ResponseEntity<ProfileResponse> updateProfile(
            @Valid @RequestBody UpdateProfileRequest request) {

        return ResponseEntity.ok(
                profileService.updateProfile(request)
        );

    }

}