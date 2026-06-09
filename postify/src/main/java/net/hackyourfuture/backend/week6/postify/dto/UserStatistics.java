package net.hackyourfuture.backend.week6.postify.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserStatistics {
    private long userId;
    private String userName;
    private String userCountry;
    private long totalStreams;
    private long uniqueTracksStreamed;
    private long uniqueArtistsStreamed;
    private String favoriteGenre;
    private long totalListeningTimeSeconds;
    
}
