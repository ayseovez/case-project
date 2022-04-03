package com.getir.assessment.readingisgood.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseResponse {
    private Integer responseStatus;
    private String responseMessage;
}
