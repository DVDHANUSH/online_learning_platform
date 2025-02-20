package com.elearn.app.start_learn_back.Repositories;

import com.elearn.app.start_learn_back.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
}