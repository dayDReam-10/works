package com.jdbc.Entity;
import java.util.Date;

public class Score {
    private int id;
    private int student_id;
    private String subject;
    private double score;
    private Date exam_time=new Date();
    public Score() {
    }
    public Score(int student_id, String subject, double score) {
        this.student_id = student_id;
        this.subject = subject;
        this.score = score;
    }
    public Score(int id, int student_id, String subject, double score) {
        this.id = id;
        this.student_id = student_id;
        this.subject = subject;
        this.score = score;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getStudent_id() {
        return student_id;
    }
    public String getSubject() {
        return subject;
    }
    public double getScore() {
        return score;
    }
    public Date getExam_time() {
        return exam_time;
    }//这一条是为了防止时间的覆盖，因为我们查询的时候返回的rs数组里面的score对象是new出来的，所以我们需要覆盖掉默认的时间
    public void setExam_time(Date exam_time) {
        this.exam_time = exam_time;
    }
    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public void setScore(double score) {
        this.score = score;
    }
}