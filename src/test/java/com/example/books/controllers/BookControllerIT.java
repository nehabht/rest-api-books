package com.example.books.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.books.TestData;
import com.example.books.domain.Book;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Integration test class for the BookController.
 * Tests the creation of a book using MockMvc to simulate HTTP requests.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class BookControllerIT {
    
    @Autowired
    private MockMvc mockMvc;

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

}