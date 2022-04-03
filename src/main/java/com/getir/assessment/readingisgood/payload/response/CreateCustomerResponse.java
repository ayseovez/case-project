package com.getir.assessment.readingisgood.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateCustomerResponse extends BaseResponse implements Serializable {
    private String firstName;
    private String lastName;
}
