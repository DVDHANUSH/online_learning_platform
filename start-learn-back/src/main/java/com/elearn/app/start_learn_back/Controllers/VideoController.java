package com.elearn.app.start_learn_back.Controllers;

import com.elearn.app.start_learn_back.dtos.VideoDto;
import com.elearn.app.start_learn_back.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/videos")
public class VideoController {

    // @Autowired
    private VideoService videoService;
    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @PostMapping
    public ResponseEntity<VideoDto> createVideo(@RequestBody VideoDto videoDto) {
        return ResponseEntity.ok(videoService.createVideo(videoDto));
    }
    @PutMapping("/{id}")

    public ResponseEntity<VideoDto> updateVideo(@PathVariable String id, @RequestBody VideoDto videoDto ){
        return ResponseEntity.ok(videoService.updateVideo(id,videoDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideoDto> getVideoById(@PathVariable String id){
        return ResponseEntity.ok(videoService.getVideoById(id));
    }
    @GetMapping
    public ResponseEntity<Page<VideoDto>> getAllVideos(Pageable pageable){
        return ResponseEntity.ok(videoService.getAllVideos(pageable));
    }
}