package model;

public class Graduate extends Student{

    private static final double COST_PER_CREDIT = 60000;
    private static final double RESEARCH_FEE = 50000;

    public Graduate(String name, String email, String studentID, double GPA, String department) {

        super(name, email, studentID, GPA, department);
    }
    @Override
    public double calculateTuition() {

        int totalCredits = 0;
        for (Course course : getCourseGrade().keySet()) {
            totalCredits += course.getCredits();
        }
        return (totalCredits * COST_PER_CREDIT) + RESEARCH_FEE;
    }

    @Override
    public String getRole() {
        return "Graduate";
    }
}
