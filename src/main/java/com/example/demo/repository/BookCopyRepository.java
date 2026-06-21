package com.example.demo.repository;

import com.example.demo.entity.BookCopy;
import com.example.demo.entity.FormatType;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookCopyRepository extends JpaRepository<BookCopy, UUID> {

  @Query(
      "SELECT COALESCE(SUM(bc.currentStock), 0) FROM BookCopy bc "
          + "WHERE bc.book.id = :bookId AND bc.formatType = :formatType")
  int getTotalStockByBookAndFormat(
      @Param("bookId") UUID bookId, @Param("formatType") FormatType formatType);

  @Query(
      "SELECT bc.formatType, COALESCE(SUM(bc.currentStock), 0) FROM BookCopy bc "
          + "WHERE bc.book.id = :bookId GROUP BY bc.formatType")
  List<Object[]> findStockGroupedByBook(@Param("bookId") UUID bookId);
}
