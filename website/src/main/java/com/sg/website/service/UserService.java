package com.sg.website.service;

import com.sg.website.entities.User;
import com.sg.website.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class UserService {


    private UserMapper userMapper;

    @Autowired
    public UserService(UserMapper userMapper){
        this.userMapper = userMapper;
    }

    private String hashPassword(String pass){
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(pass.getBytes());
            byte[] src = digest.digest();
            StringBuilder stringBuilder = new StringBuilder();
            //字节数组 -》 16进制字符串
            for(byte asrc: src){
                String s = Integer.toHexString(asrc & 0XFF);
                if(s.length()<2){
                    stringBuilder.append('0');
                }
                stringBuilder.append(s);
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean comparePassword(User user, User userInDB){
        return hashPassword(user.getPassword()).equals(userInDB.getPassword());
    }

    public User add(User user){
        String passwordHash = hashPassword(user.getPassword());
        user.setPassword(passwordHash);
        userMapper.add(user);
        return user;
    }

    public User findByEmail(String email){
        User user = new User();
        user.setEmail(email);
        return userMapper.findOne(user);
    }

    public User findById(int id){
        User user = new User();
        user.setUid(id);
        return userMapper.findOne(user);
    }

}
