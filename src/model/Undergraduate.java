package model;

public class Undergraduate  extends Student{

    private static final double FLAT_RATE = 100000;

    public Undergraduate(String name, String email, String studentID, double GPA, String department) {
        super(name, email, studentID, GPA, department);
    }

    @Override
    public double calculateTuition() {

        return FLAT_RATE;
    }

    @Override
    public String getRole() {

        return "Undergraduate";
    }
}
