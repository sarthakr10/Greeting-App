package com.exampe.Greeting;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/greeting")
public class GreetingController {

    private final GreetingService greetingService;

    public GreetingController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @GetMapping
    public GreetingResponse getGreeting() {
        return new GreetingResponse(greetingService.getGreetingMessage());
    }
}
