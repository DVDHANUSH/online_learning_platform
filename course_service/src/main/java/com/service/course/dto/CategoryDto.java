package com.service.course.dto;
import lombok.Data;
import java.util.Date;
@Data
public class CategoryDto {
    private String id;
    private String title;
    private String desc;
    private Date addedDate;
    private String bannerImageUrl;
}