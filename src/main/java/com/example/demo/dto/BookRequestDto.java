package com.example.demo.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record BookRequestDto(
    String title, LocalDate publicationDate, String categoryName, List<UUID> authorIds) {}
