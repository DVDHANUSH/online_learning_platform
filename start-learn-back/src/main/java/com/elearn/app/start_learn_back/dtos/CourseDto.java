package com.elearn.app.start_learn_back.dtos;
import com.elearn.app.start_learn_back.entites.Category;
import com.elearn.app.start_learn_back.entites.Video;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Data

public class CourseDto {
    private String id;
    private String title;
    private String shortDesc;
    private String longDesc;
    private double price;
    private boolean live = false;
    private double disc;
    private Date createdDate;
    private String banner;
    private  String bannerContentType;
    private List<VideoDto> videos = new ArrayList<>();
    private List<CategoryDto> categoryList = new ArrayList<>();
}