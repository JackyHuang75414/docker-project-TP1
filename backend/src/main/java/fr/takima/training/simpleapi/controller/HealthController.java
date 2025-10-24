package fr.takima.training.simpleapi.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    
    @GetMapping("/health")
    public String health() {
        return "{\"status\": \"UP\"}";
    }
    
    @GetMapping("/test")
    public String test() {
        return "Test endpoint working!";
    }
}