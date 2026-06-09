package net.hackyourfuture.backend.week6.postify.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TrackNotFoundException extends RuntimeException {
    public TrackNotFoundException(Long id) {
        super("Track not found: " + id);
    }
}
