package com.db.dao;

import com.db.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {

    @Test
    void addAndGet() throws SQLException {
        User user1 = new User("1", "홍홍홍", "1234");
        UserDao userDao = new DaoFactory().awsUserDao();
        userDao.add(user1);
        User user = userDao.get(user1.getId());
        Assertions.assertEquals(user1.getName(),user.getName());
    }

}