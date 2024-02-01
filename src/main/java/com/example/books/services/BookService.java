package com.example.books.services;

import java.util.List;
import java.util.Optional;

import com.example.books.domain.Book;

public interface BookService {

    // Creates a new book in the system
    Book save(Book book);

     // Finds a book by its ISBN
    Optional<Book> findById(String ibsn);

    // All books list
    List<Book> listBooks();

    boolean isBookExits(Book book);
    
}
