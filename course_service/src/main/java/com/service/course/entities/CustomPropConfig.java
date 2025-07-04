package com.service.course.entities;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(
        uniqueConstraints = {
            @UniqueConstraint(
                    name = "unique_ConfigId_Key",
                    columnNames = {"config_Id", "property_key"})
        })
public class CustomPropConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "config_Id")
    private String configId;

    @Column(name = "property_key")
    private String propertyKey;

    @Column(name = "property_value")
    private String propertyValue;
}