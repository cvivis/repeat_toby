package com.db.dao;

import com.db.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.annotation.Annotation;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DaoFactory.class)
class UserDaoTest {
    @Autowired
    ApplicationContext context ;
    UserDao userDao;
    @BeforeEach
    void setUp(){
        System.out.println("before");
        this.userDao = context.getBean("awsUserDao", UserDao.class);
    }
    @Test
    void addAndGet() throws SQLException {
        User user1 = new User("9", "홍홍홍", "1234");
        User user2 = new User("2", "힝힝힝", "1234");
        User user3 = new User("3", "헹헹헹", "1234");
        userDao.deleteAll();
        userDao.add(user1);
        Assertions.assertEquals(1,userDao.getNum());
        User user = userDao.get(user1.getId());
        Assertions.assertEquals(user1.getName(),user.getName());

        userDao.add(user2);
        userDao.add(user3);

        Assertions.assertEquals(3,userDao.getAll().size());


    }
    @Test
    void addNull(){
        assertThrows(NullPointerException.class,()->{
            User user = null;
            userDao.add(user);
        });
    }

}