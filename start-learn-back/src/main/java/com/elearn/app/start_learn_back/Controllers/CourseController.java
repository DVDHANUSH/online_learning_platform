package com.elearn.app.start_learn_back.Controllers;
import com.elearn.app.start_learn_back.config.AppConstants;
import com.elearn.app.start_learn_back.dtos.CourseDto;
import com.elearn.app.start_learn_back.dtos.CustomMessage;
import com.elearn.app.start_learn_back.dtos.ResourceContentType;
import com.elearn.app.start_learn_back.services.CourseService;
import com.elearn.app.start_learn_back.services.FileService;
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
public class CourseController {
   // @Autowired
    private CourseService courseService;
    @Autowired
    private FileService fileService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public ResponseEntity<CourseDto> createCourse(@RequestBody CourseDto courseDto) {
        return ResponseEntity.ok(courseService.createCourse(courseDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDto> updateCourse(@PathVariable String id, @RequestBody CourseDto courseDto) {
        return ResponseEntity.ok(courseService.updateCourse(id, courseDto));
    }
    @GetMapping("/{id}")
    public ResponseEntity<CourseDto>  getCourseById(@PathVariable String id){
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
    public  ResponseEntity<CourseDto> uploadBanner(
            @PathVariable String courseId,
            @RequestParam("banner") MultipartFile banner
            ) throws IOException {
        System.out.println(banner.getOriginalFilename());
        System.out.println(banner.getName());
        System.out.println(banner.getSize());
        System.out.println(banner.getContentType());
//        fileService.save(banner, AppConstants.COURSE_BANNER_UPLOAD_DIR, banner.getOriginalFilename());


       CourseDto courseDto = courseService.saveBanner(banner, courseId);
       return ResponseEntity.ok(courseDto);
    }
    // serve banner


    @GetMapping("/{courseId}/banners")
    public  ResponseEntity<Resource> serveBanner(
            @PathVariable String courseId
    ){
        ResourceContentType resourceContentType = courseService.getCourseBannerById(courseId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(resourceContentType.getContentType()))
                .body(resourceContentType.getResource());
    }
//@GetMapping("/{courseId}/banners")
//public ResponseEntity<Resource> serveBanner(@PathVariable String courseId) {
//    try {
//        // Get the banner path dynamically (you may fetch this path from the database or courseService)
//        String bannerPath = "uploads/courses/banners/Image.jpg"; // Example hardcoded path
//        Path filePath = Paths.get(bannerPath).toAbsolutePath();
//
//        // Load the file as a resource
//        Resource resource = new org.springframework.core.io.UrlResource(filePath.toUri());
//
//        // Check if the resource exists and is readable
//        if (!resource.exists() || !resource.isReadable()) {
//            throw new RuntimeException("File not found or is not readable: " + bannerPath);
//        }
//
//        // Determine the content type
//        String contentType = Files.probeContentType(filePath);
//
//        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType(contentType != null ? contentType : "application/octet-stream"))
//                .body(resource);
//
//    } catch (Exception e) {
//        e.printStackTrace();
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//    }
//}
}