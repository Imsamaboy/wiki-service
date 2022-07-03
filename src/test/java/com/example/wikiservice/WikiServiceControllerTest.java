package com.example.wikiservice;

import com.example.wikiservice.controller.WikiServiceController;
import com.example.wikiservice.dto.ArticleToPrint;
import com.example.wikiservice.entity.AuxiliaryText;
import com.example.wikiservice.service.api.WikiApiService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = WikiServiceController.class)
@AutoConfigureMockMvc(addFilters = false)
class WikiServiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    WikiApiService wikiApiService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGoodGetArticleByName() throws Exception {
//     given
//        String name = "Физики СССР";
//        when(chartographerService.createCharta(createChartaDto)).thenReturn("1");
//        AuxiliaryText auxiliaryText = new AuxiliaryText("", 10);
//        ArticleToPrint articleToPrint = ArticleToPrint.builder()
//                .id(10)
//                .title("Физики СССР")
//                .language("ru")
//                .wiki("ruwikiquote")
//                .createTimeStamp(1332581196L)
//                .timeStamp(1334779259L)
//                .build();
//
//        when(wikiApiService.getArticleByNameValue(name)).thenReturn(articleToPrint);
//
//        // when
//        mockMvc.perform(post("/wiki/{}", name)
//                        .content(objectMapper.writeValueAsString(articleToPrint)))
//                // then
//                .andDo(print())
//                .andExpect(status().isOk());
    }
}
