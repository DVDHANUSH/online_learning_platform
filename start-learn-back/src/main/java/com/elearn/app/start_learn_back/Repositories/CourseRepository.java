package com.elearn.app.start_learn_back.Repositories;
import com.elearn.app.start_learn_back.entites.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
public interface CourseRepository extends JpaRepository<Course, String> {
Optional<Course> findByTitle(String title);
List<Course> findByLive(boolean live);





// search the course by price
//    @Query("select c from course where)
//    List<Course> findByCategoryId(String categoryId);
}