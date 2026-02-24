package model;

import java.util.Map;

public class Graduate extends Student{

    private static final double Per_credit_rate = 60000;
    private static final double Research_fee = 50000;

    public Graduate(String name, String email, String studentID, double GPA, String department) {

        super(name, email, studentID, GPA, department);
    }

    public double calculateTuition() {

        int totalCredits = 0;
        for (Course course : getCourseGrade().keySet()) {
            totalCredits += course.getCredits();
        }
        return (totalCredits * Per_credit_rate) +Research_fee;
    }

    @Override
    public String getRole() {
        return "Graduate";
    }
}
