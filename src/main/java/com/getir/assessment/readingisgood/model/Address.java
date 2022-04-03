package com.getir.assessment.readingisgood.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Address {
    private String country;
    private String city;
    private String town;
    private String postalCode;
    private String addressDetail;
}
