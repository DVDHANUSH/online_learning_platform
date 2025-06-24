package com.service.category.services;
import com.service.category.dto.CategoryDto;
import com.service.category.dto.CustomPageResponse;

import java.util.List;

public interface CategoryService {
    //create
    CategoryDto insert(CategoryDto categoryDto);
    CustomPageResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortBy);
    void delete (String categoryId);
    CategoryDto update(CategoryDto categoryDto, String categoryId);
    CategoryDto get(String categoryId);
}