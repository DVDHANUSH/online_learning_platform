package com.video.services.video_services.documents;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document
@Data
public class Video
{
    @Id
    private String id;
    private String title;
    private String desc;
    private String filePath;
    private String contentType;
    private  String courseId;
}