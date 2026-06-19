package com.example.demo.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.demo.dto.BookCopyResponseDto;
import com.example.demo.entity.FormatType;
import com.example.demo.exception.GlobalExceptionHandler;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.service.BookCopyService;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(BookCopyController.class)
@Import(GlobalExceptionHandler.class)
class BookCopyControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private BookCopyService bookCopyService;

  @Test
  void getBookCopyById_shouldReturn200_whenExists() throws Exception {
    UUID id = UUID.randomUUID();

    BookCopyResponseDto dto =
        new BookCopyResponseDto(
            id,
            "978-3-16-148410-0",
            new BigDecimal("10.00"),
            new BigDecimal("15.00"),
            5,
            FormatType.POCHE,
            UUID.randomUUID(),
            "Test Book");

    when(bookCopyService.getBookCopyById(id)).thenReturn(dto);

    mockMvc
        .perform(get("/book-copies/" + id))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.isbn").value("978-3-16-148410-0"))
        .andExpect(jsonPath("$.currentStock").value(5));
  }

  @Test
  void getBookCopyById_shouldReturn404_whenNotExists() throws Exception {
    UUID id = UUID.randomUUID();

    when(bookCopyService.getBookCopyById(id))
        .thenThrow(new ResourceNotFoundException("BookCopy", id));

    mockMvc.perform(get("/book-copies/" + id)).andExpect(status().isNotFound());
  }

  @Test
  void getBookCopyById_shouldReturn400_whenInvalidUuid() throws Exception {
    mockMvc.perform(get("/book-copies/invalid-uuid")).andExpect(status().isBadRequest());
  }

  @Test
  void getAllBookCopies_shouldReturn200_withList() throws Exception {
    BookCopyResponseDto dto =
        new BookCopyResponseDto(
            UUID.randomUUID(),
            "978-3-16-148410-0",
            new BigDecimal("10.00"),
            new BigDecimal("15.00"),
            5,
            FormatType.POCHE,
            UUID.randomUUID(),
            "Test Book");

    when(bookCopyService.getAllBookCopies()).thenReturn(List.of(dto));

    mockMvc
        .perform(get("/book-copies"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].isbn").value("978-3-16-148410-0"));
  }

  @Test
  void getAllBookCopies_shouldReturn200_whenEmpty() throws Exception {
    when(bookCopyService.getAllBookCopies()).thenReturn(List.of());

    mockMvc
        .perform(get("/book-copies"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isEmpty());
  }
}
