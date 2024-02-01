package com.example.books.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
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

    // Test to ensure that the findById method returns a book when it exists in the repository.
    @Test
    public void testThatFindByIdReturnsEmptyWhenNBookExists(){

        // Create a test Book and BookEntity
        final Book book = testBook();
        final BookEntity bookEntity = testBookEntity();
       
        // Configure the mock repository to return an Optional containing the BookEntity when findById is called with the book's ISBN.
        when(bookRepository.findById(eq(book.getIsbn()))).thenReturn(Optional.of(bookEntity));
        
        // Call the findById method of the service to find the book
        final Optional<Book> result = underTest.findById(book.getIsbn());

        // Verify that the result is an Optional containing the expected Book
        assertEquals(Optional.of(book), result);
    }

    /**
     * Tests that the listBooks method returns an empty list when no books exist.
     */
    @Test
    public void testListBooksReturnsEmptyListWhenNoBooksExists(){

        // Mock the behavior of bookRepository.findAll() to return an empty list by default
        when(bookRepository.findAll()).thenReturn(new ArrayList<BookEntity>()); // default behavior line can be ommitted 
        
        // Call the listBooks method to retrieve the list of books
        final List<Book> result = underTest.listBooks();

         // Assert that the size of the returned list is 0, indicating that no books were found
        assertEquals(0, result.size());
        
    }

    /**
     * Tests that the listBooks method returns a list of books when books exist in the repository.
     */
    @Test
    public void testListBookReturnsBooksWhenExist(){
        
        // Create a test BookEntity using the testBookEntity method
        final BookEntity bookEntity = testBookEntity();

        // Mock the behavior of bookRepository.findAll() to return a list containing a test BookEntity
        when(bookRepository.findAll()).thenReturn(List.of(bookEntity));

        // Call the listBooks method to retrieve the list of books
        final List<Book> result = underTest.listBooks();

        // Assert that the size of the returned list is 1, indicating that one book was found
        assertEquals(1, result.size());
    }

    

    
}
