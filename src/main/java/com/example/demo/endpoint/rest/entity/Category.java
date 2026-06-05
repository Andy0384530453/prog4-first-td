package com.example.demo.endpoint.rest.entity;


import jakarta.persistence.Entity;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class Category {
    private UUID id;
    private CategoryEnum categoryEnum;

}
