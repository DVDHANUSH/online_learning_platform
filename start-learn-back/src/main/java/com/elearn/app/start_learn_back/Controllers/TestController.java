package com.elearn.app.start_learn_back.Controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String testing(){
        return "testing";
    }
}