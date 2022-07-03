package com.example.wikiservice.repository;

import com.example.wikiservice.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {
    @Query(value = "select * from article where lower(article.title) = ?1", nativeQuery = true)
    Optional<Article> findArticleByNameCaseInsensitive(String title);
}
