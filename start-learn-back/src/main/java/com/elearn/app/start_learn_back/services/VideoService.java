package com.elearn.app.start_learn_back.services;

import com.elearn.app.start_learn_back.dtos.VideoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VideoService {
    VideoDto createVideo(VideoDto videoDto);
    VideoDto updateVideo(String videoId, VideoDto videoDto);
    VideoDto getVideoById(String videoId);
    Page<VideoDto> getAllVideos(Pageable pageable);
    void deleteVideo(String videoDto);

    List<VideoDto> searchVideos(String keyword);
}