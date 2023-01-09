package com.andela.irrigationsystem.controller;

import com.andela.irrigationsystem.ObjectBuilder;
import com.andela.irrigationsystem.service.PlotService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Brume
 **/
@WebMvcTest(PlotController.class)
@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
class PlotControllerTest {
    @Autowired
    WebApplicationContext webApplicationContext;
    @Autowired
    private MockMvc mvc;
    @MockBean
    private PlotService service;

    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    @Test
    void addPlotWhenEndpointIsWrong() throws Exception {
        var request = ObjectBuilder.plotDto();

        mvc.perform(post("/rest/plots")
                        .content(mapToJson(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError());

        verifyNoInteractions(service);
    }
}