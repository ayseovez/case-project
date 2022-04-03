package com.getir.assessment.readingisgood.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.getir.assessment.readingisgood.util.enums.BookCategoriesEnum;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Book {

    @Id
    private String id;
    private String bookName;
    private Double price;
    private int stock;
    private int soldQuantity;
    private String image;
    private String author;
    private String description;
    private BookCategoriesEnum category;
}
