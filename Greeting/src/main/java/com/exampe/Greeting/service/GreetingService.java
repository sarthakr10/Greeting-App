package com.exampe.Greeting.service;

import com.exampe.Greeting.model.Greeting;
import com.exampe.Greeting.repository.GreetingRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class GreetingService {
    private final GreetingRepository greetingRepository;

    public GreetingService(GreetingRepository greetingRepository) {
        this.greetingRepository = greetingRepository;
    }

    public Greeting saveGreeting(String message) {
        Greeting greeting = new Greeting(message);
        return greetingRepository.save(greeting);
    }

    public Optional<Greeting> getGreetingById(Long id) {
        return greetingRepository.findById(id);
    }
}