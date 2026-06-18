package com.example.demo.service;

import com.example.demo.dto.BookRequestDto;
import com.example.demo.dto.BookResponseDto;
import com.example.demo.entity.Author;
import com.example.demo.entity.Book;
import com.example.demo.entity.BookCopy;
import com.example.demo.entity.Category;
import com.example.demo.entity.CategoryEnum;
import com.example.demo.entity.SaleBook;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.SaleRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookService {

  private final BookRepository bookRepository;
  private final SaleRepository saleRepository;
  private final AuthorRepository authorRepository;
  private final CategoryRepository categoryRepository;

  @Transactional(readOnly = true)
  public List<BookResponseDto> getAllBooks() {
    return bookRepository.findAll().stream().map(this::toDto).toList();
  }

  @Transactional(readOnly = true)
  public BookResponseDto getBookById(UUID id) {
    Book book =
        bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book", id));
    return toDto(book);
  }

  @Transactional
  public BookResponseDto createBook(BookRequestDto requestDto) {
    if (requestDto.title() == null || requestDto.title().isBlank()) {
      throw new IllegalArgumentException("the title is required");
    }
    if (requestDto.publicationDate() == null) {
      throw new IllegalArgumentException("the publication date is mandatory");
    }
    if (requestDto.categoryName() == null || requestDto.categoryName().isBlank()) {
      throw new IllegalArgumentException("category is required");
    }
    if (requestDto.authorIds() == null || requestDto.authorIds().isEmpty()) {
      throw new IllegalArgumentException("category is required");
    }

    CategoryEnum categoryEnum;
    try {
      categoryEnum = CategoryEnum.valueOf(requestDto.categoryName().toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("invalid category : " + requestDto.categoryName());
    }

    Category category =
        categoryRepository
            .findByCategoryEnum(categoryEnum)
            .orElseThrow(
                () -> new IllegalArgumentException("category not found : " + categoryEnum));

    List<Author> authors = authorRepository.findAllById(requestDto.authorIds());
    if (authors.size() != requestDto.authorIds().size()) {
      throw new IllegalArgumentException("one or more authors cannot be found.");
    }

    Book book =
        Book.builder()
            .title(requestDto.title())
            .publicationDate(requestDto.publicationDate())
            .category(category)
            .authors(authors)
            .build();

    book = bookRepository.save(book);
    return toDto(book);
  }

  @Transactional(readOnly = true)
  public List<BookResponseDto> getBooksSoldToday() {
    LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
    LocalDateTime endOfDay = LocalDate.now().plusDays(1).atStartOfDay();

    return new ArrayList<>(
            saleRepository.findBySaleDateBetween(startOfDay, endOfDay).stream()
                .flatMap(sale -> sale.getBooks().stream())
                .map(SaleBook::getBookCopy)
                .map(BookCopy::getBook)
                .collect(Collectors.toMap(Book::getId, b -> b, (a, b) -> a))
                .values())
        .stream().map(this::toDto).toList();
  }

  private BookResponseDto toDto(Book book) {
    return new BookResponseDto(
        book.getId(),
        book.getTitle(),
        book.getPublicationDate(),
        book.getCategory() != null ? book.getCategory().getCategoryEnum().name() : null,
        book.getAuthors() != null
            ? book.getAuthors().stream().map(a -> a.getFirstName() + " " + a.getLastName()).toList()
            : List.of());
  }
}
