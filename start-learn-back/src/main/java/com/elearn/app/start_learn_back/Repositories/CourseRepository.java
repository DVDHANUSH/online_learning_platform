package com.elearn.app.start_learn_back.Repositories;
import com.elearn.app.start_learn_back.entites.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, String> {
    List<Course> findByTitleContainingIgnoreCaseOrShortDescContainingIgnoreCase(String title, String keyword);
   List<Course> findByLive(boolean live);

    Page<Course> findAll(Pageable pageable);
    // search the course by price
//    @Query("select c from course where)
//    List<Course> findByCategoryId(String categoryId);
}