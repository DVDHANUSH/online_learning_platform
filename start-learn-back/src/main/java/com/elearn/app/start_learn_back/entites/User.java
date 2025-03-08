package com.elearn.app.start_learn_back.entites;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;
@Entity
@Data
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

    private boolean emailVerified;

    private boolean smsVerified;

    private Date createAt;

    private String profilePath;

    private String recentOTP;

    @ManyToMany(mappedBy = "users", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();
   // Fetchtype is ByDefault "LAZY" for "@ManyToMany" and "@OneToMany"

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Order> orders = new ArrayList<>();
    public void assignRole(Role role){
        this.roles.add(role);
        role.getUsers().add(this);
        // "this" -> current class object ("User user = new User()")
        // here the "user" is "this"
    }
    public void removeRole(Role role){
        this.roles.remove(role);
        role.getUsers().remove(this);
    }
}