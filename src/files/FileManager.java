package files;

import manager.UniversityManager;
import model.Course;
import model.Graduate;
import model.Student;
import model.Undergraduate;

import java.io.*;
import java.util.List;

public class FileManager {

    private static final String STUDENTS_FILE = "students.csv";
    private static final String COURSES_FILE  = "courses.csv";

    public void saveStudents(List<Student> students) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(STUDENTS_FILE))) {
            for (Student student : students) {
                writer.write(
                        student.getClass().getSimpleName() + "," +
                                student.getName()       + "," +
                                student.getEmail()      + "," +
                                student.getStudentID()  + "," +
                                student.getGPA()        + "," +
                                student.getDepartment()
                );
                writer.newLine();
            }
            System.out.println("Students saved successfully → " + STUDENTS_FILE);
        } catch (IOException e) {
            System.out.println("ERROR saving students: " + e.getMessage());
        }
    }

    public void saveCourses(List<Course> courses) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(COURSES_FILE))) {
            for (Course course : courses) {
                writer.write(
                        course.getCourseID()    + "," +
                                course.getCourseName()  + "," +
                                course.getCredits()     + "," +
                                course.getMaxCapacity()
                );
                writer.newLine();
            }
            System.out.println("Courses saved successfully → " + COURSES_FILE);
        } catch (IOException e) {
            System.out.println("ERROR saving courses: " + e.getMessage());
        }
    }

    public void loadStudents(UniversityManager manager) {
        File file = new File(STUDENTS_FILE);

        if (!file.exists()) {
            System.out.println("No student save file found. Starting with empty records.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(STUDENTS_FILE))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split(",");

                if (parts.length < 6) {
                    System.out.println("Skipping malformed line: " + line);
                    continue;
                }

                String type       = parts[0].trim();
                String name       = parts[1].trim();
                String email      = parts[2].trim();
                String studentID  = parts[3].trim();
                double gpa        = Double.parseDouble(parts[4].trim());
                String department = parts[5].trim();

                Student student;
                if (type.equalsIgnoreCase("Graduate")) {
                    student = new Graduate(name, email, studentID, gpa, department);
                } else {
                    student = new Undergraduate(name, email, studentID, gpa, department);
                }

                manager.registerStudent(student);
            }

            System.out.println("Students loaded from " + STUDENTS_FILE);

        } catch (IOException e) {
            System.out.println("ERROR loading students: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Corrupt data in student file: " + e.getMessage());
        }
    }

    public void loadCourses(UniversityManager manager) {
        File file = new File(COURSES_FILE);

        if (!file.exists()) {
            System.out.println("No course save file found. Starting with empty records.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(COURSES_FILE))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split(",");

                if (parts.length < 4) {
                    System.out.println("Skipping malformed line: " + line);
                    continue;
                }

                String courseID    = parts[0].trim();
                String courseName  = parts[1].trim();
                int    credits     = Integer.parseInt(parts[2].trim());
                int    maxCapacity = Integer.parseInt(parts[3].trim());

                manager.createCourse(new Course(courseID, courseName, credits, maxCapacity));
            }

            System.out.println("Courses loaded from " + COURSES_FILE);

        } catch (IOException e) {
            System.out.println("ERROR loading courses: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Corrupt data in course file: " + e.getMessage());
        }
    }
}