package zephyr.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import zephyr.TreasureBoxTddApp;

import java.nio.charset.StandardCharsets;

/**
 * Created by zephyr on 2019-09-30.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {TreasureBoxTddApp.class})
@AutoConfigureMockMvc
class TestLoginControllerMock {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void wrongPasswordShouldRedirectToErrorPage() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8.name())
                        // .param("xxx", "xxx")
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("{}"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

}
