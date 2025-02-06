package com.elearn.app.start_learn_back.entites;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    @OneToMany(mappedBy = "course")
    private List<Video> videos = new ArrayList<>();
    @ManyToMany()
    private List<Category> categoryList = new ArrayList<>();
    public void addCategory(Category category)
    {
        categoryList.add(category);
        category.getCourses().add(this);
    }
    public void removeCategory(Category category){
        categoryList.remove(category);
        category.getCourses().remove(this);
    }
}