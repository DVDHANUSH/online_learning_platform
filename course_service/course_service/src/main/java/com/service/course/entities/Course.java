package com.service.course.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @Id
    private String id;
    private String title;
    private String shortDesc;
    private String longDesc;
    private double price;
    private Boolean live = false;
    private double disc;
    private Date createdDate;
    // is a image field
    private String banner;
    private  String bannerContentType;
    private String categoryId;
}