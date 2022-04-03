package com.getir.assessment.readingisgood.service;

import com.getir.assessment.readingisgood.model.Book;
import com.getir.assessment.readingisgood.payload.request.UpdateStockRequest;
import com.getir.assessment.readingisgood.payload.response.CreateBookResponse;
import com.getir.assessment.readingisgood.payload.response.ReduceStockResponse;
import com.getir.assessment.readingisgood.payload.response.UpdateStockResponse;

import java.util.List;

public interface BookService {
    CreateBookResponse createBookInfo(Book book);
    UpdateStockResponse updateBookStock(UpdateStockRequest request);
    ReduceStockResponse reduceBookStock(List<Book> request);
    Book getBookInfo(String bookName);
    int addQuantity(String bookName, int quantity);
}
