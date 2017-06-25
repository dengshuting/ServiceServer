package test.controller;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.http.MediaType;
import test.BaseTest;
import test.RestControllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class APIControllerTest extends RestControllerTest {

    @Test
    public void testSum() throws Exception {
        mockMvc.perform(get(URI_SUM)
            .param("x", "10")
            .param("y", "10"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(1)))
            .andExpect(jsonPath("$.ans").value(20));
    }

    @Test
    public void testProduct() throws Exception {
        mockMvc.perform(post(URI_PRODUCT)
            .param("x", "10")
            .param("y", "10"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(1)))
            .andExpect(jsonPath("$.ans").value(100));
    }

    @Test
    public void testPositioning() throws Exception {
        mockMvc.perform(get(URI_POSITIONING)
            .param("alpha", "45")
            .param("beta", "45")
            .param("x1", "-1")
            .param("y1", "0")
            .param("x2", "0")
            .param("y2", "-1")
            .param("x3", "1")
            .param("y3", "0"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*").value(Matchers.hasSize(2)))
            .andExpect(jsonPath("$.x").isNumber())
            .andExpect(jsonPath("$.y").isNumber());
    }

}
