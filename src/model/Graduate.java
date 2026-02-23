package model;

public class Graduate extends Student{

    private static final double Per_credit_rate = 60000;
    private static final double Research_fee = 50000;

    public Graduate(String name, String email, String studentID, double GPA) {
        super(name, email, studentID, GPA);
    }

//    public double calculateTuition() {
//        int totalCredits = 0;
//
//    }

    @Override
    public String getRole() {
        return "Graduate";
    }
}
