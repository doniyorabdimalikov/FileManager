package com.example.filemanager;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ImageDto {
    private Integer id;
    private String url;
    private String path;
    private Long size;
    private String type;
    private String token;
    private LocalDateTime createdAt;
}
