package com.service.category.entities;
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
    @Column(name= "banner url")
    private String bannerImageUrl;
    private String courseId;
    // if any course has courseId as null, that means it is not associated with any of the category.
    // and we want multiple courses in a single category.
}