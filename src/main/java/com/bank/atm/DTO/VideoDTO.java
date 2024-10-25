package com.bank.atm.DTO;

import lombok.Data;

@Data
public class VideoDTO {
    private String id;
    private String title;
    private String thumbnailUrl;
    private String duration;
    private String uploadTime;
    private String views;
    private String author;
    private String videoUrl;
    private String description;
    private String subscriber;
    private boolean isLive;
}
