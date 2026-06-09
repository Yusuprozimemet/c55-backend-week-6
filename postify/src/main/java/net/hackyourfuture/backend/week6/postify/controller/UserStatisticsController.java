package net.hackyourfuture.backend.week6.postify.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import net.hackyourfuture.backend.week6.postify.dto.UserStatistics;
import net.hackyourfuture.backend.week6.postify.service.UserStatisticsService;

@RestController
public class UserStatisticsController {
    private final UserStatisticsService service;

    public UserStatisticsController(UserStatisticsService service){
        this.service = service;
    }
    @GetMapping("/users/{id}/stats")
    public UserStatistics getStats(@PathVariable Long id) {
        return service.getStats(id); 
    }
    
}
