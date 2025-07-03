package com.service.video.repositories;
import com.service.video.documents.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
//import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
@Repository
public interface VideoRepo extends JpaRepository<Video, String> {
    List<Video> findByTitleContainingIgnoreCaseOrDescContainingIgnoreCase(String keyword, String desc);
    List<Video> findByCourseId(String courseId);
}