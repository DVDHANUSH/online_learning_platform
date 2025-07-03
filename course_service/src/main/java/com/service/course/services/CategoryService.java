package com.service.course.services;
import com.service.course.dto.CategoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@FeignClient(name = "CATEGORY-SERVICE")
public interface CategoryService {
    @GetMapping("/api/v1/categories/{categoryId}")
    CategoryDto getCategoryById(@PathVariable String categoryId);

}