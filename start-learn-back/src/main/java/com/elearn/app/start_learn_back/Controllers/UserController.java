package com.elearn.app.start_learn_back.Controllers;

import com.elearn.app.start_learn_back.dtos.CourseDto;
import com.elearn.app.start_learn_back.dtos.UserDTO;
import com.elearn.app.start_learn_back.entites.User;
import com.elearn.app.start_learn_back.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseBody
    public UserDTO createUser(@RequestBody UserDTO userDTO){
        System.out.println("The data received from postman : " + userDTO.getName());
      return userService.create(userDTO);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getCourseById(@PathVariable String userId){
        System.out.println("the id received : "+ userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.getById(userId));
    }
}