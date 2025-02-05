package com.elearn.app.start_learn_back.Repositories;
import com.elearn.app.start_learn_back.entites.Course;
import com.elearn.app.start_learn_back.entites.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
public interface VideoRepository extends JpaRepository<Video, String> {
    List<Video> findByTitle(String title);
    List<Video> findByCourse(Course course);
}