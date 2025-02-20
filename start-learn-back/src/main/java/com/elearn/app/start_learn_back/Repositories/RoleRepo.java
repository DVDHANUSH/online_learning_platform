package com.elearn.app.start_learn_back.Repositories;

import com.elearn.app.start_learn_back.entites.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface RoleRepo extends JpaRepository<Role, String> {
    Optional<Role> findByRoleName(String roleName);
}

