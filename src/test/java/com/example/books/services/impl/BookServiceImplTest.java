package com.example.books.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.Optional;

import static com.example.books.TestData.testBook;
import static com.example.books.TestData.testBookEntity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.books.domain.Book;
import com.example.books.domain.BookEntity;
import com.example.books.repositories.BookRepository;


/**
 * Unit test for the BookServiceImpl class.
 *
 * This test ensures that the BookServiceImpl's create method properly saves a BookEntity
 * using the BookRepository, and that the returned Book matches the expected result.
 */
@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl underTest;

    /**
     * Tests that a Book is saved correctly using the create method of BookServiceImpl.
     */
    @Test
    public void testThatBookIsSaved(){
        // Create a sample Book
        final Book book = testBook();

        // Create the corresponding BookEntity for comparison
        final BookEntity bookEntity = testBookEntity();

        // Configure the mock behavior for the save method
        when(bookRepository.save(eq(bookEntity))).thenReturn(bookEntity);

        // Invoke the create method of BookServiceImpl
        final Book result = underTest.create(book);

        // Verify that the result matches the expected Book
        // assertEquals(book,null); //expected error
        assertEquals(book,result);


    }

    // Test to ensure that the findById method returns an empty Optional when no book is found.
    @Test
    public void testThatFindByIdReturnsEmptyWhenNoBook(){

        // Imagine searching for a book with a fictional ISBN that doesn't exist (123123123).
        final String isbn = "123123123";

        // Configure the mock repository to return an empty Optional when findById is called with the specified ISBN.
        when(bookRepository.findById(eq(isbn))).thenReturn(Optional.empty());
        
        // Now, when the service looks for that book, we expect it not to find it and return an empty Optional.
        final Optional<Book> result = underTest.findById(isbn);

        // Verify that the result is an empty Optional, as we expect the book not to be present.
        assertEquals(Optional.empty(), result);
    }

    
}
