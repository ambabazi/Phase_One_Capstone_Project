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
        System.out.println("║ UNIVERSITY MANAGEMENT SYSTEM ║");

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
                case "6" -> handleAverageGPAByDepartment();
                case "7" -> handleTopStudent();
                case "8" -> {
                    handleSaveAndExit();
                    return;
                }
                default -> System.out.println("Invalid choice. Please enter a number from 1 to 8.");
            }
        }
    }

    private void printMenu() {
        System.out.println("│               MAIN MENU             │");
        System.out.println("│  1. Register Student                │");
        System.out.println("│  2. Create Course                   │");
        System.out.println("│  3. Enroll Student in Course        │");
        System.out.println("│  4. View Student Record             │");
        System.out.println("│  5. Generate Dean's List (GPA>3.5)  │");
        System.out.println("│  6. Average GPA by Department       │");
        System.out.println("│  7. Top Performing Student          │");
        System.out.println("│  8. Save and Exit                   │");

        System.out.print("  Your choice: ");
    }

    private void handleRegisterStudent() {
        System.out.println(" REGISTER NEW STUDENT ");

        System.out.print("  Student type  (1 = Undergraduate  /  2 = Graduate): ");
        String type = scanner.nextLine().trim();

        System.out.print("  Full name     : ");
        String name = scanner.nextLine().trim();

        System.out.print("  Email         : ");
        String email = scanner.nextLine().trim();

        System.out.print("  Student ID    : ");
        String id = scanner.nextLine().trim();

        // ✅ Use the class-level scanner and parse as a String → double
        double gpa = 0;
        while (true) {
            System.out.print("  GPA           : ");
            try {
                gpa = Double.parseDouble(scanner.nextLine().trim());
                break;
            } catch (NumberFormatException e) {
                System.out.println("  Invalid GPA. Please enter a number (e.g. 3.75).");
            }
        }

        System.out.print("  Department    : ");
        String dept = scanner.nextLine().trim();  // ✅ No longer skipped

        Student student;
        if (type.equals("2")) {
            student = new Graduate(name, email, id, gpa, dept);
        } else {
            student = new Undergraduate(name, email, id, gpa, dept);
        }

        String result = manager.registerStudent(student);
        System.out.println("\n  " + result);
    }

    private void handleCreateCourse() {
        System.out.println("── CREATE NEW COURSE ─────────────────────");

        System.out.print("  Course ID       : ");
        String id = scanner.nextLine().trim();

        System.out.print("  Course name     : ");
        String name = scanner.nextLine().trim();

        int credits  = readInt("  Credits         : ");
        int capacity = readInt("  Max capacity    : ");

        String result = manager.createCourse(new Course(id, name, credits, capacity));
        System.out.println("\n  " + result);
    }

    private void handleEnrollStudent() {
        System.out.println("── ENROLL STUDENT IN COURSE ──────────────");

        System.out.print("  Student ID : ");
        String studentID = scanner.nextLine().trim();

        System.out.print("  Course ID  : ");
        String courseID = scanner.nextLine().trim();

        try {
            manager.enrollStudentInCourse(studentID, courseID);
            System.out.println("\n  Enrollment successful!");

        } catch (CourseIsFull e) {
            System.out.println("\n  ENROLLMENT FAILED — Course is full!");
            System.out.println("  Reason: " + e.getMessage());

        } catch (StudentEnrolled e) {
            System.out.println("\n  ENROLLMENT FAILED — Student already enrolled!");
            System.out.println("  Reason: " + e.getMessage());

        } catch (IllegalArgumentException e) {
            System.out.println("\n  ENROLLMENT FAILED — " + e.getMessage());
        }
    }

    private void handleViewStudentRecord() {
        System.out.println("── VIEW STUDENT RECORD ───────────────────");

        System.out.print("  Enter Student ID: ");
        String id = scanner.nextLine().trim();

        Student s = manager.findStudent(id);

        if (s == null) {
            System.out.println("\n  No student found with ID: " + id);
            return;
        }

        System.out.println("\n  ╔══════════════════════════════════════╗");
        System.out.println("  ║           STUDENT RECORD             ║");
        System.out.println("  ╠══════════════════════════════════════╣");
        System.out.printf("  ║  Name       : %-22s║%n", s.getName());
        System.out.printf("  ║  Student ID : %-22s║%n", s.getStudentID());
        System.out.printf("  ║  Type       : %-22s║%n", s.getRole());
        System.out.printf("  ║  Department : %-22s║%n", s.getDepartment());
        System.out.printf("  ║  Email      : %-22s║%n", s.getEmail());
        System.out.printf("  ║  GPA        : %-22.2f║%n", s.getGPA());
        System.out.printf("  ║  Tuition    : %-22s║%n", formatMoney(s.calculateTuition()));
        System.out.println("  ╠══════════════════════════════════════╣");
        System.out.println("  ║  ENROLLED COURSES                    ║");
        System.out.println("  ╠══════════════════════════════════════╣");

        if (s.getCourseGrade().isEmpty()) {
            System.out.println("  ║  (No courses enrolled yet)           ║");
        } else {
            for (Course course : s.getCourseGrade().keySet()) {
                double grade = s.getCourseGrade().get(course);
                String gradeStr = (grade == 0.0) ? "Not graded" : String.format("%.1f", grade);
                System.out.printf("  ║  %-20s Grade: %-5s ║%n",
                        course.getCourseName(), gradeStr);
            }
        }

        System.out.println("  ╚══════════════════════════════════════╝");
    }

    private void handleDeansList() {
        System.out.println("── DEAN'S LIST (GPA > 3.5) ───────────────");

        List<Student> deansList = manager.getDeansList();

        if (deansList.isEmpty()) {
            System.out.println("  No students currently qualify for the Dean's List.");
            return;
        }

        System.out.println("\n  Students on the Dean's List:");
        System.out.println("  ┌────────────────┬──────────────────────┬────────────┬───────┐");
        System.out.println("  │ Student ID     │ Name                 │ Department │ GPA   │");
        System.out.println("  ├────────────────┼──────────────────────┼────────────┼───────┤");

        for (Student s : deansList) {
            System.out.printf("  │ %-14s │ %-20s │ %-10s │ %-5.2f │%n",
                    s.getStudentID(),
                    s.getName(),
                    s.getDepartment(),
                    s.getGPA()
            );
        }
        System.out.println("  └────────────────┴──────────────────────┴────────────┴───────┘");
        System.out.println("  Total: " + deansList.size() + " student(s) on the Dean's List.");
    }

    private void handleAverageGPAByDepartment() {
        System.out.println("── AVERAGE GPA BY DEPARTMENT ─────────────");

        System.out.print("  Enter department name: ");
        String dept = scanner.nextLine().trim();

        double avg = manager.getAverageGPAByDepartment(dept);

        if (avg == 0.0) {
            System.out.println("\n  No students found in department: " + dept);
        } else {
            System.out.printf("%n  Average GPA for [%s]: %.2f%n", dept, avg);
        }
    }

    private void handleTopStudent() {
        System.out.println("── TOP PERFORMING STUDENT ────────────────");

        Student s = manager.getTopPerformingStudent();

        if (s == null) {
            System.out.println("  No students in the system yet.");
            return;
        }

        System.out.println("\n  Top Performing Student:");
        System.out.println("     Name       : " + s.getName());
        System.out.println("     Student ID : " + s.getStudentID());
        System.out.println("     Department : " + s.getDepartment());
        System.out.printf("     GPA        : %.2f%n", s.getGPA());
    }

    private void handleSaveAndExit() {
        System.out.println("── SAVING DATA ───────────────────────────");
        fileManager.saveStudents(manager.getStudents());
        fileManager.saveCourses(manager.getCourses());
        System.out.println("\n  All data saved. See you next time!");
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                if (value > 0) return value;
                System.out.println("  Please enter a positive number.");
            } catch (NumberFormatException e) {
                System.out.println("  Invalid input. Please enter a whole number.");
            }
        }
    }

    private String formatMoney(double amount) {
        return String.format("%,.0f RWF", amount);
    }
}