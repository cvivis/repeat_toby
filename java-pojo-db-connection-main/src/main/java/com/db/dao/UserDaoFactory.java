package com.db.dao;

public class UserDaoFactory {

    public UserDao05WithInterface userDao() {
        MySqlConnectionMaker mySqlConnectionMaker = new MySqlConnectionMaker();
        return new UserDao05WithInterface(mySqlConnectionMaker);
    }
}
