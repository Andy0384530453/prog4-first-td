package com.example.demo.service;

import com.example.demo.dto.BookCopyResponseDto;
import com.example.demo.dto.StockByFormatEntry;
import com.example.demo.dto.StockResponse;
import com.example.demo.dto.StockUpdateResponse;
import com.example.demo.entity.BookCopy;
import com.example.demo.entity.FormatType;
import com.example.demo.exception.InsufficientStockException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.BookCopyRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookCopyService {

  private final BookCopyRepository bookCopyRepository;

  @Transactional(readOnly = true)
  public List<BookCopyResponseDto> getAllBookCopies() {
    return bookCopyRepository.findAll().stream().map(this::toDto).toList();
  }

  @Transactional(readOnly = true)
  public BookCopyResponseDto getBookCopyById(UUID id) {
    return bookCopyRepository
        .findById(id)
        .map(this::toDto)
        .orElseThrow(() -> new ResourceNotFoundException("BookCopy", id));
  }

  @Transactional(readOnly = true)
  public StockResponse getStockByBookAndFormat(UUID bookId, FormatType formatType) {
    int stock = bookCopyRepository.getTotalStockByBookAndFormat(bookId, formatType);
    return new StockResponse(stock);
  }

  @Transactional(readOnly = true)
  public List<StockByFormatEntry> getStockGroupedByFormat(UUID bookId) {
    List<Object[]> results = bookCopyRepository.findStockGroupedByBook(bookId);
    return results.stream()
        .map(row -> new StockByFormatEntry((FormatType) row[0], ((Number) row[1]).intValue()))
        .toList();
  }

  @Transactional
  public StockUpdateResponse decrementStock(UUID bookCopyId, int quantity) {
    if (quantity <= 0) {
      throw new IllegalArgumentException("Quantity must be positive");
    }
    BookCopy bookCopy =
        bookCopyRepository
            .findById(bookCopyId)
            .orElseThrow(() -> new ResourceNotFoundException("BookCopy", bookCopyId));
    int currentStock = bookCopy.getCurrentStock() != null ? bookCopy.getCurrentStock() : 0;
    if (currentStock < quantity) {
      throw new InsufficientStockException(bookCopyId, quantity, currentStock);
    }
    bookCopy.setCurrentStock(currentStock - quantity);
    bookCopyRepository.save(bookCopy);
    return new StockUpdateResponse(bookCopyId, bookCopy.getCurrentStock());
  }

  @Transactional
  public StockUpdateResponse incrementStock(UUID bookCopyId, int quantity) {
    if (quantity <= 0) {
      throw new IllegalArgumentException("Quantity must be positive");
    }
    BookCopy bookCopy =
        bookCopyRepository
            .findById(bookCopyId)
            .orElseThrow(() -> new ResourceNotFoundException("BookCopy", bookCopyId));
    int currentStock = bookCopy.getCurrentStock() != null ? bookCopy.getCurrentStock() : 0;
    bookCopy.setCurrentStock(currentStock + quantity);
    bookCopyRepository.save(bookCopy);
    return new StockUpdateResponse(bookCopyId, bookCopy.getCurrentStock());
  }

  private BookCopyResponseDto toDto(BookCopy entity) {
    return new BookCopyResponseDto(
        entity.getId(),
        entity.getIsbn(),
        entity.getPurchasePrice(),
        entity.getSellingPrice(),
        entity.getCurrentStock(),
        entity.getFormatType(),
        entity.getBook().getId(),
        entity.getBook().getTitle());
  }
}
