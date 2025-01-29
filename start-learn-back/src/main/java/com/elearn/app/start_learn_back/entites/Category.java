package com.elearn.app.start_learn_back.entites;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "categories")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    private String id;
    @Column(nullable = false)
    private String title;
    @Column(name = "description")
    private String desc;
    private Date addedDate;
//    @OneToMany(mappedBy = "category")
//    private List<Course> courses = new ArrayList<>();
@ManyToMany(mappedBy = "categoryList")
private List<Course> courses = new ArrayList<>();
}