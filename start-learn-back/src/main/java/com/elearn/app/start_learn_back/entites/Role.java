package com.elearn.app.start_learn_back.entites;

import com.fasterxml.jackson.databind.ser.std.UUIDSerializer;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    private String roleId;
    private String roleName;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "roles_users")
    private Set<User> users = new HashSet<>();
    public Role(String roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }
// here by default "FetchType" is LAZY
}