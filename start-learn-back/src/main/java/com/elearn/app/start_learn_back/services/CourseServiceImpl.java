package com.elearn.app.start_learn_back.services;

import com.elearn.app.start_learn_back.Exceptions.ResourceNotPresentException;
import com.elearn.app.start_learn_back.Repositories.CourseRepository;
import com.elearn.app.start_learn_back.dtos.CourseDto;
import com.elearn.app.start_learn_back.entites.Course;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class CourseServiceImpl implements CourseService{
    private CourseRepository courseRepository;
    private ModelMapper modelMapper;
//    public CourseServiceImpl(CourseRepository courseRepository, ModelMapper modelMapper){
//        this.courseRepository = courseRepository;
//        this.modelMapper = modelMapper;
//    }




    @Override
    public CourseDto create(CourseDto courseDto) {
      Course savedCourse = courseRepository.save(this.dtoToEntity(courseDto));
        return entityToDto(savedCourse);
    }




    @Override
    public List<CourseDto> getAll() {
        List<Course> courses = courseRepository.findAll();
        List<CourseDto> courseDtoList  =  courses
                .stream()
                .map(course -> entityToDto(course))
                .collect(Collectors.toList());
        return courseDtoList;
    }
    @Override
    public CourseDto update(CourseDto dto, String courseId) {
        return null;
    }

    @Override
    public void delete(String courseId) {

    Course course =    courseRepository.findById(courseId).orElseThrow(()-> new ResourceNotPresentException("Course not present"));
    courseRepository.delete(course);
    }
    @Override
    public List<CourseDto> searchByTitle(String titleKeyword) {
        return null;
    }

    // to convert an entity to dto, we have to this
//    public CourseDto entityToDto(Course course){
//        CourseDto courseDto = new CourseDto();
//        courseDto.setId(courseDto.getId());
//        courseDto.setTitle(courseDto.getTitle());
//        return courseDto;
//    }

    // to convert a dto to entity, we have to do this
//    public Course dtoToEntity(CourseDto dto){
//        Course course = new Course();
//        course.setId(dto.getId());
//        course.setLive(dto.isLive());
//        return course;
//    }

    // note, if there are 100 fields,, we have to map for all 100 fields, instead there is a dependency that takes care of these mappings ...
    // Let's see how to do that
    public CourseDto entityToDto(Course course){
     CourseDto courseDto = modelMapper.map(course, CourseDto.class);
     return courseDto;
    }
    public Course dtoToEntity(CourseDto dto){
      Course course =  modelMapper.map(dto, Course.class);
      return course;
    }
}