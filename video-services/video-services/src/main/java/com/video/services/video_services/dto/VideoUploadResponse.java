package com.video.services.video_services.dto;

import lombok.Data;

@Data
public class VideoUploadResponse {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public VideoDto getVideoDto() {
        return videoDto;
    }

    public void setVideoDto(VideoDto videoDto) {
        this.videoDto = videoDto;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
    private VideoDto videoDto;
    private boolean success;
}