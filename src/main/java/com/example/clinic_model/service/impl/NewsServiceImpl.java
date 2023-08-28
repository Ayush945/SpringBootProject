package com.example.clinic_model.service.impl;

import com.example.clinic_model.dto.NewsDTO;
import com.example.clinic_model.exception.ResourceNotFoundException;
import com.example.clinic_model.model.News;
import com.example.clinic_model.repository.NewsRepository;
import com.example.clinic_model.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    NewsRepository newsRepository;
    @Override
    public NewsDTO createNews(NewsDTO newsDTO) {
        News news = modelMapper.map(newsDTO, News.class);
        news = newsRepository.save(news);
        return modelMapper.map(news, NewsDTO.class);
    }
    @Override
    public NewsDTO getNewsById(Long newsId) {
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new ResourceNotFoundException("News not found"));
        return modelMapper.map(news, NewsDTO.class);
    }
    @Override
    public List<NewsDTO> getAllNews() {
        List<News> newsList = newsRepository.findAll();
        return newsList.stream()
                .map(news -> modelMapper.map(news, NewsDTO.class))
                .collect(Collectors.toList());
    }


    @Override
    public NewsDTO updateNews(Long newsId, NewsDTO newsDTO) {
        Optional<News> optionalNews = newsRepository.findById(newsId);

        if (optionalNews.isEmpty()) {
            throw new ResourceNotFoundException("News not found");
        }

        News existingNews = optionalNews.get();

        // Update properties from DTO
        if (newsDTO.getHeadLine() != null) {
            existingNews.setHeadLine(newsDTO.getHeadLine());
        }
        if (newsDTO.getNewsBody() != null) {
            existingNews.setNewsBody(newsDTO.getNewsBody());
        }

        // Save the updated news
        News updatedNews = newsRepository.save(existingNews);

        return modelMapper.map(updatedNews, NewsDTO.class);
    }

    @Override
    public void deleteNewsById(Long newsId) {
        if (!newsRepository.existsById(newsId)) {
            throw new ResourceNotFoundException("News not found");
        }
        newsRepository.deleteById(newsId);
    }


}

