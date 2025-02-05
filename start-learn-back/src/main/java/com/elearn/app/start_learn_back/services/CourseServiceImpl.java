package com.elearn.app.start_learn_back.services;
import com.elearn.app.start_learn_back.Repositories.CourseRepository;
import com.elearn.app.start_learn_back.dtos.CourseDto;
import com.elearn.app.start_learn_back.entites.Course;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService{
    private CourseRepository courseRepository;
    private ModelMapper modelMapper;

    private CategoryService categoryService;
    public CourseServiceImpl(CourseRepository courseRepository, ModelMapper modelMapper, CategoryService categoryService) {
        this.courseRepository = courseRepository;
        this.modelMapper = modelMapper;
        this.categoryService = categoryService;
    }
    @Override
    public CourseDto createCourse(CourseDto courseDto) {
        String courseId = UUID.randomUUID().toString();
        courseDto.setId(courseId);
        courseDto.setCreatedDate(new Date());
        Course course = modelMapper.map(courseDto, Course.class);
        Course savedCourse = courseRepository.save(course);
//        String cat = courseDto.getId();
//        categoryService.addCourseToCategory(cat, savedCourse.getId());

        return modelMapper.map(savedCourse, CourseDto.class);
    }
    // public CourseDto updateCourse(String id, CourseDto courseDto) here, the "courseDto" will have the course Data that has to be updated


//    @Override
//    public CourseDto updateCourse(String id, CourseDto courseDto) {
//        Course course = courseRepository.findById(id).orElseThrow(()-> new RuntimeException("Course Not Present"));
//        modelMapper.getConfiguration().setSkipNullEnabled(true);
//        modelMapper.typeMap(CourseDto.class, Course.class).addMappings(mapper ->
//                mapper.skip(Course::setId)
//        );
//        modelMapper.map(courseDto, course);
//        //  modelMapper.map(courseDto, course);
//        Course updatedcourse = courseRepository.save(course);
//        return modelMapper.map(updatedcourse, CourseDto.class);
//    }

    @Override
public CourseDto updateCourse(String id, CourseDto courseDto) {
    // Fetch the existing course
    Course course = courseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Course Not Present"));
    // Only update fields that are not null in the courseDto
    if (courseDto.getTitle() != null) {
        course.setTitle(courseDto.getTitle());
    }
    if (courseDto.getShortDesc() != null) {
        course.setShortDesc(courseDto.getShortDesc());
    }
    if (courseDto.getLongDesc() != null) {
        course.setLongDesc(courseDto.getLongDesc());
    }
    if (courseDto.getPrice() != 0.0) { // assuming 0 is not a valid value for price
        course.setPrice(courseDto.getPrice());
    }
    if (courseDto.getLive() != null) { // Assuming boolean values can be modified

        System.out.println("not same hence updating");
        course.setLive(courseDto.getLive());
    }
    if (courseDto.getDisc() != 0.0) {
        course.setDisc(courseDto.getDisc());
    }
    if (courseDto.getBanner() != null) {
        course.setBanner(courseDto.getBanner());
    }
    if (courseDto.getBannerContentType() != null) {
        course.setBannerContentType(courseDto.getBannerContentType());
    }
//    if (courseDto.getCategoryList() != null) {
//        course.setCategoryList(courseDto.getCategoryList());
//    }
    // Save the updated course
    Course updatedCourse = courseRepository.save(course);
    // Return the updated course as a DTO
    return modelMapper.map(updatedCourse, CourseDto.class);
}
    @Override
    public CourseDto getCourseById(String id) {
        Course course = courseRepository.findById(id).orElseThrow(()-> new RuntimeException("Course not present"));
        return modelMapper.map(course, CourseDto.class);
    }
    @Override
    public Page<CourseDto> getAllCourses(Pageable pageable) {
        Page<Course> courses = courseRepository.findAll(pageable);
        List<CourseDto> dtos = courses.getContent()
                .stream()
                .map(course -> modelMapper.map(course, CourseDto.class))
                .collect(Collectors.toList());
        // it you want, create your own page response
        return new PageImpl<>(dtos, pageable, courses.getTotalElements());
    }

    @Override
    public void deleteCourse(String courseId) {
         courseRepository.deleteById(courseId);
    }
    @Override
    public List<CourseDto> searchCourses(String keyword) {
        List<Course> courses = courseRepository.findByTitleContainingIgnoreCaseOrShortDescContainingIgnoreCase(keyword, keyword);
        return courses.stream().map(course -> modelMapper.map(course, CourseDto.class)).collect(Collectors.toList());
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