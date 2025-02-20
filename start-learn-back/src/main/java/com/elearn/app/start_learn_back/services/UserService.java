package com.elearn.app.start_learn_back.services;

import com.elearn.app.start_learn_back.dtos.UserDTO;

public interface UserService {
    UserDTO create(UserDTO userDTO);
    UserDTO getById(String userId);
}
