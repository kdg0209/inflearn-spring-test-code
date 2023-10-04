package com.inflearn.testcode.controller;

import com.inflearn.testcode.model.dto.UserResponse;
import com.inflearn.testcode.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable("id") long id) {
        return ResponseEntity
                .ok()
                .body(null);
    }

    @GetMapping("/{id}/verify")
    public ResponseEntity<Void> verifyEmail(@PathVariable("id") long id, @RequestParam String certificationCode) {
        return ResponseEntity
                .ok()
                .body(null);
    }
}
