package com.example.demo.controller;

import com.example.demo.dto.StockByFormatEntry;
import com.example.demo.dto.StockRequest;
import com.example.demo.dto.StockResponse;
import com.example.demo.dto.StockUpdateResponse;
import com.example.demo.entity.FormatType;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.service.BookCopyService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books/{bookId}/copies")
@RequiredArgsConstructor
public class BookCopyStockController {

  private final BookCopyService bookCopyService;

  @GetMapping("/stock")
  public ResponseEntity<?> getStock(
      @PathVariable String bookId, @RequestParam(required = false) String format) {
    try {
      UUID bookUuid = UUID.fromString(bookId);
      if (format != null && !format.isBlank()) {
        FormatType formatType = FormatType.valueOf(format.toUpperCase());
        StockResponse response = bookCopyService.getStockByBookAndFormat(bookUuid, formatType);
        return ResponseEntity.ok(response);
      }
      List<StockByFormatEntry> response = bookCopyService.getStockGroupedByFormat(bookUuid);
      return ResponseEntity.ok(response);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body("Invalid UUID or format: " + e.getMessage());
    } catch (ResourceNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
  }

  @PostMapping("/{copyId}/decrement")
  public ResponseEntity<?> decrementStock(
      @PathVariable String bookId, @PathVariable String copyId, @RequestBody StockRequest request) {
    try {
      UUID copyUuid = UUID.fromString(copyId);
      StockUpdateResponse response = bookCopyService.decrementStock(copyUuid, request.quantity());
      return ResponseEntity.ok(response);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    } catch (ResourceNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
  }

  @PostMapping("/{copyId}/increment")
  public ResponseEntity<?> incrementStock(
      @PathVariable String bookId, @PathVariable String copyId, @RequestBody StockRequest request) {
    try {
      UUID copyUuid = UUID.fromString(copyId);
      StockUpdateResponse response = bookCopyService.incrementStock(copyUuid, request.quantity());
      return ResponseEntity.ok(response);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    } catch (ResourceNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
  }
}
