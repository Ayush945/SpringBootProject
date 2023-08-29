package com.example.clinic_model.service;

import com.example.clinic_model.dto.NewsDTO;

import java.util.List;

public interface NewsService {
    NewsDTO createNews(NewsDTO newsDTO);
    List<NewsDTO> getAllNews();
    NewsDTO getNewsById(Long newsId);
    NewsDTO updateNews(Long newsId, NewsDTO newsDTO);
    void deleteNewsById(Long newsId);
}
