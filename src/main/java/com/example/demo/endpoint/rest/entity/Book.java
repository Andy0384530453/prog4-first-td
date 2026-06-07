package com.example.demo.endpoint.rest.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "book")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id_book", updatable = false, nullable = false)
  private UUID id;

  @Column(name = "isbn", nullable = false, unique = true)
  private String isbn;

  @Column(name = "title")
  private String title;

  @Column(name = "genre")
  private String genre;

  @Column(name = "publication_date")
  private LocalDate publicationDate;

  @Column(name = "purchase_price")
  private BigDecimal purchasePrice;

  @Column(name = "selling_price")
  private BigDecimal sellingPrice;

  @Column(name = "current_stock")
  private Integer currentStock;

  @ManyToOne
  @JoinColumn(name = "id_category", nullable = false)
  private Category category;

  @ManyToMany
  @JoinTable(
      name = "book_author",
      joinColumns = @JoinColumn(name = "id_book"),
      inverseJoinColumns = @JoinColumn(name = "id_author"))
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private List<Author> authors;
}
