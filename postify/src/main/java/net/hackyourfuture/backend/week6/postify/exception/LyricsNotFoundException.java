package net.hackyourfuture.backend.week6.postify.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class LyricsNotFoundException extends RuntimeException {
    public LyricsNotFoundException(String artistName, String trackTitle) {
        super("No lyrics found for \"" + trackTitle + "\" by " + artistName + ".");
    }
}
