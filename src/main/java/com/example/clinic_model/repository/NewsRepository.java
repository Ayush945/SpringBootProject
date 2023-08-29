package com.example.clinic_model.repository;

import com.example.clinic_model.model.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News,Long> {
}
