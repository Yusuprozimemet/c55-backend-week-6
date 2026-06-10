package net.hackyourfuture.backend.week6.postify.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import net.hackyourfuture.backend.week6.postify.dto.TrackLyrics;
import net.hackyourfuture.backend.week6.postify.exception.LyricsNotFoundException;
import net.hackyourfuture.backend.week6.postify.exception.TrackNotFoundException;
import net.hackyourfuture.backend.week6.postify.repository.TrackRepository;

@Service
public class TrackLyricsService {
    private final TrackRepository trackRepository;
    private final RestClient lyricsRestClient;

    public TrackLyricsService(TrackRepository trackRepository, RestClient lyricsRestClient) {
        this.trackRepository = trackRepository;
        this.lyricsRestClient = lyricsRestClient;
    }

    public TrackLyrics getLyrics(Long trackId) {
        TrackLyrics track = trackRepository.findTrackWithArtist(trackId)
                .orElseThrow(() -> new TrackNotFoundException(trackId));

        try {
            LyricsResponse response = lyricsRestClient.get()
                    .uri("/{artist}/{title}", track.getArtistName(), track.getTrackTitle())
                    .retrieve()
                    .body(LyricsResponse.class);

            if (response == null || response.lyrics() == null || response.lyrics().isBlank()) {
                throw new LyricsNotFoundException(track.getArtistName(), track.getTrackTitle());
            }
            track.setLyrics(response.lyrics());
        } catch (HttpClientErrorException.NotFound e) {
            throw new LyricsNotFoundException(track.getArtistName(), track.getTrackTitle());
        }

        return track;
    }

    // Shape of the lyrics.ovh response: {"lyrics": "..."}
    private record LyricsResponse(String lyrics) {
    }
}
