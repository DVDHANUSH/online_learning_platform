package com.elearn.app.start_learn_back.dtos;
import com.elearn.app.start_learn_back.entites.Course;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Data
public class CategoryDto {
     private String id;
     @NotEmpty(message = "Title is required")
     @Size(min = 3, max = 50, message = "Title has to be between 3 to 50 characters")
     private String title;
     @NotEmpty(message = "Description is required")

//     @Email()
//     @Pattern()
     private String desc;
     private Date addedDate;
    //    @OneToMany(mappedBy = "category")
//    private List<Course> courses = new ArrayList<>();
 //   private List<Course> courses = new ArrayList<>();
}