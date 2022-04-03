package com.getir.assessment.readingisgood.payload.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
public class TokenResponse implements Serializable {
    private String accessToken;
    private String tokenType = "Bearer";
}
