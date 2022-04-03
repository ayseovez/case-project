package com.getir.assessment.readingisgood.service.impl;

import com.getir.assessment.readingisgood.exception.NotFoundException;
import com.getir.assessment.readingisgood.exception.ValidationException;
import com.getir.assessment.readingisgood.model.Book;
import com.getir.assessment.readingisgood.payload.request.UpdateStockRequest;
import com.getir.assessment.readingisgood.payload.response.CreateBookResponse;
import com.getir.assessment.readingisgood.payload.response.ReduceStockResponse;
import com.getir.assessment.readingisgood.payload.response.UpdateStockResponse;
import com.getir.assessment.readingisgood.repository.BookRepository;
import com.getir.assessment.readingisgood.service.BookService;
import com.getir.assessment.readingisgood.util.enums.ResponseInfoEnum;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    private final Logger LOGGER = LogManager.getLogger(BookServiceImpl.class);

    private final BookRepository bookRepository;
    private final MongoOperations mongoOperations;

    @Override
    public CreateBookResponse createBookInfo(Book book) {
        CreateBookResponse response = new CreateBookResponse();
        try {
            validate(book);
            existing(book.getBookName());
            Book bookModel = bookRepository.save(book);
            response.setId(bookModel.getId());
            response.setBookName(book.getBookName());
            response.setResponseStatus(ResponseInfoEnum.SUCCESS.getStatus());
            response.setResponseMessage(ResponseInfoEnum.SUCCESS.getMessage());
            LOGGER.info(book.getBookName()+" book registered");
        }catch (ValidationException e){
            response.setResponseStatus(ResponseInfoEnum.FAIL.getStatus());
            response.setResponseMessage(e.getMessage());
            LOGGER.error(e.getMessage());
        }catch (Exception e){
            response.setResponseStatus(ResponseInfoEnum.FAIL.getStatus());
            response.setResponseMessage(ResponseInfoEnum.FAIL.getMessage());
            LOGGER.error(e.getMessage());
        }
        return response;
    }

    private void validate(Book book) {
        if (book.getBookName().isEmpty() || Objects.isNull(book.getPrice()) || Objects.isNull(book.getStock())
            || Objects.isNull(book.getCategory()))
            throw new ValidationException("Please enter fields of Book completely.");
    }

    private void existing(String bookName) {
       bookRepository.findByBookName(bookName).ifPresent(s -> { throw new ValidationException("Book has already been registered."); });
    }

    @Override
    public UpdateStockResponse updateBookStock(UpdateStockRequest request) {
        UpdateStockResponse response = new UpdateStockResponse();
        try {
            Book book = bookRepository.findById(request.getId()).orElseThrow(() -> new NotFoundException(request.getId(), " Cannot find by id"));
            validate(book);
            book.setStock(request.getStock());
            bookRepository.save(book);
            response.setBookName(book.getBookName());
            response.setResponseStatus(ResponseInfoEnum.SUCCESS.getStatus());
            response.setResponseMessage(ResponseInfoEnum.SUCCESS.getMessage());
            LOGGER.info(response.getBookName()+" updates book's stock");
        }catch (ValidationException e){
            response.setResponseStatus(ResponseInfoEnum.FAIL.getStatus());
            response.setResponseMessage(e.getMessage());
            LOGGER.error(e.getMessage());
        }catch (Exception e){
            response.setResponseStatus(ResponseInfoEnum.FAIL.getStatus());
            response.setResponseMessage(ResponseInfoEnum.FAIL.getMessage());
            LOGGER.error(e.getMessage());
        }

        return response;
    }

    @Override
    public ReduceStockResponse reduceBookStock(List<Book> request) {
        ReduceStockResponse response = new ReduceStockResponse();
        List<String> bookNames= new ArrayList<>();
        try {
            request.forEach(e ->  {
                Book book = getBookInfo(e.getBookName());
                int quantity = e.getSoldQuantity();
                checkStock(book, quantity);
                Query query = new Query();
                query.addCriteria(Criteria.where("bookName").is(e.getBookName()));
                Update update = new Update();
                int newStock = book.getStock() - quantity;
                update.set("stock", newStock);
                mongoOperations.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), Book.class);
                LOGGER.info(book.getBookName()+ " reduced stock");
                bookNames.add(e.getBookName());
            });
            response.setBookNames(bookNames);
            response.setResponseStatus(ResponseInfoEnum.SUCCESS.getStatus());
            response.setResponseMessage(ResponseInfoEnum.SUCCESS.getMessage());
        }catch (Exception e){
            response.setResponseStatus(ResponseInfoEnum.FAIL.getStatus());
            response.setResponseMessage(e.getMessage());
            LOGGER.error(e.getMessage());
        }

        return response;
    }

    @Override
    public Book getBookInfo(String bookName) {
        return bookRepository.findByBookName(bookName).orElseThrow(() -> new NotFoundException(bookName, " Connot find by Book Name"));
    }

    @Override
    public int addQuantity(String bookName, int quantity) {
        int newQuantity = 0;
        try {
            Book book = getBookInfo(bookName);
            book.setSoldQuantity(book.getSoldQuantity()+quantity);
            bookRepository.save(book);
            newQuantity = book.getSoldQuantity();
            LOGGER.info("Quantity has been added " +bookName);
        }catch (Exception e){
            LOGGER.error("Not add quantity "+bookName);
        }
        return newQuantity;
    }


    private void checkStock(Book book, int count) {
        if (book.getStock() == 0 || book.getStock() < count)
            throw new NotFoundException(book.getBookName(), " Cannot find enough in stock");
    }


}
