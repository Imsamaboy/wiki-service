package com.example.wikiservice.repository;

import com.example.wikiservice.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query(value = "select distinct (category_name) from category", nativeQuery = true)
    List<String> getDistinctByCategoryName();
}
