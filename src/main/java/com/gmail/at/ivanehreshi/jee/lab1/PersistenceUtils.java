package com.gmail.at.ivanehreshi.jee.lab1;


import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.*;

public class PersistenceUtils {
    private DataSource ds;

    public PersistenceUtils(String url, String db, String user, String password) {
        MysqlDataSource mysqlDs = new MysqlDataSource();
        mysqlDs.setUrl(url + db);;
        mysqlDs.setUser(user);
        mysqlDs.setPassword(password);
        this.ds = mysqlDs;
    }

    public Connection getConnection() {
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public  ResultSet query(String query, Object... params) {
        Connection conn = getConnection();
        if(conn == null)
            return null;

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }

            ResultSet rs = stmt.executeQuery();
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public int update(String updQuery, Object... params) {
        Connection conn = getConnection();

        try {
            PreparedStatement stmt = conn.prepareStatement(updQuery);
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }

            return stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public Long insert(String updQuery, Object... params) {
        Connection conn = getConnection();

        try {
            PreparedStatement stmt = conn.prepareStatement(updQuery, Statement.RETURN_GENERATED_KEYS);

            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }

            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();

            if(rs.next()) {
                return rs.getLong(1);
            }

            return null;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
