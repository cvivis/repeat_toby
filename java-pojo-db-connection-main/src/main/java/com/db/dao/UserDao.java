package com.db.dao;

import java.sql.*;
import java.util.Map;

import com.db.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;

public class UserDao {
    private Connection c;

    UserDao(Connection connection) {
        this.c = connection;
    }

    public void add(User user) throws SQLException {
        PreparedStatement ps = null;
        try {
            // Query문 작성
            ps = c.prepareStatement("INSERT INTO users(id, name, password) VALUES(?,?,?);");
            ps.setString(1, user.getId());
            ps.setString(2, user.getName());
            ps.setString(3, user.getPassword());

            // Query문 실행
            ps.executeUpdate();
//
//            ps.close();
//            c.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
//        }finally {
//            if (ps != null) {
//                try {
//                    ps.close();
//                } catch (SQLException e) {
//                }
//            }
//            if (c != null) {
//                try {
//                    c.close();
//                } catch (SQLException e) {
//                }
//            }
        }
    }

    public int deleteAll() throws SQLException {
        PreparedStatement ps = null;
//        try {
        // Query문 작성
        ps = c.prepareStatement("DELETE FROM users");
        // Query문 실행
        int result = ps.executeUpdate();
        return result;

    }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }finally {
//            if (ps != null) {
//                try {
//                    ps.close();
//                } catch (SQLException e) {
//                }
//            }
//            if (c != null) {
//                try {
//                    c.close();
//                } catch (SQLException e) {
//                }
//            }
//        }

    public int getNum() throws SQLException {
        PreparedStatement ps = null;
        try {
            ps = c.prepareStatement("select count(*) from users");
            ResultSet result = ps.executeQuery();
            int count = 0;
            if (result.next()) {
                count = Integer.parseInt(result.getString("count(*)"));
            }
            return count;
        } catch (SQLException e) {
            throw new RuntimeException(e);
//        } finally {
//            if (ps != null) {
//                try {
//                    ps.close();
//                } catch (SQLException e) {
//                }
//            }
//            if (c != null) {
//                try {
//                    c.close();
//                } catch (SQLException e) {
//                }
//            }
        }
    }

    public User get(String id) throws SQLException {
        PreparedStatement ps = null;
        try {
            // Query문 작성
            ps = c.prepareStatement("SELECT * FROM users WHERE id = ?");
            ps.setString(1, id);

            // Query문 실행
            ResultSet rs = ps.executeQuery();
            rs.next();
            User user = new User(rs.getString("id"), rs.getString("name"),
                    rs.getString("password"));

//            rs.close();
//            pstmt.close();

            return user;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
//        finally {
//            if (ps != null) {
//                try {
//                    ps.close();
//                } catch (SQLException e) {
//                }
//            }
//            if (c != null) {
//                try {
//                    c.close();
//                } catch (SQLException e) {
//                }
//            }
    }
}
