package net.hackyourfuture.backend.week6.postify.client;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

/**
 * Wraps the external lyrics.ovh API call.
 * Returns Optional.empty() when the API has no lyrics for the song.
 */
@Component
public class LyricsApiClient {
    private final RestClient lyricsRestClient;

    public LyricsApiClient(RestClient lyricsRestClient) {
        this.lyricsRestClient = lyricsRestClient;
    }

    public Optional<String> fetchLyrics(String artistName, String trackTitle) {
        try {
            LyricsResponse response = lyricsRestClient.get()
                    .uri("/{artist}/{title}", artistName, trackTitle)
                    .retrieve()
                    .body(LyricsResponse.class);

            if (response == null || response.lyrics() == null || response.lyrics().isBlank()) {
                return Optional.empty();
            }
            return Optional.of(response.lyrics());
        } catch (HttpClientErrorException.NotFound e) {
            return Optional.empty();
        }
    }

    // Shape of the lyrics.ovh response: {"lyrics": "..."}
    private record LyricsResponse(String lyrics) {
    }
}
