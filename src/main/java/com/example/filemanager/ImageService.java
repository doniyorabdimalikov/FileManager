package com.example.filemanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImageService {
    private String fileUrl = "uploads/images/";
    @Autowired
    private ImageRepository imageRepository;

    public String saveToSystem(MultipartFile file) {
        try {

            String YMD = getYMD();
            String token = UUID.randomUUID().toString();
            String type = file.getContentType().split("/")[1];

            String URL = YMD + "/" + token + "." + type;
            File folder = new File(fileUrl + YMD);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            Path path = Paths.get(fileUrl).resolve(URL);
            Files.copy(file.getInputStream(), path);
            return createImage(YMD, type, file.getSize(), token);
        } catch (IOException e) {
            throw new IllegalArgumentException("File not created");
        }
    }

    private String createImage(String ymd, String type, long size, String token) {
        Image image = new Image();
        image.setPath(ymd);
        image.setSize(size);
        image.setToken(token);
        image.setType(type);
        image.setCreatedAt(LocalDateTime.now());
        imageRepository.save(image);
        return "SUCCESSFUL CREATED";
    }

    public String getYMD() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        return String.format("%s/%s/%s", year, month + 1, day);
    }

    public Resource load(String filename) {
        Optional<Image> optional = imageRepository.findByToken(filename);
        if (optional.isEmpty()) {
            throw new IllegalArgumentException("Image not found");
        }
        return null;
    }
}
