package com.example.wikiservice.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "auxiliarytext")
public class AuxiliaryText {
    @Id
    @Column(name = "auxiliary_text_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "article")
    private Integer article;

    @Column(name = "text")
    private String text;

    public AuxiliaryText(String text, Integer article) {
        this.text = text;
        this.article = article;
    }

    public AuxiliaryText() {

    }
}
