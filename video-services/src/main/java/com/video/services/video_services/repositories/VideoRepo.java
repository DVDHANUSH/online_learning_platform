package com.video.services.video_services.repositories;
import com.video.services.video_services.documents.Video;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface VideoRepo extends MongoRepository<Video, String> {
    List<Video> findByTitleContainingIgnoreCaseOrDescContainingIgnoreCase(String keyword, String desc);
    List<Video> findByCourseId(String courseId);

}