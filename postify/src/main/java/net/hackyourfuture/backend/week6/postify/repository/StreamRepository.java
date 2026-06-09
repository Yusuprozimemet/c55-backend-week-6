package net.hackyourfuture.backend.week6.postify.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class StreamRepository {
     private final JdbcTemplate jdbcTemplate;

    public StreamRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
