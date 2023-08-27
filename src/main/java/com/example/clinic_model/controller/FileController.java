package com.example.clinic_model.controller;

import com.example.clinic_model.dto.ImageDTO;
import com.example.clinic_model.dto.ImageDownloadDTO;
import com.example.clinic_model.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("file")
public class FileController {
    @Autowired
    private FileService fileService;
    @PostMapping("/upload")
    public ResponseEntity<ImageDTO> upload(@ModelAttribute ImageDTO imageDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.fileService.uploadFile(imageDTO));
    }

    @GetMapping("/download/{Id}")
    public ResponseEntity<Resource> download(@PathVariable("Id")Long imageId){
        ImageDownloadDTO imageDownloadDTO=this.fileService.downloadFile(imageId);
        return ResponseEntity.ok()
                .contentType(imageDownloadDTO.getMediaType())
                .body(imageDownloadDTO.getResource());
    }
}
