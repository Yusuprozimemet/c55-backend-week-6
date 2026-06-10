package net.hackyourfuture.backend.week6.postify;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for GET /users/{id}/stats.
 * Requires the POSTIFY Postgres database (Docker) with seed data.
 */
@SpringBootTest
@AutoConfigureMockMvc
class UserStatisticsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void returnsStatsForExistingUser() throws Exception {
        mockMvc.perform(get("/users/1/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.userName").value("lena_v"))
                .andExpect(jsonPath("$.userCountry").value("NL"))
                .andExpect(jsonPath("$.favoriteGenre").value("Nederpop"));
    }

    @Test
    void returns404ForUnknownUser() throws Exception {
        mockMvc.perform(get("/users/999999/stats"))
                .andExpect(status().isNotFound());
    }
}
