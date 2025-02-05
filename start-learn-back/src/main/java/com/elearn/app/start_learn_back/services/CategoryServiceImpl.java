package com.elearn.app.start_learn_back.services;
import com.elearn.app.start_learn_back.Exceptions.ResourceNotPresentException;
import com.elearn.app.start_learn_back.Repositories.CategoryRepository;
import com.elearn.app.start_learn_back.Repositories.CourseRepository;
import com.elearn.app.start_learn_back.dtos.CategoryDto;
import com.elearn.app.start_learn_back.dtos.CourseDto;
import com.elearn.app.start_learn_back.dtos.CustomPageResponse;
import com.elearn.app.start_learn_back.entites.Category;
import com.elearn.app.start_learn_back.entites.Course;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    private CourseRepository courseRepository;
    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper, CourseRepository courseRepository){
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
        this.courseRepository = courseRepository;
    }
    @Override
    public CategoryDto insert(CategoryDto categoryDto) {

        // create categoryId
        String catId = UUID.randomUUID().toString();
        categoryDto.setId(catId);
        categoryDto.setAddedDate(new Date());
        // convert DTO to entity
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
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDto.class);
    }





    @Override
    public CategoryDto get(String categoryId) {
      Category category =  categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotPresentException("Category Not Present"));
        return modelMapper.map(category, CategoryDto.class);
    }
    public void addCourseToCategory(String catId, String courseid){
       // get category
       Category category = categoryRepository.findById(catId).orElseThrow(()-> new ResourceNotPresentException("Category not present"));
        // get course
       Course course = courseRepository.findById(courseid).orElseThrow(()-> new ResourceNotPresentException("Course not present"));

       // course ke ander cat list mein cat add gohi
        // cat ke ander course hai
       category.addCourse(course);
       // its children get saved to as we have cascade
       categoryRepository.save(category);
       System.out.println("Category relationship updated");
    }
    @Override
    @Transactional
    public List<CourseDto> getCourseOfCat(String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotPresentException("Category not present"));
        System.out.println("the category is present");
        List<Course> courses  = category.getCourses();
        return courses.stream().map(course -> modelMapper.map(course, CourseDto.class)).collect(Collectors.toList());
    }
}