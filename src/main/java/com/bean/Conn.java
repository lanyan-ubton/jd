package com.bean;

import java.sql.*;

public class Conn {
    private static Conn instance;
    private Connection conn = null;

    // 获取数据库连接的单例实例
    public static synchronized Conn getInstance() {
        if (instance == null) {
            instance = new Conn();
        }
        return instance;
    }

    // 私有构造函数，加载驱动程序
    public Conn() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("数据库驱动加载失败: " + e.getMessage());
        }
    }

    // 获取数据库连接
    private void connect() throws SQLException {
        if (conn == null || conn.isClosed()) {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jd?useUnicode=true&characterEncoding=UTF-8", "root", "Lanyan040529");
        }
    }

    // 执行查询操作
    public ResultSet executeQuery(String sql) throws SQLException {
        connect();
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(sql);
    }

    // 执行更新操作
    public int executeUpdate(String sql) throws SQLException {
        connect();
        try (Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            return stmt.executeUpdate(sql);
        }
    }

    // 关闭数据库连接
    public void close() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            System.err.println("关闭数据库连接时出错: " + e.getMessage());
        }
    }

    // 获取数据库连接对象
    public Connection getConnection() throws SQLException {
        connect();
        return conn;
    }
}
