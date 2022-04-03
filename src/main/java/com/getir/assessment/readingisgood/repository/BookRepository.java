package com.getir.assessment.readingisgood.repository;

import com.getir.assessment.readingisgood.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface BookRepository extends MongoRepository<Book, String> {
    @Query("{'bookName': ?0}")
    Optional<Book> findByBookName(String bookName);
}
