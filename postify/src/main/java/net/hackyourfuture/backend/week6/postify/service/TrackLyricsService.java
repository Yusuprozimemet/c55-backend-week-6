package net.hackyourfuture.backend.week6.postify.service;

import org.springframework.stereotype.Service;

import net.hackyourfuture.backend.week6.postify.client.LyricsApiClient;
import net.hackyourfuture.backend.week6.postify.dto.TrackLyrics;
import net.hackyourfuture.backend.week6.postify.exception.LyricsNotFoundException;
import net.hackyourfuture.backend.week6.postify.exception.TrackNotFoundException;
import net.hackyourfuture.backend.week6.postify.repository.TrackRepository;

@Service
public class TrackLyricsService {
    private final TrackRepository trackRepository;
    private final LyricsApiClient lyricsApiClient;

    public TrackLyricsService(TrackRepository trackRepository, LyricsApiClient lyricsApiClient) {
        this.trackRepository = trackRepository;
        this.lyricsApiClient = lyricsApiClient;
    }

    public TrackLyrics getLyrics(Long trackId) {
        TrackLyrics track = trackRepository.findTrackWithArtist(trackId)
                .orElseThrow(() -> new TrackNotFoundException(trackId));

        String lyrics = lyricsApiClient.fetchLyrics(track.getArtistName(), track.getTrackTitle())
                .orElseThrow(() -> new LyricsNotFoundException(track.getArtistName(), track.getTrackTitle()));

        track.setLyrics(lyrics);
        return track;
    }
}
