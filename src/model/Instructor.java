package model;

public class Instructor extends Person{

    private String employeeID;

    public Instructor(String name, String email, String employeeID) {
        super(name, email);
        this.employeeID = employeeID;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    @Override
    public String getRole() {
        return "Instructor";
    }
}
