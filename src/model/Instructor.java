package model;

public class Instructor extends Person{

    private String employeeID;
    private String department;

    public Instructor(String name, String email, String employeeID, String department) {
        super(name, email);
        this.employeeID = employeeID;
        this.department = department;
    }

    public String getEmployeeID() {

        return employeeID;
    }

    public String getDepartment() {

        return department;
    }

    @Override
    public String getRole() {
        return "Instructor";

}
}
