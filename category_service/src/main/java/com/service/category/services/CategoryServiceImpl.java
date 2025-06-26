package com.service.category.services;

import com.service.category.Exceptions.ResourceNotPresentException;
import com.service.category.dto.CategoryDto;
import com.service.category.dto.CustomPageResponse;
import com.service.category.dto.SearchResponse;
import com.service.category.entities.Category;
import com.service.category.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService{
    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper){
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }
    @Override
    public CategoryDto insert(CategoryDto categoryDto) {

        // create categoryId
        String catId = UUID.randomUUID().toString();
        categoryDto.setId(catId);
        categoryDto.setAddedDate(new Date());
        // convert DTO to entity
        System.out.println("The dto : "+categoryDto);
        Category category = modelMapper.map(categoryDto, Category.class);
        Category savedCat = categoryRepository.save(category);
        return modelMapper.map(savedCat, CategoryDto.class);
    }

    @Override
    public CustomPageResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortBy) {
        Sort sort = Sort.by(sortBy).ascending();
        // page request
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        Page<Category> categoryPage = categoryRepository.findAll(pageRequest);
        List<Category> all = categoryPage.getContent();
        List<CategoryDto> categoryDtoList = all.stream().map(cat -> modelMapper.map(cat,CategoryDto.class)).toList();
        CustomPageResponse<CategoryDto> customPageResponse = new CustomPageResponse<>();
        customPageResponse.setContent(categoryDtoList);
        customPageResponse.setLast(categoryPage.isLast());
        customPageResponse.setTotalElements(categoryPage.getTotalPages());
        customPageResponse.setPageNumber(pageNumber);
        customPageResponse.setPageSize(categoryPage.getSize());
        return customPageResponse;
    }
    @Override
    public void  delete(String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotPresentException("Category Not Present"));
        categoryRepository.delete(category);
    }
    @Override
    public CategoryDto update(CategoryDto categoryDto, String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotPresentException("Category not present"));
        category.setTitle(categoryDto.getTitle());
        category.setDesc(categoryDto.getDesc());
        category.setBannerImageUrl(categoryDto.getBannerImageUrl());
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDto.class);
    }
    @Override
    public CategoryDto get(String categoryId) {
        Category category =  categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotPresentException("Category Not Present"));
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public SearchResponse search(String keyword) {
        SearchResponse searchResponse = new SearchResponse();
        if(keyword.isBlank()||keyword.isEmpty()){
//            return new ArrayList<>();
             searchResponse.setMessage("Enter a valid search string");
             return searchResponse;
        }
        List<CategoryDto> categoryDtos = this.categoryRepository.findByTitleContainingIgnoreCaseOrDescContainingIgnoreCase(keyword, keyword).stream().map(cat -> modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
        if (categoryDtos.isEmpty()) {
            searchResponse.setMessage("No category present with search key: " + keyword);
            return searchResponse;
        }
        searchResponse.setResults(categoryDtos);
        return searchResponse;
    }
}