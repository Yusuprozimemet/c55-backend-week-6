package net.hackyourfuture.backend.week6.postify.service;

import org.springframework.stereotype.Service;

import net.hackyourfuture.backend.week6.postify.dto.UserStatistics;
import net.hackyourfuture.backend.week6.postify.exception.UserNotFoundException;
import net.hackyourfuture.backend.week6.postify.repository.StreamRepository;

@Service
public class UserStatisticsService {
    private final StreamRepository streamRepository;

    public UserStatisticsService(StreamRepository streamRepository) {
        this.streamRepository = streamRepository;
    }

    public UserStatistics getStats(Long id) {
        // Fetch the aggregate row. Empty means the user id does not exist -> 404.
        UserStatistics stats = streamRepository.findStats(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        // Favorite genre is a separate query; absent when the user has no streams.
        streamRepository.findFavoriteGenre(id).ifPresent(stats::setFavoriteGenre);

        return stats;
    }
}
