package com.example.wikiservice.service.api;

import com.example.wikiservice.dto.ArticleToPrint;
import com.example.wikiservice.dto.AuxiliaryTextList;
import com.example.wikiservice.dto.CategoryList;
import com.example.wikiservice.entity.Article;
import com.example.wikiservice.exception.ArticleNotFoundException;
import com.example.wikiservice.service.WikiService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class WikiApiService {
    private final WikiService service;
    public ArticleToPrint getArticleByNameValue(String title) throws ArticleNotFoundException {
        log.info("Getting article with name: {}", title);
        ArticleToPrint article = service.handleGetRequestArticleByName(title);
        log.info("Successfully got article with name: {}", title);
        return article;
    }

    public String getUglyArticleByNameValue(String title) throws ArticleNotFoundException, JsonProcessingException {
        log.info("Getting article with name: {}", title);
        String article = service.handleGetRequestUglyArticleByName(title);
        log.info("Successfully got article with name: {}", title);
        return article;
    }

    public void updateArticleTitle(String name, String title) throws ArticleNotFoundException {
        log.info("Trying to change article {} with title: {}", name, title);
        service.handleUpdateRequestArticleTitle(name, title);
        log.info("Successfully changed article {} title: {}", name, title);
    }

    public void updateCategoryByArticleName(String name, CategoryList categoryList) throws ArticleNotFoundException {
        log.info("Trying to change article {} with categories: {}", name, categoryList.getCategories());
        service.handleUpdateRequestArticleCategory(name, categoryList);
        log.info("Successfully changed article {} with categories: {}", name, categoryList.getCategories());
    }

    public void updateAuxiliaryTextByArticleName(String name, AuxiliaryTextList auxiliaryTextList) throws ArticleNotFoundException {
        log.info("Trying to change article {} with auxiliary text: {}", name, auxiliaryTextList.getAuxiliaryTextList());
        service.handleUpdateRequestArticleAuxiliaryText(name, auxiliaryTextList);
        log.info("Successfully changed article {} with auxiliary text: {}", name, auxiliaryTextList.getAuxiliaryTextList());
    }

    public Map<String, Integer> getCategoryStatistics() {
        log.info("Trying to get category statistics");
        Map<String, Integer> result = service.handleGetRequestStatistics();
        log.info("Successfully got category statistics");
        return result;
    }

    public ModelAndView getHtmlArticleByNameValue(String name) throws ArticleNotFoundException {
        log.info("Trying to get html view for article {}", name);
        ModelAndView modelAndView = service.handleGetRequestHtmlArticleByName(name);
        log.info("Successfully got html view for article {}", name);
        return modelAndView;
    }
}
