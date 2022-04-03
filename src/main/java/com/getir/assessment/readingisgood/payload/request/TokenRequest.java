package com.getir.assessment.readingisgood.payload.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TokenRequest {

    private String accessToken;
    private String tokenType = "Bearer";
}
