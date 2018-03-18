package com.sg.website.api;

import com.sg.website.annotation.LoginRequired;
import com.sg.website.entities.User;
import com.sg.website.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserApi {

    @Autowired
    private UserService userService;


    @PostMapping("")
    public Object add(@RequestBody User user){
        if(userService.findByEmail(user.getEmail()) !=null){

            return 0;
        }
        return  userService.add(user);
    }

    @GetMapping("/test")
    @LoginRequired
    public Object testLogin(){
        return "success";
    }

}
