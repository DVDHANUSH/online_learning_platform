package com.elearn.app.start_learn_back.entites;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "orders_mindup")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String razorpayOrderId;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private String pmtStatus;

    private LocalDate createdDate;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false) // Ensure FK is created
    private Course course;

//    @Column(nullable = false)
//    private String userId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Lob
    @Column(name = "address", columnDefinition = "TEXT")
    private String address;
}