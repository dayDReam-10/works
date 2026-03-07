package com.jdbc.Entity;

import java.util.Date;

public class Students {
    private int id;
    private String name;
    private int age;
    private char sex;
    private String student_no;
    private Date create_time = new Date();

    public Students() {
    };

    public Students(String name, int age, char sex, String student_no) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.student_no = student_no;
    }
    //不是add的时候id我们需要获取，故再开一个构造法
    public Students(int id, String name, int age, char sex, String student_no) {
    this.id = id; 
    this.name = name;
    this.age = age;
    this.sex = sex;
    this.student_no = student_no;
}
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
    public char getSex() {
        return sex;
    }
    public String getStudent_no() {
        return student_no;
    }
    public Date getCreate_time() {
        return create_time;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void setSex(char sex) {
        this.sex = sex;
    }
    public void setStudent_no(String student_no) {
        this.student_no = student_no;
    }
    
}