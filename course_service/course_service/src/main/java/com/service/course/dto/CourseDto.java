package com.service.course.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Data

public class CourseDto {
    private String id;
    private String title;
    private String shortDesc;
    // @JsonIgnore
    // this will not be shown in the response payload when the api call is made
    // @JsonProperty("long_description")
    // to change the name of the column_name
    private String longDesc;
    private double price;
    private Boolean live;
    private double disc;
  //  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss a", timezone = "GMT")
    private Date createdDate;
    private String banner;
    private  String bannerContentType;
    public String getBannerURL(){
        return "http://localhost:8080/api/v1/courses/" +id+"/banners";
    }

    private String categoryId;
    private CategoryDto categoryDto;
    private List<VideoDto> videos;
}