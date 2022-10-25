package com.db.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

@Configuration
public class DaoFactory {
    @Bean
    public UserDao awsUserDao() throws SQLException {
        return new UserDao(new AwsConnectionMaker().makeConnection());
    }
    @Bean
    public UserDao localUserDao() throws SQLException {
        return new UserDao(new LocalConnectionMaker().makeConnection());
    }
}
