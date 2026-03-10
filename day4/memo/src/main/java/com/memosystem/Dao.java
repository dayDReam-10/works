package com.memosystem;

import java.util.HashMap;
import java.util.Map;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
//prestatement防sql

public class Dao {

    // login
    public User login(String u, String p) throws Exception {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = Utils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, u);
            ps.setString(2, p);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setAvatar(rs.getString("avatar"));
                return user;
            }
        }
        return null;
    }

    // sign up
    public boolean register(String u, String p) throws Exception {
        String sql = "INSERT INTO users(username, password) VALUES(?, ?)";
        try (Connection conn = Utils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, u);
            ps.setString(2, p);
            return ps.executeUpdate() > 0;//偷学的一招

        }
    }

    // add
    public void addMemo(int userId, String title, String content) throws Exception {
        String sql = "INSERT INTO memos(user_id, title, content) VALUES(?, ?, ?)";
        try (Connection conn = Utils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, title);
            ps.setString(3, content);
            ps.executeUpdate();
        }
    }

    // del
    public void deleteMemo(int id) throws Exception {
        String sql = "DELETE FROM memos WHERE id = ?";
        try (Connection conn = Utils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
    // update nano
    public void updateMemo(int id, String title, String content) throws Exception {
        String sql = "UPDATE memos SET title = ?, content = ? WHERE id = ?";
        try (Connection conn = Utils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, title);
            ps.setString(2, content);
            ps.setInt(3, id);
            ps.executeUpdate();
        }
    }
    // IO image
    public void updateAvatar(int userId, String fileName) throws Exception {
        String sql = "UPDATE users SET avatar = ? WHERE id = ?";
        try (Connection conn = Utils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, fileName);
            ps.setInt(2, userId);
            ps.executeUpdate();
        }
    }
    public String get(int userId) throws Exception {
    StringBuilder sb = new StringBuilder();
    String sql = "SELECT title, content FROM memos WHERE user_id = ?";
    try (Connection conn = Utils.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            sb.append("标题: ").append(rs.getString("title"))
              .append(" | 内容: ").append(rs.getString("content"))
              .append("\n---\n"); // 用分隔符切开
        }
    }
    return sb.toString();
}
}