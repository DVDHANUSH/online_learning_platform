package com.elearn.app.start_learn_back.entites;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private String userId;

    private String name;

    // username=email
    @Column(unique = true)
    private String email;

    private String phoneNumber;

    private String password;

    private String about;

    private boolean active;

    private boolean emailVarified;

    private boolean smsVerified;

    private Date createAt;

    private String profilePath;

    private String recentOTP;

    @ManyToMany(mappedBy = "users", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();
}