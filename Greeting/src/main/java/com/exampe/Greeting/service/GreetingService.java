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

    public Greeting getGreetingById(Long id) {
        return greetingRepository.findById(id).orElse(null);
    }

    public Greeting updateGreeting(Long id, String newMessage) {
        Optional<Greeting> existingGreeting = greetingRepository.findById(id);
        if (existingGreeting.isPresent()) {
            Greeting greeting = existingGreeting.get();
            greeting.setMessage(newMessage);
            return greetingRepository.save(greeting);
        }
        return null; // Return null if ID not found
    }

    public boolean deleteGreeting(Long id) {
        if (greetingRepository.existsById(id)) {
            greetingRepository.deleteById(id);
            return true; // Return true if deletion is successful
        }
        return false; // Return false if ID does not exist
    }
}