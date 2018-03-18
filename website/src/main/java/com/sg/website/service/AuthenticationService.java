package com.sg.website.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.sg.website.entities.User;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class AuthenticationService {

    public String getToken(User user){
        String token = "";
        try{
            token = JWT.create().withAudience(user.getUid().toString()).sign(Algorithm.HMAC256(user.getPassword()));
        }catch (Exception e){
            System.out.println(e);
        }
        return token;
    }
}

