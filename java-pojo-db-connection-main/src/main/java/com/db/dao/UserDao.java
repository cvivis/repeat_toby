package com.db.dao;

import java.sql.*;
import java.util.List;
import java.util.Map;

import com.db.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;

public class UserDao {

    private DataSource dataSource; // ConnectionMaker가 필요없어짐 대신 DaoFactory에서 DataSource 주입시켜야함
//    private JdbcContext jdbcContext;
    private JdbcTemplate jdbcTemplate; // jdbcContext와 동일한 역할을 해주는 JdbcTemplate
    UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    RowMapper<User> rowMapper = (rs, rowNum) ->
        new User(rs.getString("id"),
                rs.getString("name"),
                rs.getString("password")
                );

    public void add(User user) throws SQLException {
        // 기능마다 Strategy 만드는것은 너무 번거로움 익명 클래스 사용!
//        jdbcContextWithStatementStrategy(new InsertStrategy(user));
//        jdbcContext.jdbcContextWithStatementStrategy(new StatementStrategy() { // StatementStrategy도 필요없음
//                                             @Override
//                                             public PreparedStatement makePreparedStatement(Connection connection) throws SQLException {
//                                                 PreparedStatement ps = connection.prepareStatement("INSERT INTO users(id, name,password) VALUES (?,?,?)");
//                                                 ps.setString(1, user.getId());
//                                                 ps.setString(2, user.getName());
//                                                 ps.setString(3, user.getPassword());
//                                                 return ps;
//                                             }
//                                         }
//
//        );
        //위와 동일한 역할
        this.jdbcTemplate.update("INSERT INTO users(id, name,password) VALUES (?,?,?)",user.getId(),user.getName(),user.getPassword());
    }

    public int deleteAll() throws SQLException {
        // 인자가 필요 없을경우 고정되는 부분이 동일 하기 때문에 executeSql정의해서 불러오기

        return this.jdbcTemplate.update("DELETE FROM users");
    }

    public int getNum() throws SQLException {
            return this.jdbcTemplate.queryForObject("select count(*) from users", Integer.class);

    }

    public User get(String id) throws SQLException {
        return this.jdbcTemplate.queryForObject("select * from users where id = ?",rowMapper,id);

    }
    public List<User> getAll() throws SQLException{
        return this.jdbcTemplate.query("select * from users",rowMapper);
    }
}
