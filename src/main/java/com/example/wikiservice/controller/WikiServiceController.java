package com.example.wikiservice.controller;

import com.example.wikiservice.dto.ArticleToPrint;
import com.example.wikiservice.dto.AuxiliaryTextList;
import com.example.wikiservice.dto.CategoryList;
import com.example.wikiservice.exception.ArticleNotFoundException;
import com.example.wikiservice.service.api.WikiApiService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/wiki")
@RequiredArgsConstructor
@Tag(name = "Контроллер для получения информации по статьям из дампа википедии")
public class WikiServiceController {
    private final WikiApiService apiService;

    @Operation(
            summary = "Получение статьи в формате json из базы данных",
            description = "Получаем статью со всеми её данными в формате pretty json."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Article output"),
            @ApiResponse(responseCode = "400", description = "Article name is incorrect", content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected error", content = @Content)
    })
    @Parameter(name = "pretty", in = ParameterIn.QUERY)
    @GetMapping(value = "/{name}", params = {"pretty"})
    public ArticleToPrint getArticleByName(@PathVariable String name) throws ArticleNotFoundException {
        return apiService.getArticleByNameValue(name);
    }

    @Operation(
            summary = "Получение статьи в формате json из базы данных",
            description = "Получаем статью со всеми её данными в формате single line json."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Article output"),
            @ApiResponse(responseCode = "400", description = "Article name is incorrect", content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected error", content = @Content)
    })
    @GetMapping(value = "/{name}")
    public String getUglyArticleByName(@PathVariable String name) throws ArticleNotFoundException, JsonProcessingException {
        return apiService.getUglyArticleByNameValue(name);
    }

    @Operation(
            summary = "Обновление название статьи",
            description = "Позволяет обновить название статьи по прошлому названию"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Article title updated"),
            @ApiResponse(responseCode = "400", description = "Article name is incorrect or smth bad with Params", content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected error", content = @Content)
    })
    @Parameter(name = "title", in = ParameterIn.QUERY, required = true)
    @PostMapping(value = "/{name}/update/title", params = {"title"})
    public void updateArticleName(@PathVariable String name, @RequestParam String title) throws ArticleNotFoundException {
        apiService.updateArticleTitle(name, title);
    }

    @Operation(
            summary = "Обновление текста статьи",
            description = "Метод заменяет весь предыдущий текст статьи на новый, который передается через RequestBody"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Article Auxiliary Text updated"),
            @ApiResponse(responseCode = "400", description = "Article name is incorrect", content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected error", content = @Content)
    })
    @PostMapping(value = "/{name}/update/auxiliary_text")
    public void updateArticleAuxiliaryText(@PathVariable String name, @Valid @RequestBody AuxiliaryTextList auxiliaryTextList) throws ArticleNotFoundException {
        apiService.updateAuxiliaryTextByArticleName(name, auxiliaryTextList);
    }

    @Operation(
            summary = "Обновление категорий статьи",
            description = "Метод заменяет все категории статьи на новые, которые передаются через RequestBody"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Article Categories updated"),
            @ApiResponse(responseCode = "400", description = "Article name is incorrect", content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected error", content = @Content)
    })
    @PostMapping(value = "/{name}/update/category")
    public void updateArticleCategories(@PathVariable String name, @Valid @RequestBody CategoryList categoryList) throws ArticleNotFoundException {
        apiService.updateCategoryByArticleName(name, categoryList);
    }


    @Operation(
            summary = "Получение статистики категорий по статьям",
            description = "Вывод статистики по количеству статей для каждой категории"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Statistics got"),
            @ApiResponse(responseCode = "400", description = "Article name is incorrect", content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected error", content = @Content)
    })
    @GetMapping(value = "/statistics")
    public Map<String, Integer> getStatisticArticleCountByCategories() {
        return apiService.getCategoryStatistics();
    }

    public void getHtmlViewForArticle() {

    }
}
