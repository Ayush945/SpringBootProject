package com.example.clinic_model.controller;


import com.example.clinic_model.dto.ImageDTO;
import com.example.clinic_model.dto.ImageDownloadDTO;
import com.example.clinic_model.dto.NewsDTO;
import com.example.clinic_model.service.FileService;
import com.example.clinic_model.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private FileService fileService;

    @Autowired
    private NewsService newsService;

    @PostMapping("/create-news")
    public ResponseEntity<NewsDTO> createNews(@RequestBody NewsDTO newsDTO) {
        NewsDTO createdNews = newsService.createNews(newsDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNews);
    }

    @GetMapping("/get-all-news")
    public ResponseEntity<List<NewsDTO>> getAllNews() {
        List<NewsDTO> newsList = newsService.getAllNews();
        return ResponseEntity.ok(newsList);
    }

    @GetMapping("/{newsId}")
    public ResponseEntity<NewsDTO> getNewsById(@PathVariable Long newsId) {
        NewsDTO news = newsService.getNewsById(newsId);
        return ResponseEntity.ok(news);
    }

    @PutMapping("update-news/{newsId}")
    public ResponseEntity<NewsDTO> updateNews(@PathVariable Long newsId, @RequestBody NewsDTO newsDTO) {
        NewsDTO updatedNews = newsService.updateNews(newsId, newsDTO);
        return ResponseEntity.ok(updatedNews);
    }

    @DeleteMapping("delete-news/{newsId}")
    public ResponseEntity<Void> deleteNews(@PathVariable Long newsId) {
        newsService.deleteNewsById(newsId);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/upload-news-image/{newsId}")
    ResponseEntity<ImageDTO> uploadNewsImage(@PathVariable("newsId") Long newsId , @ModelAttribute ImageDTO imageDTO){
        return  ResponseEntity.ok().body(fileService.uploadNewsImage(newsId,imageDTO));
    }

    @GetMapping("/download-news-image/{newsId}")
    ResponseEntity<Resource> getNewsImage(@PathVariable("newsId") Long newsId){
        ImageDownloadDTO imageDownloadDTO=this.fileService.getNewsImage(newsId);
        return ResponseEntity.ok()
                .contentType(imageDownloadDTO.getMediaType())
                .body(imageDownloadDTO.getResource());
    }
}
