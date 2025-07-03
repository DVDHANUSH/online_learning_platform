package com.video.services.video_services.services;
import com.video.services.video_services.dto.VideoUploadResponse;
import org.springframework.web.multipart.MultipartFile;

public interface VideoUploadService {
    VideoUploadResponse uploadVideo(String courseId, String videoId, MultipartFile file);
}
