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

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

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
        Optional<Role> adminRole = roleRepo.findByRoleName(AppConstants.ROLE_ADMIN);
        System.out.println("Found role: " + adminRole.toString());
        Role role1 = new Role();
        role1.setRoleName(AppConstants.ROLE_ADMIN);

        role1.setRoleId(UUID.randomUUID().toString());
        Role role2 = new Role();
        role2.setRoleName(AppConstants.ROLE_GUEST);
        role2.setRoleId(UUID.randomUUID().toString());
		roleRepo.findByRoleName(AppConstants.ROLE_ADMIN).ifPresentOrElse(
				role -> System.out.println(role.getRoleName() + " already in database "),
				() -> {
					System.out.println("saving r1");
					roleRepo.save(role1);
				}
		);

		roleRepo.findByRoleName(AppConstants.ROLE_GUEST).ifPresentOrElse(
                role -> {
                    System.out.println(role.getRoleName() + " already in database ");
                },
                () -> {
                    System.out.println("saving r2");
                    roleRepo.save(role2);
                }
        );

//        User user = new User();
//        user.setUserId(UUID.randomUUID().toString());
//        user.setName("Dhanush");
//        user.assignRole(role2);
//        user.assignRole(role1);
//        user.setActive(true);
//        user.setPassword(passwordEncoder.encode("dvd12345"));
//        user.setProfilePath(AppConstants.DEFAULT_PROFILE_PIC_PATH);
//        user.setEmail("dhanush0909@gmail.com");
//        user.setEmailVerified(false);
//        user.setSmsVerified(false);
//        userRepository.save(user);
//        System.out.println("Created user");
    }
}