package com.elearn.app.start_learn_back;
import com.elearn.app.start_learn_back.Repositories.RoleRepo;
import com.elearn.app.start_learn_back.Repositories.UserRepository;
import com.elearn.app.start_learn_back.config.AppConstants;
import com.elearn.app.start_learn_back.entites.Role;
import com.elearn.app.start_learn_back.entites.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;
import java.util.stream.Collectors;

@SpringBootApplication
public class StartLearnBackApplication implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepo roleRepo;

    public static void main(String[] args) {
        SpringApplication.run(StartLearnBackApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Checking role: " + AppConstants.ROLE_ADMIN);
//        Optional<Role> adminRole = roleRepo.findByRoleName(AppConstants.ROLE_ADMIN);
//        System.out.println("Found role: " + adminRole.toString());
//        Role role1 = new Role();
//        role1.setRoleName(AppConstants.ROLE_ADMIN);
//
//        role1.setRoleId(UUID.randomUUID().toString());
//        Role role2 = new Role();
//        role2.setRoleName(AppConstants.ROLE_GUEST);
//        role2.setRoleId(UUID.randomUUID().toString());
//		roleRepo.findByRoleName(AppConstants.ROLE_ADMIN).ifPresentOrElse(
//				role -> System.out.println(role.getRoleName() + " already in database "),
//				() -> {
//					System.out.println("saving r1");
//					roleRepo.save(role1);
//				}
//		);
//
//		roleRepo.findByRoleName(AppConstants.ROLE_GUEST).ifPresentOrElse(
//                role ->
//                    System.out.println(role.getRoleName() + " already in database "),
//                () -> {
//                    System.out.println("saving r2");
//                    roleRepo.save(role2);
//                }
//        );
        List<String> existingRoles = roleRepo.findAll()
                .stream()
                .map(Role::getRoleName)
                .collect(Collectors.toList());

        List<Role> rolesToAdd = new ArrayList<>();
        Role roleAdmin = new Role();
        roleAdmin.setRoleId(UUID.randomUUID().toString());
        roleAdmin.setRoleName(AppConstants.ROLE_ADMIN);

        Role roleGuest = new Role();
        roleGuest.setRoleId(UUID.randomUUID().toString());
        roleGuest.setRoleName(AppConstants.ROLE_GUEST);
        if (!existingRoles.contains(AppConstants.ROLE_ADMIN)) {
            System.out.println("Saving ROLE_ADMIN");
            rolesToAdd.add(roleAdmin);
        }

        if (!existingRoles.contains(AppConstants.ROLE_GUEST)) {
            System.out.println("Saving ROLE_GUEST");
            rolesToAdd.add(roleGuest);
        }

// Save only if there are new roles to add
        if (!rolesToAdd.isEmpty()) {
            roleRepo.saveAll(rolesToAdd);
        }
//        User user = new User();
//        user.setUserId(UUID.randomUUID().toString());
//        user.setName("Kashyap");
//
//        // check the "db" before you assign the role
//
//
//        Role savedRoleGuest = roleRepo.findByRoleName(AppConstants.ROLE_GUEST)
//                .orElseThrow(() -> new RuntimeException("ROLE_GUEST not found in the database"));
//       // user.assignRole(roleGuest);
//        user.assignRole(savedRoleGuest);
//        user.setActive(true);
//        user.setPassword(passwordEncoder.encode("dvd12345"));
//        user.setProfilePath(AppConstants.DEFAULT_PROFILE_PIC_PATH);
//        user.setEmail("kashyap5959@gmail.com");
//        user.setEmailVerified(false);
//        user.setSmsVerified(false);
//        userRepository.save(user);
//        System.out.println("Created user");
    }
}