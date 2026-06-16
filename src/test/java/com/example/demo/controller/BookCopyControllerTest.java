package com.example.demo.controller;

import com.example.demo.entity.Book;
import com.example.demo.entity.BookCopy;
import com.example.demo.entity.FormatType;
import com.example.demo.service.BookCopyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookCopyController.class)
class BookCopyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookCopyService bookCopyService;

    @Test
    void getBookCopyById_shouldReturn200_whenExists() throws Exception {
        UUID id = UUID.randomUUID();

        BookCopy bookCopy = BookCopy.builder()
                .id(id)
                .isbn("978-3-16-148410-0")
                .purchasePrice(new BigDecimal("10.00"))
                .sellingPrice(new BigDecimal("15.00"))
                .currentStock(5)
                .formatType(FormatType.POCHE)
                .book(new Book())
                .build();

        when(bookCopyService.findById(id)).thenReturn(Optional.of(bookCopy));

        mockMvc.perform(get("/book-copies/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isbn").value("978-3-16-148410-0"))
                .andExpect(jsonPath("$.currentStock").value(5));
    }

    @Test
    void getBookCopyById_shouldReturn404_whenNotExists() throws Exception {
        UUID id = UUID.randomUUID();

        when(bookCopyService.findById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/book-copies/" + id))
                .andExpect(status().isNotFound());
    }
}