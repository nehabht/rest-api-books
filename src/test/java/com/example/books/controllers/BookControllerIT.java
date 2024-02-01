package com.example.books.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.books.TestData;
import com.example.books.domain.Book;
import com.example.books.services.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Integration test class for the BookController.
 * Tests the creation of a book using MockMvc to simulate HTTP requests.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class BookControllerIT {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookService bookService;

    /**
     * Tests that a book is successfully created using the HTTP PUT request.
     *
     * @throws Exception If an error occurs during the test execution.
     */
    @Test
    public void testThatBookIsCreated() throws Exception{

         // Create a test Book object
        final Book book = TestData.testBook();

        // Convert the Book object to JSON format
        final ObjectMapper objectMapper = new ObjectMapper();
        final String bookJson = objectMapper.writeValueAsString(book);

        // Perform the HTTP PUT request to create the book
        mockMvc.perform(MockMvcRequestBuilders.put("/books/" + book.getIsbn())
        .contentType(MediaType.APPLICATION_JSON)
        .content(bookJson))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(book.getIsbn()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(book.getTitle()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(book.getAuthor()));

    }

    ////////////////////////////

    /**
     * Tests that a book is not successfully retrived and status 404
     *
     * @throws Exception If an error occurs during the test execution.
     */
    @Test
    public void testThatRetriveBookReturns404WhenBookNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/books/123123123"))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Tests that a book is successfully retrived and status 200
     *
     * @throws Exception If an error occurs during the test execution.
     */
    @Test
    public void testThatRetriveBookReturnsHttp200AndBookWhenExists() throws Exception {

        final Book book = TestData.testBook();
        bookService.save(book);

        mockMvc.perform(MockMvcRequestBuilders.get("/books/" + book.getIsbn()))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(book.getIsbn()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(book.getTitle()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(book.getAuthor()));


    }

    /////////////////////////

    /**
     * Tests that the listBooks endpoint returns HTTP 200 and an empty list when no books exist.
     *
     * @throws Exception If an error occurs during the test execution.
     */
    @Test
    public void testThatListBookReturnsHttp200EmptyListWhenNoBookExists() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/books"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string("[]"));

    }

    /**
     * Tests that the listBooks endpoint returns HTTP 200 and a list of books when books exist in the repository.
     *
     * @throws Exception If an error occurs during the test execution.
     */
    @Test
    public void testThatListBooksReturnHttp200AndBooksWhenBooksExist() throws Exception {

        
        // Create a test book and save it to the repository
        final Book book = TestData.testBook();
        bookService.save(book);

        // Perform a GET request to the listBooks endpoint
        mockMvc.perform(MockMvcRequestBuilders.get("/books"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.[0].isbn").value(book.getIsbn()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.[0].title").value(book.getTitle()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.[0].author").value(book.getAuthor()));

    }

    ////////////

    /**
     * Tests that updating a book via HTTP PUT returns HTTP 201.
     *
     * - Create a test Book object and save it to the database.
     * - Update the author of the test book.
     * - Convert the updated Book object to JSON format.
     * - Perform an HTTP PUT request to update the book.
     * - Verify that the HTTP status is OK (200).
     * - Verify that the updated book attributes match the expected values.
     */
    @Test
    public void testThatBookIsUpdatedReturnsHTTP201() throws Exception{

         // Create a test Book object
        final Book book = TestData.testBook();

        // Save the test book to the database
        bookService.save(book);

        // Update the author of the test book
        book.setAuthor("mary shelly");

        // Convert the Book object to JSON format
        final ObjectMapper objectMapper = new ObjectMapper();
        final String bookJson = objectMapper.writeValueAsString(book);

        // Perform the HTTP PUT request to update the book
        mockMvc.perform(MockMvcRequestBuilders.put("/books/" + book.getIsbn())
        .contentType(MediaType.APPLICATION_JSON)
        .content(bookJson))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(book.getIsbn()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(book.getTitle()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(book.getAuthor()));

    }









}