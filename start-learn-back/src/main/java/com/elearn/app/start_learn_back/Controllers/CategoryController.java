package com.elearn.app.start_learn_back.Controllers;
import com.elearn.app.start_learn_back.config.AppConstants;
import com.elearn.app.start_learn_back.dtos.CategoryDto;
import com.elearn.app.start_learn_back.dtos.CourseDto;
import com.elearn.app.start_learn_back.dtos.CustomMessage;
import com.elearn.app.start_learn_back.dtos.CustomPageResponse;
import com.elearn.app.start_learn_back.services.CategoryService;
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
    public ResponseEntity<?> create(@Valid @RequestBody CategoryDto categoryDto
//            , BindingResult bindingResult
    ) {
//        if(bindingResult.hasErrors()){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Data");
//        }
        // we will create a validation that will work for the entire application
        CategoryDto createdDto = categoryService.insert(categoryDto);
        // return 201
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdDto);
    }

    //    @GetMapping
//    public List<CategoryDto> getAll(){
//       return categoryService.getAll();
//    }
    @GetMapping
    public CustomPageResponse<CategoryDto> getAll(@RequestParam(value = "pageNumber", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int pageNumber,
                                                  @RequestParam(value = "pageSize", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize,
                                                  @RequestParam(value = "sortBy", required = false, defaultValue = AppConstants.DEFAULT_SORT_BY)String sortBy) {
        return categoryService.getAll(pageNumber,pageSize, sortBy);
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

    @PostMapping("/{categoryId}/courses/{courseId}")
    public ResponseEntity<CustomMessage> addCourseToCategory(
            @PathVariable String categoryId,
            @PathVariable String courseId
    ){
        categoryService.addCourseToCategory(categoryId, courseId);
        CustomMessage customMessage = new CustomMessage();
        customMessage.setMessage("Category Updated");
        customMessage.setSuccess(true);
        return ResponseEntity.ok(customMessage);
    }

    @GetMapping("/{categoryId}/courses")
    public ResponseEntity<List<CourseDto>> getCourseOfCategory(@PathVariable String categoryId){
        System.out.println("here at the cc");
        return ResponseEntity.ok(categoryService.getCourseOfCat(categoryId));
    }
}