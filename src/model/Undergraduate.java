package model;

public class Undergraduate  extends Student{

    private static final double flat_rate = 100000;

    public Undergraduate(String name, String email, String studentID, double GPA, String department) {
        super(name, email, studentID, GPA, department);
    }

    public double calculateTuition() {

        return flat_rate;
    }

    @Override
    public String getRole() {

        return "Undergraduate";
    }
}
