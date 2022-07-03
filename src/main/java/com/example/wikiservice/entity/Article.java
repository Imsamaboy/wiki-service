package com.example.wikiservice.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "article")
public class Article {
    @Id
    @Column(name = "article_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "language")
    private String language;

    @Column(name = "wiki")
    private String wiki;

    @Column(name = "create_timestamp")
    private LocalDateTime createTimeStamp;

    @Column(name = "timestamp")
    private LocalDateTime timeStamp;

    @ManyToMany
    @JoinTable(
            name = "article_category",
            joinColumns = @JoinColumn(name = "article"),
            inverseJoinColumns = @JoinColumn(name = "category")
    )
    private List<Category> categories;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="article")
    private List<AuxiliaryText> auxiliaryText;
}
