package com.elearn.app.start_learn_back.Repositories;

import com.elearn.app.start_learn_back.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
