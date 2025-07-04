package com.video.services.video_services.services;
import com.video.services.video_services.dto.CourseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "course-service")
public interface CourseService { @GetMapping("/api/v1/courses/{courseId}")
CourseDto getCourseById(@PathVariable String courseId);
}