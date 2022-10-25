package com.db;

import com.db.dao.UserDao05WithInterface;
import com.db.dao.UserDaoFactory;
import com.db.dao.UserDaoFactory2;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

public class FactoryMain {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDaoFactory factory = new UserDaoFactory();
        UserDao05WithInterface userDao = factory.userDao();
        userDao.add();
    }
}
