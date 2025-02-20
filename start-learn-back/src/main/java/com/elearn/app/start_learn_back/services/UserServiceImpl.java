package com.elearn.app.start_learn_back.services;

import com.elearn.app.start_learn_back.Exceptions.ResourceNotPresentException;
import com.elearn.app.start_learn_back.Repositories.RoleRepo;
import com.elearn.app.start_learn_back.Repositories.UserRepository;
import com.elearn.app.start_learn_back.config.AppConstants;
import com.elearn.app.start_learn_back.dtos.CourseDto;
import com.elearn.app.start_learn_back.dtos.UserDTO;
import com.elearn.app.start_learn_back.entites.Course;
import com.elearn.app.start_learn_back.entites.Role;
import com.elearn.app.start_learn_back.entites.User;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private RoleRepo roleRepo;
    private BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, RoleRepo roleRepo, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO create(UserDTO userDTO) {
        System.out.println("I am here" + userDTO.toString());
        User user = modelMapper.map(userDTO, User.class);
        user.setUserId(UUID.randomUUID().toString());
        user.setCreateAt(new Date());
        user.setEmailVerified(false);
        user.setSmsVerified(false);
        user.setProfilePath(AppConstants.DEFAULT_PROFILE_PIC_PATH);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        // role
       Role role = roleRepo.findByRoleName(AppConstants.ROLE_GUEST).orElseThrow(()-> new ResourceNotPresentException("Role Not present, connect with admin"));
        user.assignRole(role);
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDTO.class);
    }

    @Override
    public UserDTO getById(String userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("User not present"));
        return modelMapper.map(user, UserDTO.class);
    }
}