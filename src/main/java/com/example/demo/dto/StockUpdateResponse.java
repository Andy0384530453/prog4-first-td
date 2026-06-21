package com.example.demo.dto;

import java.util.UUID;

public record StockUpdateResponse(UUID bookCopyId, int newStock) {}
