package com.elearn.app.start_learn_back.dtos;

import com.elearn.app.start_learn_back.entites.Course;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class VideoDto {
    private String id;
    private String title;
    private String desc;
    private String filePath;
    private String contentType;
    private String banner;
    private Course course;
}