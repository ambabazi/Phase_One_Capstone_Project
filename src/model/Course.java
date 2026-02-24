package model;

import jdk.jfr.DataAmount;

import java.util.ArrayList;
import java.util.List;

public class Course {

    private String courseID;
    private String courseName;
    private int credits;
    private int maxCapacity;

    private List<Student> students;

    public Course(String courseID, String courseName, int credits, int maxCapacity) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.credits = credits;
        this.maxCapacity = 60;
        this.students =  new ArrayList<>();
    }

    public String getCourseID() {

        return courseID;
    }

    public String getCourseName() {

        return courseName;
    }

    public int getCredits() {

        return  credits;
    }

    public int getMaxCapacity() {

        return maxCapacity;
    }

    public List<Student> getStudents() {

        return students;
    }

    public void addStudent(Student student) {

        students.add(student);
    }

    public boolean isFull() {

        return students.size() >= maxCapacity;
    }

    @Override
    public String toString() {
        return "CourseID: " +courseID + "Name of the Course:  " + courseName + "credits " + credits;
    }

}
