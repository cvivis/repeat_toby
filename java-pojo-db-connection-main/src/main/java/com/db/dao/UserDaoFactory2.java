package com.db.dao;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map;

@Configuration
public class UserDaoFactory2 {
    @Bean
    public UserDao05WithInterface userDao() {
        MySqlConnectionMaker mySqlConnectionMaker = new MySqlConnectionMaker();
        return new UserDao05WithInterface(mySqlConnectionMaker);
    }
}
