package com.example.books.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.books.domain.Book;
import com.example.books.domain.BookEntity;
import com.example.books.repositories.BookRepository;
import com.example.books.services.BookService;

@Service
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;

    // Inject the repository into the service through the constructor
    @Autowired
    public BookServiceImpl(final BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    @Override
    public Book save(final Book book){

        // Create a BookEntity from the book data
        final BookEntity bookEntity =bookToBookEntity(book);

        // Save the entity to the database using the repository
        final BookEntity savedBookEntity = bookRepository.save(bookEntity);
       
        // Convert the saved entity back to a Book object
        return bookEntityToBook(savedBookEntity);
    }

    // Convert a Book object to a BookEntity
    private BookEntity bookToBookEntity(Book book){
        return BookEntity.builder()
            .isbn(book.getIsbn())
            .title(book.getTitle())
            .author(book.getAuthor())
            .build();
    }

    // Convert a BookEntity to a Book object
    private Book bookEntityToBook (BookEntity bookEntity){
        return Book.builder()
            .isbn(bookEntity.getIsbn())
            .title(bookEntity.getTitle())
            .author(bookEntity.getAuthor())
            .build();
    }

    @Override
    public Optional<Book> findById(String ibsn) {

        // Find a book entity by its ISBN using the repository
        final Optional<BookEntity> foundBook = bookRepository.findById(ibsn);

        // Map the found BookEntity to a Book if present
        return foundBook.map(book -> bookEntityToBook(book));
    }

    //Retrieves a list of all books from the repository.
    @Override
    public List<Book> listBooks() {

        // Retrieve all book entities from the repository
        final List<BookEntity> foundBooks = bookRepository.findAll();

        // Map the list of BookEntity objects to a list of Book objects
        return foundBooks.stream().map(book -> bookEntityToBook(book)).collect(Collectors.toList());
    }

    @Override
    public boolean isBookExits(Book book) {
        return bookRepository.existsById(book.getIsbn());
    }

    //Deletes a book from the repository based on its ISBN.
    @Override
    public void deleteBookById(String isbn) {
        bookRepository.deleteById(isbn);
    }
    

    


}
