package com.elearn.app.start_learn_back.services;
import com.elearn.app.start_learn_back.dtos.CourseDto;
import com.elearn.app.start_learn_back.entites.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
public interface CourseService {
  CourseDto createCourse(CourseDto courseDto);
  CourseDto updateCourse(String id, CourseDto courseDto);
  CourseDto getCourseById(String id);
  Page<CourseDto> getAllCourses(Pageable pageable);

  void deleteCourse(String courseId);
  List<CourseDto> searchCourses (String titleKeyword);
}