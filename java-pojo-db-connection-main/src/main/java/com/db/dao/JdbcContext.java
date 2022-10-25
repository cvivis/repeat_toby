package com.db.dao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcContext {

    private DataSource dataSource;
    JdbcContext(DataSource dataSource){
        this.dataSource = dataSource;
    }
    public int jdbcContextWithStatementStrategy(StatementStrategy st) throws SQLException {
        PreparedStatement ps = null;
        Connection c = dataSource.getConnection();

        try {
            ps = st.makePreparedStatement(c);
            int result = ps.executeUpdate();
            return result;
        } catch (SQLException e) {
            throw e;
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                }
            }
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                }
            }
        }
    }
}
