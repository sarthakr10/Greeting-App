package com.exampe.Greeting;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/greeting")
public class GreetingController {

    @GetMapping
    public GreetingResponse getGreeting() {
        return new GreetingResponse("Hello from GET!");
    }

    @PostMapping
    public GreetingResponse postGreeting() {
        return new GreetingResponse("Hello from POST!");
    }

    @PutMapping
    public GreetingResponse putGreeting() {
        return new GreetingResponse("Hello from PUT!");
    }

    @DeleteMapping
    public GreetingResponse deleteGreeting() {
        return new GreetingResponse("Hello from DELETE!");
    }
}
