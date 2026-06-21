package com.example.demo.exception;

import java.util.UUID;

public class InsufficientStockException extends RuntimeException {
  public InsufficientStockException(UUID bookCopyId, int requested, int available) {
    super(
        "Insufficient stock for BookCopy "
            + bookCopyId
            + ": requested "
            + requested
            + ", available "
            + available);
  }
}
