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
        String sql = """
            SELECT tracks.track_id, tracks.track_title, artists.artist_name
            FROM tracks
            JOIN albums  ON albums.album_id   = tracks.album_id
            JOIN artists ON artists.artist_id = albums.artist_id
            WHERE tracks.track_id = ?
            """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> TrackLyrics.builder()
                        .trackId(rs.getLong("track_id"))
                        .trackTitle(rs.getString("track_title"))
                        .artistName(rs.getString("artist_name"))
                        .build(),
                trackId)
                .stream()
                .findFirst();
    }
}
