package com.example.wikiservice.dto;

import com.example.wikiservice.entity.AuxiliaryText;
import com.example.wikiservice.entity.Category;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ArticleToPrint {
    private Integer id;

    private String title;

    private String language;

    private String wiki;

    private Long createTimeStamp;

    private Long timeStamp;

    private List<Category> categories;

    private List<AuxiliaryText> auxiliaryText;
}
