package com.service.category.Controllers;
import com.service.category.config.AppConstants;
import com.service.category.dto.CategoryDto;
import com.service.category.dto.CustomMessage;
import com.service.category.dto.CustomPageResponse;
import com.service.category.dto.SearchResponse;
import com.service.category.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")

public class CategoryController {


    private CategoryService categoryService;
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CategoryDto categoryDto) {
        try {
            CategoryDto createdDto = categoryService.insert(categoryDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdDto);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }
    @GetMapping
    public CustomPageResponse<CategoryDto> getAll(@RequestParam(value = "pageNumber", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int pageNumber,
                                                  @RequestParam(value = "pageSize", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize,
                                                  @RequestParam(value = "sortBy", required = false, defaultValue = AppConstants.DEFAULT_SORT_BY) String sortBy) {
        return categoryService.getAll(pageNumber, pageSize, sortBy);
    }

    @GetMapping("/{categoryId}")
    public CategoryDto getSingleCategory(@PathVariable String categoryId) {
        return categoryService.get(categoryId);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<CustomMessage> delete(@PathVariable String categoryId) {
        categoryService.delete(categoryId);
        CustomMessage customMessage = new CustomMessage();
        customMessage.setMessage("Category Deleted");
        customMessage.setSuccess(true);
        return ResponseEntity.status(HttpStatus.OK).body(customMessage);
    }

    @PutMapping("/{categoryId}")
    public CategoryDto update(@PathVariable String categoryId, @RequestBody
    CategoryDto categoryDto) {
        return categoryService.update(categoryDto, categoryId);
    }

    @GetMapping("/search")
    public SearchResponse searchCategory(@RequestParam("q") String q) {
        return this.categoryService.search(q);
    }
}