package com.gmail.at.ivanehreshi.jee.lab1.persistence;


import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PersistenceUtils {
    private DataSource ds;
    private Connection connection;

    public PersistenceUtils(String url, String db, String user, String password) {
        MysqlDataSource mysqlDs = new MysqlDataSource();
        mysqlDs.setUrl(url + db);
        mysqlDs.setUser(user);
        mysqlDs.setPassword(password);
        this.ds = mysqlDs;
    }

    public PersistenceUtils(DataSource ds) {
        this.ds = ds;
    }

    public Connection getConnection() {
        if(connection != null)
            return connection;

        try {
            this.connection = ds.getConnection();
            return connection;
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


    public boolean executeResourceFile(String path) {
        URL url = PersistenceUtils.class.getClassLoader().getResource(path);

        File file = null;
        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        try {
            if (file.exists()) {
                String content = new String(Files.readAllBytes(file.toPath()));
                List<String> queries = Arrays.asList(content.split(";"))
                        .stream()
                        .map(s -> s.trim())
                        .filter(s -> !s.isEmpty())
                        .collect(Collectors.toList());

                Connection conn = getConnection();

                for(String query: queries) {
                    try (Statement stmt = conn.createStatement()) {
                        String tr = query.trim();
                        stmt.execute(query.trim());
                    }
                }

                return true;
            }


        } catch (IOException e) {
            return false;
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return false;
    }

    public void close() {
        try {
            this.connection.close();
            this.connection = null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
