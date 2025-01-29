package com.elearn.app.start_learn_back.services;
import com.elearn.app.start_learn_back.dtos.CourseDto;
import com.elearn.app.start_learn_back.entites.Course;

import java.util.List;

public interface CourseService {
  CourseDto create(CourseDto courseDto);
  List<CourseDto> getAll();
  CourseDto update(CourseDto dto, String courseId);
  void delete(String courseId);


  List<CourseDto> searchByTitle (String titleKeyword);
}