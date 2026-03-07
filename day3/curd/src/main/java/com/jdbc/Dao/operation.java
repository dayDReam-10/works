package com.jdbc.Dao;

import com.jdbc.Utils.utils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import com.jdbc.Entity.Students;

public class operation {
    // c普通
    public void addStudent(Students student) throws Exception {
        Connection conn = utils.getConnection();
        // addstudent
        String sql = "INSERT INTO student(name, gender, age, student_no, create_time) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, student.getName());
        stmt.setString(2, String.valueOf(student.getSex()));
        stmt.setInt(3, student.getAge());
        stmt.setString(4, student.getStudent_no());
        stmt.setObject(5, student.getCreate_time());
        stmt.executeUpdate();
        utils.close(conn, stmt, null);
    }

    // c的扩展任务
    public void batchAddStudents(List<Students> students) throws Exception {
        Connection conn = utils.getConnection();
        String sql = "INSERT INTO student(name, gender, age, student_no, create_time) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        for (Students s : students) {
            stmt.setString(1, s.getName());
            stmt.setString(2, String.valueOf(s.getSex()));
            stmt.setInt(3, s.getAge());
            stmt.setString(4, s.getStudent_no());
            stmt.setObject(5, s.getCreate_time());
            stmt.addBatch();
        }
        stmt.executeBatch();
        utils.close(conn, stmt, null);
    }

    // c score
    public void addScore(Score score) throws Exception {//为指定id加score里面的内容
        Connection conn = utils.getConnection();
        String sql = "INSERT INTO score(student_id, subject, score, exam_time) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, score.getStudent_id());
        stmt.setString(2, score.getSubject());
        stmt.setDouble(3, score.getScore());
        stmt.setObject(4, score.getExam_time());
        stmt.executeUpdate();
        utils.close(conn, stmt, null);
    }

    // u
    public void updateAgeAndName(Students student) throws Exception {
        Connection conn = utils.getConnection();
        String sql = "UPDATE student SET name = ?, age = ? WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, student.getName());
        stmt.setInt(2, student.getAge());
        stmt.setInt(3, student.getId());
        stmt.executeUpdate();
        utils.close(conn, stmt, null);

    }

    // r All info
    public List<Score> queryAll(int id) throws Exception {
        Connection conn = utils.getConnection();
        String sql = "SELECT * FROM student,score where student_id=id and student_id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        List<Score> scores = new ArrayList<>();
        while (rs.next()) {
            Score score = new Score();
            score.setStudent_id(rs.getInt("student_id"));
            score.setSubject(rs.getString("subject"));
            score.setScore(rs.getDouble("score"));
            score.setExam_time(rs.getDate("exam_time"));
            scores.add(score);
        }
        utils.close(conn, stmt, rs);
        return scores;
    }
    //r 用学号查询
    public List<Score> query_stuNo(int sutdent_no) throws Exception{
        Connection conn =utils.getConnection();
        String sql ="SELECT * FROM score where student_no=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, sutdent_no);
        ResultSet rs = stmt.executeQuery();
        List<Score> scores = new ArrayList<>();
        while (rs.next()) {
            Score score = new Score();
            score.setStudent_id(rs.getInt("student_id"));
            score.setSubject(rs.getString("subject"));
            score.setScore(rs.getDouble("score"));
            score.setExam_time(rs.getDate("exam_time"));
            scores.add(score);
        }
        utils.close(conn, stmt, rs);
        return scores;
    }
    //r 模糊查询,其实就是拼个字符串
    public List<Score> query_like(String name) throws Exception{
        Connection conn =utils.getConnection();
        String sql ="SELECT * FROM student,score WHERE student.id = score.student_id AND student.name LIKE ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, "%"+name+"%");
        ResultSet rs = stmt.executeQuery();
        List<Score> scores = new ArrayList<>();
        while (rs.next()) {
            Score score = new Score();
            score.setStudent_id(rs.getInt("student_id"));
            score.setSubject(rs.getString("subject"));
            score.setScore(rs.getDouble("score"));
            score.setExam_time(rs.getDate("exam_time"));
            scores.add(score);
        }
        utils.close(conn, stmt, rs);
        return scores;

    }

    // d
    public void deleteScore(int student_id) throws Exception {
        Connection conn = utils.getConnection();
        String sql = "DELETE FROM score WHERE student_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, student_id);
        stmt.executeUpdate();
        utils.close(conn, stmt, null);
    }

    // d
    public void deleteStudent(int id) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = utils.getConnection();
            conn.setAutoCommit(false);
            String sql1 = "DELETE FROM score WHERE student_id = ?";
            stmt = conn.prepareStatement(sql1);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            stmt.close();
            String sql2 = "DELETE FROM student WHERE id = ?";
            stmt = conn.prepareStatement(sql2);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            conn.commit();
        } catch (Exception e) {
            if (conn != null)
                conn.rollback();
            throw e;
        } finally {
            utils.close(conn, stmt, null);
        }
    }
}