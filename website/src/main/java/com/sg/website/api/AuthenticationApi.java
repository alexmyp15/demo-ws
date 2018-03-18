package com.sg.website.api;

import com.sg.website.entities.User;
import com.sg.website.service.AuthenticationService;
import com.sg.website.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authentication")
public class AuthenticationApi {

    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private UserService userService;

    @PostMapping("")
    public Object login(@RequestBody User user){
        User userInDB = userService.findByEmail(user.getEmail());

        if(userInDB == null){
            return "User not exist";
        }
        else if(!userService.comparePassword(user,userInDB)) {
            return "Username/Password incorrect";
        }
        else {
            String token = authenticationService.getToken(userInDB);
            return token;
        }
    }

}
