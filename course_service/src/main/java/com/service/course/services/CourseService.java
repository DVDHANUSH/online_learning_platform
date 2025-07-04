package com.service.course.services;
import com.service.course.dto.CourseDto;
import com.service.course.dto.ResourceContentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
//import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
public interface CourseService {
  CourseDto createCourse(CourseDto courseDto);
  CourseDto updateCourse(String id, CourseDto courseDto);
  CourseDto getCourseById(String id);
  Page<CourseDto> getAllCourses(Pageable pageable);

  void deleteCourse(String courseId);
  List<CourseDto> searchCourses (String titleKeyword);
//  public CourseDto saveBanner(MultipartFile file, String courseId) throws IOException;
  ResourceContentType getCourseBannerById(String courseId);
  List<CourseDto> getCoursesOfCategory(String categoryId);

}