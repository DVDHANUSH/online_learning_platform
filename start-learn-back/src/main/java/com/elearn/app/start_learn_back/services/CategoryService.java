package com.elearn.app.start_learn_back.services;
import com.elearn.app.start_learn_back.dtos.CategoryDto;
import com.elearn.app.start_learn_back.dtos.CourseDto;
import com.elearn.app.start_learn_back.dtos.CustomPageResponse;

import java.util.List;

public interface CategoryService {
    //create
    CategoryDto insert(CategoryDto categoryDto);
    CustomPageResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortBy);
    void delete (String categoryId);
    CategoryDto update(CategoryDto categoryDto, String categoryId);
    CategoryDto get(String categoryId);
    public void addCourseToCategory(String child, String course);


    List<CourseDto> getCourseOfCat(String categoryId);
}