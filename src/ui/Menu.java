package ui;

import exceptions.CourseIsFull;
import exceptions.StudentEnrolled;
import files.FileManager;
import manager.UniversityManager;
import model.Course;
import model.Graduate;
import model.Student;
import model.Undergraduate;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Menu {

    private final UniversityManager manager;
    private final FileManager       fileManager;
    private final Scanner           scanner;

    public Menu(UniversityManager manager, FileManager fileManager) {
        this.manager     = manager;
        this.fileManager = fileManager;
        this.scanner     = new Scanner(System.in);
    }

    public void start() {
        System.out.println("UNIVERSITY MANAGEMENT SYSTEM");

        while (true) {
            printMenu();
            String choice = scanner.nextLine().trim();
            System.out.println();

            switch (choice) {
                case "1" -> handleRegisterStudent();
                case "2" -> handleCreateCourse();
                case "3" -> handleEnrollStudent();
                case "4" -> handleViewStudentRecord();
                case "5" -> handleDeansList();
                case "6" -> {handleSaveAndExit();
                    return;
                }
                default -> System.out.println("Invalid choice. Please enter a number from 1 to 7.");
            }
        }
    }

    private void printMenu() {
        System.out.println("MAIN MENU");
        System.out.println("1. Register Student");
        System.out.println("2. Create Course");
        System.out.println("3. Enroll Student in Course");
        System.out.println("4. View Student Record");
        System.out.println("5. Generate Dean's List (GPA > 3.5)");
        System.out.println("6. Save and Exit");
        System.out.print("Your choice: ");
    }

    private void handleRegisterStudent() {
        System.out.println("REGISTER NEW STUDENT");

        System.out.print("Student type (1 = Undergraduate / 2 = Graduate): ");
        String type = scanner.nextLine().trim();

        System.out.print("Full name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Student ID: ");
        String id = scanner.nextLine().trim();

        double gpa = 0;
        while (true) {
            System.out.print("GPA: ");
            try {
                gpa = Double.parseDouble(scanner.nextLine().trim());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid GPA. Please enter a number (e.g. 3.75).");
            }
        }

        System.out.print("Department: ");
        String dept = scanner.nextLine().trim();

        Student student;
        if (type.equals("2")) {
            student = new Graduate(name, email, id, gpa, dept);
        } else {
            student = new Undergraduate(name, email, id, gpa, dept);
        }

        String result = manager.registerStudent(student);
        System.out.println("\n" + result);
    }

    private void handleCreateCourse() {
        System.out.println("CREATE NEW COURSE");

        System.out.print("Course ID: ");
        String id = scanner.nextLine().trim();

        System.out.print("Course name: ");
        String name = scanner.nextLine().trim();

        int credits  = readInt("Credits: ");
        int capacity = readInt("Max capacity: ");

        String result = manager.createCourse(new Course(id, name, credits, capacity));
        System.out.println("\n" + result);
    }

    private void handleEnrollStudent() {
        System.out.println("ENROLL STUDENT IN COURSE");

        System.out.print("Student ID: ");
        String studentID = scanner.nextLine().trim();

        System.out.print("Course ID: ");
        String courseID = scanner.nextLine().trim();

        try {
            manager.enrollStudentInCourse(studentID, courseID);
            System.out.println("\nEnrollment successful!");

        } catch (CourseIsFull e) {
            System.out.println("\nENROLLMENT FAILED - Course is full!");
            System.out.println("Reason: " + e.getMessage());

        } catch (StudentEnrolled e) {
            System.out.println("\nENROLLMENT FAILED - Student already enrolled!");
            System.out.println("Reason: " + e.getMessage());

        } catch (IllegalArgumentException e) {
            System.out.println("\nENROLLMENT FAILED - " + e.getMessage());
        }
    }

    private void handleViewStudentRecord() {
        System.out.println("VIEW STUDENT RECORD");

        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine().trim();

        Student s = manager.findStudent(id);

        if (s == null) {
            System.out.println("\nNo student found with ID: " + id);
            return;
        }

        System.out.println("\n  +--------------------------------------+");
        System.out.println("  |           STUDENT RECORD             |");
        System.out.println("  +--------------------------------------+");
        System.out.println("  |  Name       : " + padRight(s.getName(), 22) + "|");
        System.out.println("  |  Student ID : " + padRight(s.getStudentID(), 22) + "|");
        System.out.println("  |  Type       : " + padRight(s.getRole(), 22) + "|");
        System.out.println("  |  Department : " + padRight(s.getDepartment(), 22) + "|");
        System.out.println("  |  Email      : " + padRight(s.getEmail(), 22) + "|");
        System.out.println("  |  GPA        : " + padRight(String.format("%.2f", s.getGPA()), 22) + "|");
        System.out.println("  |  Tuition    : " + padRight(formatMoney(s.calculateTuition()), 22) + "|");
        System.out.println("  +--------------------------------------+");
        System.out.println("  |  ENROLLED COURSES                    |");
        System.out.println("  +--------------------------------------+");

        if (s.getCourseGrade().isEmpty()) {
            System.out.println("  |  (No courses enrolled yet)           |");
        } else {
            for (Map.Entry<Course, Double> entry : s.getCourseGrade().entrySet()) {
                String courseName = entry.getKey().getCourseName();
                double grade      = entry.getValue();
                String gradeStr   = (grade == 0.0) ? "Not graded" : String.format("%.1f", grade);
                System.out.printf("  |  %-20s Grade: %-5s |%n", courseName, gradeStr);
            }
        }

        System.out.println("  +--------------------------------------+");
    }

    private void handleDeansList() {
        System.out.println("DEAN'S LIST (GPA > 3.5)");

        List<Student> deansList = manager.getDeansList();

        if (deansList.isEmpty()) {
            System.out.println("No students currently qualify for the Dean's List.");
            return;
        }

        System.out.println("\nStudents on the Dean's List:");
        System.out.println("+----------------+----------------------+------------+-------+");
        System.out.println("| Student ID     | Name                 | Department | GPA   |");
        System.out.println("+----------------+----------------------+------------+-------+");

        for (Student s : deansList) {
            System.out.println("| " + padRight(s.getStudentID(), 14) + " | " +
                    padRight(s.getName(), 20) + " | " +
                    padRight(s.getDepartment(), 10) + " | " +
                    padRight(String.format("%.2f", s.getGPA()), 5) + " |");
        }

        System.out.println("+----------------+----------------------+------------+-------+");
        System.out.println("Total: " + deansList.size() + " student(s) on the Dean's List.");
    }

    private void handleSaveAndExit() {
        System.out.println("SAVING DATA");
        fileManager.saveStudents(manager.getStudents());
        fileManager.saveCourses(manager.getCourses());
        System.out.println("All data saved. See you next time!");
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                if (value > 0) return value;
                System.out.println("Please enter a positive number.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
    }

    private String padRight(String text, int width) {
        if (text.length() >= width) return text.substring(0, width);
        return text + " ".repeat(width - text.length());
    }

    private String formatMoney(double amount) {
        return String.format("%,.0f RWF", amount);
    }
}