package com.service.category.dto;

import lombok.Data;

import java.util.List;
@Data
public class SearchResponse {
    private String message;
    private List<CategoryDto> results;
    // getters and setters
}