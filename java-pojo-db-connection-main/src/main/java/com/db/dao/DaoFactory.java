package com.db.dao;

import java.sql.SQLException;

public class DaoFactory {
    public UserDao awsUserDao() throws SQLException {
        return new UserDao(new AwsConnectionMaker().makeConnection());
    }

    public UserDao localUserDao() throws SQLException {
        return new UserDao(new LocalConnectionMaker().makeConnection());
    }
}
