package net.hackyourfuture.backend.week6.postify.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TrackLyrics {
    private long trackId;
    private String trackTitle;
    private String artistName;
    private String lyrics;
}
