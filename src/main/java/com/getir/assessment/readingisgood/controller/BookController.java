package com.getir.assessment.readingisgood.controller;

import com.getir.assessment.readingisgood.model.Book;
import com.getir.assessment.readingisgood.payload.request.UpdateStockRequest;
import com.getir.assessment.readingisgood.payload.response.CreateBookResponse;
import com.getir.assessment.readingisgood.payload.response.UpdateStockResponse;
import com.getir.assessment.readingisgood.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/readingIsGood")
public class BookController {
    private final BookService bookService;

    @PostMapping("/createBook")
    public CreateBookResponse createBook(@RequestBody Book request){
        CreateBookResponse createBookResponse = bookService.createBookInfo(request);
        return createBookResponse;
    }
    @PostMapping("/updateStock")
    public UpdateStockResponse updateStock(@RequestBody UpdateStockRequest request){
        UpdateStockResponse updateBookStockResponse = bookService.updateBookStock(request);
        return updateBookStockResponse;
    }
}
