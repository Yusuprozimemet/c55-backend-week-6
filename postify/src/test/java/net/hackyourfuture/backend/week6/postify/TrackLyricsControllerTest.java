package net.hackyourfuture.backend.week6.postify;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import net.hackyourfuture.backend.week6.postify.client.LyricsApiClient;
import net.hackyourfuture.backend.week6.postify.exception.LyricsNotFoundException;
import net.hackyourfuture.backend.week6.postify.exception.TrackNotFoundException;

/**
 * Integration tests for GET /tracks/{id}/lyrics.
 * Requires the POSTIFY Postgres database (Docker) with seed data;
 * the external lyrics API client is replaced with a Mockito mock,
 * so no internet connection is needed.
 */
@SpringBootTest
@AutoConfigureMockMvc
class TrackLyricsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LyricsApiClient lyricsApiClient;

    // Seed data: track 41 = 'LUNCH' by Billie Eilish

    @Test
    void returnsLyricsForTrackThatHasLyrics() throws Exception {
        when(lyricsApiClient.fetchLyrics("Billie Eilish", "LUNCH"))
                .thenReturn(Optional.of("Mocked lyrics line one"));

        mockMvc.perform(get("/tracks/41/lyrics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.trackId").value(41))
                .andExpect(jsonPath("$.trackTitle").value("LUNCH"))
                .andExpect(jsonPath("$.artistName").value("Billie Eilish"))
                .andExpect(jsonPath("$.lyrics").value("Mocked lyrics line one"));
    }

    @Test
    void returns404ForUnknownTrack() throws Exception {
        mockMvc.perform(get("/tracks/999999/lyrics"))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertThat(result.getResolvedException())
                        .isInstanceOf(TrackNotFoundException.class));

        // The service must not call the API for a track that is not in the database.
        verifyNoInteractions(lyricsApiClient);
    }

    @Test
    void returns404WithMessageWhenApiHasNoLyrics() throws Exception {
        when(lyricsApiClient.fetchLyrics("Billie Eilish", "LUNCH"))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/tracks/41/lyrics"))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertThat(result.getResolvedException())
                        .isInstanceOf(LyricsNotFoundException.class)
                        .hasMessageContaining("No lyrics found for \"LUNCH\" by Billie Eilish"));
    }
}
