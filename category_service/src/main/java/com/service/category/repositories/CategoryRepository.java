package com.service.category.repositories;
import com.service.category.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
    List<Category> findByTitleContainingIgnoreCaseOrDescContainingIgnoreCase(String keyword, String keyword1);
    Optional<Category> findByTitleIgnoreCase(String title);
}