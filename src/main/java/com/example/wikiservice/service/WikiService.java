package com.example.wikiservice.service;

import com.example.wikiservice.dto.ArticleToPrint;
import com.example.wikiservice.dto.AuxiliaryTextList;
import com.example.wikiservice.dto.CategoryList;
import com.example.wikiservice.entity.Article;
import com.example.wikiservice.entity.AuxiliaryText;
import com.example.wikiservice.entity.Category;
import com.example.wikiservice.exception.ArticleNotFoundException;
import com.example.wikiservice.repository.ArticleRepository;
import com.example.wikiservice.repository.AuxiliaryTextRepository;
import com.example.wikiservice.repository.CategoryRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class WikiService {
    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;
    private final AuxiliaryTextRepository auxiliaryTextRepository;

    public ArticleToPrint handleGetRequestArticleByName(String title) throws ArticleNotFoundException {
        Optional<Article> articleOptional = articleRepository.findArticleByNameCaseInsensitive(title.toLowerCase());
        if (articleOptional.isPresent()) {
            Article article = articleOptional.get();
            return ArticleToPrint.builder()
                    .id(article.getId())
                    .title(article.getTitle())
                    .wiki(article.getWiki())
                    .language(article.getLanguage())
                    .categories(article.getCategories())
                    .auxiliaryText(article.getAuxiliaryText())
                    .createTimeStamp(article.getCreateTimeStamp().atZone(ZoneId.systemDefault()).toEpochSecond())
                    .timeStamp(article.getTimeStamp().atZone(ZoneId.systemDefault()).toEpochSecond())
                    .build();
        }
        throw new ArticleNotFoundException();
    }

    public String handleGetRequestUglyArticleByName(String title) throws ArticleNotFoundException, JsonProcessingException {
        ArticleToPrint article = handleGetRequestArticleByName(title);
        return new ObjectMapper().writeValueAsString(article);
    }

    @Transactional
    public void handleUpdateRequestArticleTitle(String name, String title) throws ArticleNotFoundException {
        Optional<Article> articleOptional = articleRepository.findArticleByNameCaseInsensitive(name.toLowerCase());
        if (articleOptional.isPresent()) {
            Article article = articleOptional.get();
            article.setTitle(title);
            article.setTimeStamp(LocalDateTime.now());
            articleRepository.save(article);
        } else {
            throw new ArticleNotFoundException();
        }
    }

    @Transactional
    public void handleUpdateRequestArticleCategory(String name, CategoryList categoryList) throws ArticleNotFoundException {
        Optional<Article> articleOptional = articleRepository.findArticleByNameCaseInsensitive(name.toLowerCase());
        if (articleOptional.isPresent()) {
            Article article = articleOptional.get();
            List<Category> newCategories = new ArrayList<>();
            for (String category : categoryList.getCategories()) {
                Category newCategory = new Category(category);
                categoryRepository.save(newCategory);
                newCategories.add(newCategory);
            }
            article.setCategories(newCategories);
            article.setTimeStamp(LocalDateTime.now());
            articleRepository.save(article);
        } else {
            throw new ArticleNotFoundException();
        }
    }

    @Transactional
    public void handleUpdateRequestArticleAuxiliaryText(String name, AuxiliaryTextList auxiliaryTextList) throws ArticleNotFoundException {
        Optional<Article> articleOptional = articleRepository.findArticleByNameCaseInsensitive(name.toLowerCase());
        if (articleOptional.isPresent()) {
            Article article = articleOptional.get();
            List<AuxiliaryText> newAuxiliaryText = new ArrayList<>();
            for (String text : auxiliaryTextList.getAuxiliaryTextList()) {
                AuxiliaryText auxiliaryText = new AuxiliaryText(text, article.getId());
                auxiliaryTextRepository.save(auxiliaryText);
                newAuxiliaryText.add(auxiliaryText);
            }
            article.setAuxiliaryText(newAuxiliaryText);
            article.setTimeStamp(LocalDateTime.now());
            articleRepository.save(article);
        } else {
            throw new ArticleNotFoundException();
        }

    }

    public Map<String, Integer> handleGetRequestStatistics() {
        List<String> uniqueCategoriesNames = categoryRepository.getDistinctByCategoryName();

        LinkedHashMap<String, Integer> stats = new LinkedHashMap<>();
        uniqueCategoriesNames.forEach(category -> stats.put(category, 0));

        List<Article> allArticles = articleRepository.findAll();
        for (Article article: allArticles) {
            for (Category category: article.getCategories()) {
                if (stats.containsKey(category.getCategoryName()))
                    stats.put(category.getCategoryName(), stats.get(category.getCategoryName()) + 1);
                else
                    stats.put(category.getCategoryName(), 0);
            }
        }
        LinkedHashMap<String, Integer> reverseSortedMap = new LinkedHashMap<>();

        stats.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));

        return reverseSortedMap;
    }
}
