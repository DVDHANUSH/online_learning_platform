package com.service.course.services;

import com.service.course.Exceptions.ResourceNotPresentException;
import com.service.course.config.AppConstants;
import com.service.course.dto.CategoryDto;
import com.service.course.dto.CourseDto;
import com.service.course.dto.ResourceContentType;
import com.service.course.dto.VideoDto;
import com.service.course.entities.Course;
import com.service.course.repositories.CourseRepository;
import feign.FeignException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {
    private CourseRepository courseRepository;
    private FileService fileService;
    private ModelMapper modelMapper;
    private RestTemplate restTemplate;
    private WebClient.Builder webClient;
    private final CategoryService categoryService;
    public CourseServiceImpl(WebClient.Builder webClient, CourseRepository courseRepository, ModelMapper modelMapper, FileService fileService, RestTemplate restTemplate, CategoryService categoryService) {
        this.webClient = webClient;
        this.courseRepository = courseRepository;
        this.modelMapper = modelMapper;
        this.fileService = fileService;
        this.restTemplate = restTemplate;
        this.categoryService = categoryService;
    }
    //    @Override
//    public CourseDto createCourse(CourseDto courseDto) {
//        String courseId = UUID.randomUUID().toString();
//        courseDto.setId(courseId);
//        courseDto.setCreatedDate(new Date());
//        Course course = modelMapper.map(courseDto, Course.class);
//        Course savedCourse = courseRepository.save(course);
////        String cat = courseDto.getId();
////        categoryService.addCourseToCategory(cat, savedCourse.getId());
//
//        return modelMapper.map(savedCourse, CourseDto.class);
//    }

    public CourseDto createCourse(CourseDto courseDto) {
        if (courseDto.getCategoryId() == null || courseDto.getCategoryId().isBlank()) {
            throw new IllegalArgumentException("Course must be linked to a valid category");
        }
        // Fetch category via Feign Client
        try {
            CategoryDto categoryDto = categoryService.getCategoryById(courseDto.getCategoryId());
            if (categoryDto == null) {
                throw new IllegalArgumentException("Category not found");
            }
        } catch (FeignException.NotFound ex) {
            throw new IllegalArgumentException("Category with ID " + courseDto.getCategoryId() + " does not exist");
        }

        // Proceed with course creation
        String courseId = UUID.randomUUID().toString();
        courseDto.setId(courseId);
        courseDto.setCreatedDate(new Date());
        Course course = modelMapper.map(courseDto, Course.class);
        Course savedCourse = courseRepository.save(course);
        return modelMapper.map(savedCourse, CourseDto.class);
    }

    // public CourseDto updateCourse(String id, CourseDto courseDto) here, the "courseDto" will have the course Data that has to be updated
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
        // Save the updated course
        Course updatedCourse = courseRepository.save(course);
        // Return the updated course as a DTO
        return modelMapper.map(updatedCourse, CourseDto.class);
    }

    @Override
    public CourseDto getCourseById(String id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not present"));
        // get category detail of that particular course.
        // to get the category detail object,
//        CategoryDto categoryDto =  restTemplate.getForObject(AppConstants.CATEGORY_SERVICE_BASE_URL+ "/categories/"+course.getCategoryId(), CategoryDto.class);
        CourseDto courseDto = modelMapper.map(course, CourseDto.class);
        System.out.println("the course we get : " + courseDto);
        courseDto.setCategoryDto(getCategoryOfCourse(courseDto.getCategoryId()));
        // Load video services to Load videos that are associated with this course

        courseDto.setVideos(getVideosAssociatedWithParticularCourse(course.getId()));
        return courseDto;
    }

    @Override
    public Page<CourseDto> getAllCourses(Pageable pageable) {
        Page<Course> courses = courseRepository.findAll(pageable);
        //  api call to get the category service to get the category detail
        List<CourseDto> dtos = courses.getContent()
                .stream()
                .map(course -> modelMapper.map(course, CourseDto.class))
                .collect(Collectors.toList());
        // it you want, create your own page response
        List<CourseDto> newcourseDtos = dtos.stream().map(courseDto -> {
            // write implementation here.
//         ResponseEntity<List<CategoryDto>> exchange = restTemplate.exchange(AppConstants.CATEGORY_SERVICE_BASE_URL + "/categories/" + courseDto.getCategoryDto(),
//                    HttpMethod.GET, null, new ParameterizedTypeReference<List<CategoryDto>>() {
//                    });
//         List<CategoryDto> categoryDtos = exchange.getBody();
//        }).toList();
//            CategoryDto categoryDto = restTemplate.getForObject(AppConstants.CATEGORY_SERVICE_BASE_URL + "/categories/" + courseDto.getCategoryDto(), CategoryDto.class);
//            courseDto.setCategoryDto(categoryDto);
//            return courseDto;
//        }).toList();
//            try {
//                ResponseEntity<CategoryDto> exchange = restTemplate.exchange(AppConstants.CATEGORY_SERVICE_BASE_URL+"/categories/"+courseDto.getCategoryId(), org.springframework.http.HttpMethod.GET, null, CategoryDto.class);
//                courseDto.setCategoryDto(exchange.getBody());
//            }
//            catch (HttpClientErrorException ex){
//                courseDto.setCategoryDto(null);
//                ex.printStackTrace();
//            }
//            return courseDto;
            courseDto.setCategoryDto(getCategoryOfCourse(courseDto.getCategoryId()));
            courseDto.setVideos(getVideosAssociatedWithParticularCourse(courseDto.getId()));
            return courseDto;
        }).toList();
        return new PageImpl<>(newcourseDtos, pageable, courses.getTotalElements());
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

//    @Override
//    public CourseDto saveBanner(MultipartFile file, String courseId) throws IOException {
//        Course course = courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotPresentException());
//        String filePath = fileService.save(file, AppConstants.COURSE_BANNER_UPLOAD_DIR, file.getOriginalFilename());
//        // String relativeFilePath = filePath.substring(filePath.indexOf(AppConstants.COURSE_BANNER_UPLOAD_DIR));
//        course.setBanner(filePath);
//        System.out.println("Full File Path: " + filePath);
//        System.out.println("Expected Directory: " + AppConstants.COURSE_BANNER_UPLOAD_DIR);
//        course.setBannerContentType(file.getContentType());
//        return modelMapper.map(courseRepository.save(course), CourseDto.class);
//    }

    public CourseDto entityToDto(Course course) {
        CourseDto courseDto = modelMapper.map(course, CourseDto.class);
        return courseDto;
    }

    public Course dtoToEntity(CourseDto dto) {
        Course course = modelMapper.map(dto, Course.class);
        return course;

    }


    @Override
    public ResourceContentType getCourseBannerById(String courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotPresentException());
        String bannerPath = course.getBanner();
        Path path = Paths.get(bannerPath);
        Resource resource = new FileSystemResource(path);
        ResourceContentType resourceContentType = new ResourceContentType();
        resourceContentType.setResource(resource);


        resourceContentType.setContentType(course.getBannerContentType());
        return resourceContentType;
    }

    @Override
    public List<CourseDto> getCoursesOfCategory(String categoryId) {
        return this.courseRepository.findByCategoryId(categoryId).stream().map(course-> modelMapper.map(course, CourseDto.class)).collect(Collectors.toList());
    }
    public CategoryDto getCategoryOfCourse(String categoryId) {
        try {
            ResponseEntity<CategoryDto> exchange = restTemplate.exchange(AppConstants.CATEGORY_SERVICE_BASE_URL + "/categories/" + categoryId, org.springframework.http.HttpMethod.GET, null, CategoryDto.class);
            return exchange.getBody();
        } catch (HttpClientErrorException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    public List<VideoDto> getVideosAssociatedWithParticularCourse(String courseId) {
        return webClient.build()
                .get()
                .uri(AppConstants.VIDEO_SERVICE_BASE_URL + "/videos/course/{id}", courseId)
                .retrieve()
                .bodyToFlux(VideoDto.class)
                .collect(Collectors.toList())
                .block();
    }

}