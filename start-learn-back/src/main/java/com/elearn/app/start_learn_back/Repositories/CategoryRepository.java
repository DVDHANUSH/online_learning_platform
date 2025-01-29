package com.elearn.app.start_learn_back.Repositories;

import com.elearn.app.start_learn_back.entites.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {
}
