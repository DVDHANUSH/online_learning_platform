package com.video.services.video_services.dto;
import lombok.Data;
@Data
public class VideoDto
{
    private String id;
    private String title;
    private String desc;
    private String filePath;

    private String contentType;
    private  String courseId;
    private CourseDto courseDto;
}