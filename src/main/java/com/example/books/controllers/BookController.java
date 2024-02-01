package com.example.books.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.books.domain.Book;
import com.example.books.services.BookService;

@RestController
public class BookController {
    
    private final BookService bookService;

    // Constructor for the BookController class.
    // It is invoked when an instance of BookController is created,
    // and the BookService is automatically injected by Spring using the @Autowired annotation, not required since its the only constructor
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
    public ResponseEntity<Book> createUpdateBook(
        @PathVariable final String isbn, 
        @RequestBody final Book book){

            // Set the ISBN from the path variable to the book object
            book.setIsbn(isbn);

            // Check if the book already exists
            final boolean isBookExits = bookService.isBookExits(book);

            // Save the book using the bookService
            final Book savedBook = bookService.save(book);

            
            // Return ResponseEntity with HTTP OK if the book exists, otherwise return HTTP CREATED
            if(isBookExits) {
                return new ResponseEntity<Book>(savedBook, HttpStatus.OK);
            } else {
                return new ResponseEntity<Book>(savedBook, HttpStatus.CREATED);
            }
         
            

    }


    /**
     * Handles the HTTP GET request to retrieve a book based on its ISBN.
     *
     * @param isbn The ISBN of the book to be retrieved.
     * @return ResponseEntity with the retrieved Book and HTTP status OK if found,
     *         or a ResponseEntity with HTTP status NOT_FOUND if the book is not found.
     */
    @GetMapping(path = "/books/{isbn}")
    public ResponseEntity<Book> retriveBook(@PathVariable final String isbn){

        // Retrieve a book from the service using the provided ISBN
        final Optional<Book> foundBook = bookService.findById(isbn); 

        // If the book is found, return a ResponseEntity with the book and HTTP status OK
        // Otherwise, return a ResponseEntity with HTTP status NOT_FOUND
        return foundBook.map(book -> new ResponseEntity<Book>(book,HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    /**
     * Handles the HTTP GET request to retrieve a list of all books.
     *
     * @return ResponseEntity with the list of books and HTTP status OK if found,
     *         or a ResponseEntity with HTTP status NOT_FOUND if no books are present.
     */
    @GetMapping(path = "/books")
    public ResponseEntity<List<Book>> listBooks(){
        return new ResponseEntity<List<Book>>(bookService.listBooks(), HttpStatus.OK);
    }
    

    @DeleteMapping(path="/books/{isbn}")
    public ResponseEntity deleteBook(@PathVariable final String isbn) {
        bookService.deleteBookById(isbn);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}