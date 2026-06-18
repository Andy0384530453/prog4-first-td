package com.example.demo.controller;

import com.example.demo.dto.BookRequestDto;
import com.example.demo.dto.BookResponseDto;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.service.BookService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

  private final BookService bookService;

  @GetMapping
  public ResponseEntity<?> getAllBooks() {
    try {
      List<BookResponseDto> books = bookService.getAllBooks();
      return ResponseEntity.ok(books);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("server error");
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getBookById(@PathVariable UUID id) {
    try {
      BookResponseDto book = bookService.getBookById(id);
      return ResponseEntity.ok(book);
    } catch (ResourceNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("server error");
    }
  }

  @PostMapping
  public ResponseEntity<?> createBook(@RequestBody BookRequestDto requestDto) {
    try {
      BookResponseDto book = bookService.createBook(requestDto);
      return ResponseEntity.status(HttpStatus.CREATED).body(book);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or missing book data");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("server error");
    }
  }

  @GetMapping("/sold-today")
  public ResponseEntity<List<BookResponseDto>> getBooksSoldToday() {
    return ResponseEntity.ok(bookService.getBooksSoldToday());
  }
}
