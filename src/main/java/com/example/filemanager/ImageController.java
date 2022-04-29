package com.example.filemanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/image")
public class ImageController {
    @Autowired
    private ImageService imageService;

    @PostMapping
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
        String result = imageService.saveToSystem(file);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/load/{filename:.+}")
    public ResponseEntity<?> saveFile(@PathVariable String filename) {
        Resource file = imageService.load(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "filename=\"" + file.getFilename() + "\"").body(file);
    }

}
