package com.service.course.repositories;
import com.service.course.entities.CustomPropConfig;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomPropConfigRepository extends JpaRepository<CustomPropConfig, String> {

    Optional<List<CustomPropConfig>> findByConfigId(String configId);

    Optional<CustomPropConfig> findByConfigIdAndPropertyKey(String configId, String key);

    @Modifying
    @Transactional
    @Query(
            value =
                    "DELETE FROM CustomPropConfig c WHERE c.configId = :configId AND c.propertyKey = :propertyKey")
    void deleteByConfigIdAndPropertyKey(String configId, String propertyKey);
}