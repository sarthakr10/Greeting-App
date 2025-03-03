package com.exampe.Greeting;

import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/greeting")
public class GreetingController {

    private final GreetingService greetingService;

    public GreetingController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @PostMapping
    public GreetingResponse saveGreeting(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName) {

        Greeting greeting = greetingService.saveGreeting(firstName, lastName);
        return new GreetingResponse(greeting.getMessage());
    }

    @GetMapping("/{id}")
    public GreetingResponse getGreeting(@PathVariable Long id) {
        Optional<Greeting> greeting = greetingService.getGreetingById(id);
        return greeting.map(g -> new GreetingResponse(g.getMessage()))
                .orElse(new GreetingResponse("Greeting not found"));
    }
}
