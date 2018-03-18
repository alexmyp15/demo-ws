package com.sg.website.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.sg.website.annotation.LoginRequired;
import com.sg.website.entities.User;
import com.sg.website.service.UserService;
import org.apache.ibatis.annotations.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception{

        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        //decide require login or not
        LoginRequired methodAnnotation = method.getAnnotation(LoginRequired.class);

        //IF have @LoginRequired annotation, veritify
        if(methodAnnotation != null){
            //excute veritify process
            String token = request.getHeader("token");
            if(token == null){
                throw new RuntimeException("No token,Please Login");
            }
            int uid;
            try{
                uid = Integer.parseInt(JWT.decode(token).getAudience().get(0)); //get userId from token
            }catch (JWTDecodeException e){
                throw new RuntimeException("Token invaild,Please Login agian");
            }
            User user = userService.findById(uid);
            if(user == null){
                throw new RuntimeException("User not exist");
            }

            //verify token

            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
            try{
                verifier.verify(token);
            }catch (JWTVerificationException e){
                throw new RuntimeException("Invaild token,Please Login");
            }
            request.setAttribute("currentUser",user);
            return true;
        }
        return true;
    }
}
