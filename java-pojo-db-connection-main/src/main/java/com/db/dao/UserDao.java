package com.db.dao;

import java.sql.*;
import java.util.Map;

import com.db.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.sql.DataSource;

public class UserDao {

    private DataSource dataSource; // ConnectionMaker가 필요없어짐 대신 DaoFactory에서 DataSource 주입시켜야함
    private JdbcContext jdbcContext;
    UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcContext = new JdbcContext(dataSource);
    }



    public void add(User user) throws SQLException {
        // 기능마다 Strategy 만드는것은 너무 번거로움 익명 클래스 사용!
//        jdbcContextWithStatementStrategy(new InsertStrategy(user));
        jdbcContext.jdbcContextWithStatementStrategy(new StatementStrategy() {
                                             @Override
                                             public PreparedStatement makePreparedStatement(Connection connection) throws SQLException {
                                                 PreparedStatement ps = connection.prepareStatement("INSERT INTO users(id, name,password) VALUES (?,?,?)");
                                                 ps.setString(1, user.getId());
                                                 ps.setString(2, user.getName());
                                                 ps.setString(3, user.getPassword());
                                                 return ps;
                                             }
                                         }

        );
    }

    public int deleteAll() throws SQLException {
        // 인자가 필요 없을경우 고정되는 부분이 동일 하기 때문에 executeSql정의해서 불러오기
        return jdbcContext.executeSql("DELETE FROM users");
    }


    public int getNum() throws SQLException {
        PreparedStatement ps = null;
        Connection c = dataSource.getConnection();
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
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public User get(String id) throws SQLException {
        PreparedStatement ps = null;
        Connection c = dataSource.getConnection();
        try {
            // Query문 작성
            ps = c.prepareStatement("SELECT * FROM users WHERE id = ?");
            ps.setString(1, id);

            // Query문 실행
            ResultSet rs = ps.executeQuery();
            rs.next();
            User user = new User(rs.getString("id"), rs.getString("name"), rs.getString("password"));
            return user;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                }
            }
        }

    }
}
