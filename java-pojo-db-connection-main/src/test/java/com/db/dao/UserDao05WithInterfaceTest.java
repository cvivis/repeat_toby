package com.db.dao;

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserDaoFactory2.class)
public class UserDao05WithInterfaceTest {

    @Autowired
    ApplicationContext context;

    @Test
    public void testName() throws SQLException, ClassNotFoundException {
        UserDao05WithInterface userDao = context.getBean("userDao", UserDao05WithInterface.class);
        userDao.add();
    }
}