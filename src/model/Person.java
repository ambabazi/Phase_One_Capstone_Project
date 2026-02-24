package model;

public abstract class Person {

    private String name;
    private String email;

    public Person(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {

        return email;
    }

    public void SetName(String name) {

        this.name = name;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public abstract String getRole();

    @Override
    public String toString() {
        return getRole() + ": " + name + "Email: " + email ;
    }
}
