package net.hackyourfuture.backend.week6.postify.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import net.hackyourfuture.backend.week6.postify.dto.TrackLyrics;
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
        
        return null;
    }
}
