package model;

public class Undergraduate  extends Student{

    private static final double flat_rate = 100000;

    public Undergraduate(String name, String email, String studentID, double GPA) {
        super(name, email, studentID, GPA);
    }

    @Override
    public String getRole() {
        return "Undergraduate";
    }
}
