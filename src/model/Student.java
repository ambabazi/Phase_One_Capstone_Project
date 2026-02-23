package model;

import java.util.HashMap;
import java.util.Map;

public class Student extends Person{

    private String studentID;
    private double GPA;

//    private Map<Course, Double> courses;

    public Student(String name, String email, String studentID, double GPA) {
        super(name, email);
        this.studentID = studentID;
        this.GPA = 0;
//        this.courses = new HashMap<>();
    }

    public String getStudentID() {
        return studentID;
    }

    public double getGPA() {
        return GPA;
    }

    public void setGPA(double GPA) {
        this.GPA = GPA;
    }

//    public void addCourseGrade(Course course, double grade) {
//        courseGrades.put(course, grade);
//    }

    @Override
    public String getRole() {
        return "Student";
    }

    @Override
    public String toString() {
        return super.toString() + "ID: " + studentID;
    }
}
