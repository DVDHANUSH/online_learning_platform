package com.elearn.app.start_learn_back.Controllers;
import com.elearn.app.start_learn_back.config.AppConstants;
import com.elearn.app.start_learn_back.dtos.CourseDto;
import com.elearn.app.start_learn_back.dtos.CustomMessage;
import com.elearn.app.start_learn_back.dtos.ResourceContentType;
import com.elearn.app.start_learn_back.services.CourseService;
import com.elearn.app.start_learn_back.services.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
@RestController
@RequestMapping("/api/v1/courses")
//@SecurityRequirement(name = "jwtScheme")
//@CrossOrigin("http://localhost:4200")
@CrossOrigin("*")
public class CourseController {
   // @Autowired
    private CourseService courseService;
    @Autowired
    private FileService fileService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @Operation(summary = "Create new Course", description = "Pass new course information to create new course", tags = {"course operation"})
    @ApiResponse(responseCode = "201", description = "Course Created Successfully")
    @ApiResponse(responseCode = "501", description = "Internal Server Error, course not created")
    @PostMapping
    public ResponseEntity<CourseDto> createCourse(@RequestBody CourseDto courseDto) {
        return ResponseEntity.ok(courseService.createCourse(courseDto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Course", description = "Pass Updated course information to Update course", tags = {"course operation"})
    public ResponseEntity<CourseDto> updateCourse(@PathVariable String id, @RequestBody CourseDto courseDto) {
        return ResponseEntity.ok(courseService.updateCourse(id, courseDto));

    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public ResponseEntity<CourseDto>  getCourseById(@Parameter(description = "Course Id which is to update") @PathVariable String id){
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.getCourseById(id));
    }

    @GetMapping
    public ResponseEntity<Page<CourseDto>> getAllCourses(Pageable pageable)
    {
      return ResponseEntity.ok(courseService.getAllCourses(pageable));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable String id){
        courseService.deleteCourse(id);
        return ResponseEntity.ok("Entity has been successfully deleted.");


        //return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Entity has been successfully deleted.");
    }


    @GetMapping("/search")
    public ResponseEntity<List<CourseDto>> searchCourses(@RequestParam String keyword){
     return ResponseEntity.ok(courseService.searchCourses(keyword));
    }






    // going to create one banner [image upload api]
    @PostMapping("/{courseId}/banner")
    public  ResponseEntity<?> uploadBanner(
            @PathVariable String courseId,
            @RequestParam("banner") MultipartFile banner
            ) throws IOException {
        System.out.println(banner.getOriginalFilename());
        System.out.println(banner.getName());
        System.out.println(banner.getSize());
        String contentType = banner.getContentType();
        if(contentType == null){
            contentType = "image/png";
        }

        else if(contentType.equalsIgnoreCase("image/png")|| contentType.equalsIgnoreCase("image/jpeg")){

        }
        else{
            CustomMessage customMessage = new CustomMessage();
            customMessage.setSuccess(false);
            customMessage.setMessage("Invalid file format");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(customMessage);
        }
        //fileService.save(banner, AppConstants.COURSE_BANNER_UPLOAD_DIR, banner.getOriginalFilename());
       CourseDto courseDto = courseService.saveBanner(banner, courseId);
       return ResponseEntity.ok(courseDto);
    }
    // get banner
    @GetMapping("/{courseId}/banners")
    public  ResponseEntity<Resource> serveBanner(
            @PathVariable String courseId,
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session,
            ServletContext servletContext,
            @RequestHeader("Content-Type") String contentType
    )
    {
        ResourceContentType resourceContentType = courseService.getCourseBannerById(courseId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(resourceContentType.getContentType()))
                .body(resourceContentType.getResource());
    }
}