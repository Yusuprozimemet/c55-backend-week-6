package net.hackyourfuture.backend.week6.postify.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import net.hackyourfuture.backend.week6.postify.dto.TrackLyrics;
import net.hackyourfuture.backend.week6.postify.service.TrackLyricsService;

@RestController
public class TrackLyricsController {
    private final TrackLyricsService service;

    public TrackLyricsController(TrackLyricsService service) {
        this.service = service;
    }

    @GetMapping("/tracks/{id}/lyrics")
    public TrackLyrics getLyrics(@PathVariable Long id) {
        return service.getLyrics(id);
    }

}
