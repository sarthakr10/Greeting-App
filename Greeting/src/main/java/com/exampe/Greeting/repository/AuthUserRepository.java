package com.exampe.Greeting.repository;


import com.exampe.Greeting.model.AuthUser;
import com.exampe.Greeting.model.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {
    Optional<AuthUser> findByEmail(String email);
}
