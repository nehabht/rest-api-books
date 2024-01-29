package com.example.books.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.books.domain.Book;
import com.example.books.services.BookService;

@Controller
public class BookController {
    
    private final BookService bookService;

    // Constructor for the BookController class.
    // It is invoked when an instance of BookController is created,
    // and the BookService is automatically injected by Spring using the @Autowired annotation.
    @Autowired
    public BookController(final BookService bookService) {
        this.bookService = bookService;
    }


    /**
     * Handles the HTTP PUT request to create or update a book.
     *
     * @param isbn The ISBN of the book to be created or updated.
     * @param book The JSON payload containing book information in the request body.
     * @return ResponseEntity with the created or updated Book and the corresponding HTTP status.
     */
    @PutMapping(path = "/books/{isbn}")
    public ResponseEntity<Book> createBook(
        @PathVariable final String isbn, 
        @RequestBody final Book book){

            // Set the ISBN from the path variable to the book object
            book.setIsbn(isbn);

             // Call the create method of the BookService to create or update the book
            final Book saveBook = bookService.create(book);
             // Create a ResponseEntity with the saved book and HTTP status CREATED
            final ResponseEntity<Book> response = new ResponseEntity<Book>(saveBook, HttpStatus.CREATED))
            
            return response;

    }
}
