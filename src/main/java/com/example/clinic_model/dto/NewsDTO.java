package com.example.clinic_model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class NewsDTO {
    private Long newsId;
    private String headLine;
    private String newsBody;
}
