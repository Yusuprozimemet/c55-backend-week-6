package net.hackyourfuture.backend.week6.postify.repository;

import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import net.hackyourfuture.backend.week6.postify.dto.TrackLyrics;

@Repository
public class TrackRepository {
    private final JdbcTemplate jdbcTemplate;

    public TrackRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    
    public Optional<TrackLyrics> findTrackWithArtist(Long trackId) {
       
        return Optional.empty();
    }
}
