package com.getir.assessment.readingisgood.controller;

import com.getir.assessment.readingisgood.payload.request.TokenRequest;
import com.getir.assessment.readingisgood.payload.response.TokenResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/readingIsGood/auth")
public class AuthenticationController {

    @PostMapping("/v1/token")
    public TokenResponse refreshtoken(@RequestBody TokenRequest request) {
        return null;
    }
}
