package com.sg.website.mapper;

import com.sg.website.entities.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

//    @Insert("insert into user(name,password) values(#{name},#{password})")
//    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    int add(User user);


    User findOne(User user);
}
