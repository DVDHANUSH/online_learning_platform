package com.elearn.app.start_learn_back.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class PageController {
    @RequestMapping("/client-login")
    public String loginPage(){
        return "login";
    }
//    @RequestMapping("/client-login-process")
//    public String afterLoginPage(){
//        return "success";
//    }
    @RequestMapping("/success")
    public String  successPage(){
        return "success";
    }
}