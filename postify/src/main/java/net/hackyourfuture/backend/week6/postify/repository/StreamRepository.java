package net.hackyourfuture.backend.week6.postify.repository;

import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import net.hackyourfuture.backend.week6.postify.dto.UserStatistics;

@Repository
public class StreamRepository {
    private final JdbcTemplate jdbcTemplate;

    public StreamRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<UserStatistics> findStats(Long userId) {
        String sql = """
            SELECT users.user_id, users.user_name, users.user_country,
                   COUNT(streams.stream_id)                    AS total_streams,
                   COUNT(DISTINCT streams.track_id)            AS unique_tracks,
                   COUNT(DISTINCT albums.artist_id)            AS unique_artists,
                   COALESCE(SUM(tracks.track_duration_s), 0)   AS total_listening_seconds
            FROM users
            LEFT JOIN streams ON streams.user_id  = users.user_id
            LEFT JOIN tracks  ON tracks.track_id  = streams.track_id
            LEFT JOIN albums  ON albums.album_id  = tracks.album_id
            WHERE users.user_id = ?
            GROUP BY users.user_id, users.user_name, users.user_country
            """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> UserStatistics.builder()
                        .userId(rs.getLong("user_id"))
                        .userName(rs.getString("user_name"))
                        .userCountry(rs.getString("user_country"))
                        .totalStreams(rs.getLong("total_streams"))
                        .uniqueTracksStreamed(rs.getLong("unique_tracks"))
                        .uniqueArtistsStreamed(rs.getLong("unique_artists"))
                        .totalListeningTimeSeconds(rs.getLong("total_listening_seconds"))
                        .build(),
                userId)
                .stream()
                .findFirst();
    }


    public Optional<String> findFavoriteGenre(Long userId) {
        String sql = """
            SELECT tracks.genre
            FROM streams
            JOIN tracks ON tracks.track_id = streams.track_id
            WHERE streams.user_id = ? AND tracks.genre IS NOT NULL
            GROUP BY tracks.genre
            ORDER BY COUNT(*) DESC
            LIMIT 1
            """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getString("genre"), userId)
                .stream()
                .findFirst();
    }
}
