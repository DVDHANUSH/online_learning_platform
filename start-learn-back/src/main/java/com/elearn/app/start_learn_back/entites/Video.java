package com.elearn.app.start_learn_back.entites;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Video")
public class Video {
    @Id
    private String id;
    private String title;
    @Column(name = "description")
    private String desc;
    private String filePath;
    private String contentType;
    private String banner;
//    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
//    private List<Video> videos = new ArrayList<>();
    @ManyToOne
    private Course course;
}