package manager;

import exceptions.CourseIsFull;
import exceptions.StudentEnrolled;
import model.Course;
import model.Student;

import java.util.ArrayList;
import java.util.List;

public class UniversityManager {

    private final List<Student> students;
    private final List<Course> courses;

    public UniversityManager() {
        this.students = new ArrayList<>();
        this.courses = new ArrayList<>();
    }

    public String registerStudent(Student student) {

        boolean alreadyExists = students.stream()
                .anyMatch(s -> s.getStudentID().equalsIgnoreCase(student.getStudentID()));

        if(alreadyExists) {
            return "Student already exist";
        }
        students.add(student);
        return  student + " has been enrolled";
    }

    public String createCourse(Course course) {

        boolean alreadyExists = courses.stream()
                .anyMatch(c -> c.getCourseID().equalsIgnoreCase(course.getCourseID()));
        if(alreadyExists) {
            return "course already exists";
        }

            courses.add(course);
            return "course added";
    }

    public void enrollStudentInCourse(String studentID, String courseID)
            throws CourseIsFull, StudentEnrolled {

        Student targetStudent = findStudentByID(studentID);
        if (targetStudent == null) {
            throw new IllegalArgumentException("No student found with ID: " + studentID);
        }

        Course targetCourse = findCourseByID(courseID);
        if (targetCourse == null) {
            throw new IllegalArgumentException("No course found with ID: " + courseID);
        }

        if (targetCourse.isFull()) {
            throw new CourseIsFull(
                    "Course [" + courseID + "] is at full capacity ("
                            + targetCourse.getMaxCapacity() + " students)."
            );
        }

        if (targetStudent.getCourseGrade().containsKey(targetCourse)) {
            throw new StudentEnrolled(
                    "Student [" + studentID + "] is already enrolled in course [" + courseID + "]."
            );
        }

        targetCourse.addStudent(targetStudent);
        targetStudent.addCourseGrade(targetCourse, 0.0);
    }

    public double getAverageGPAByDepartment(String department) {
        double total = 0.0;
        int count = 0;
        for (Student s : students) {
            if (s.getDepartment().equalsIgnoreCase(department)) {
                total += s.getGPA();
                count++;
            }
        }
        return count == 0 ? 0.0 : total / count;
    }

    public Student getTopPerformingStudent() {
        Student top = null;
        for (Student s : students) {
            if (top == null || s.getGPA() > top.getGPA()) {
                top = s;
            }
        }
        return top;
    }

    public List<Student> getDeansList() {
        List<Student> deansList = new ArrayList<>();
        for (Student s : students) {
            if (s.getGPA() > 3.5) {
                deansList.add(s);
            }
        }
        return deansList;
    }

    public Student findStudent(String studentID) {
        for (Student s : students) {
            if (s.getStudentID().equalsIgnoreCase(studentID)) {
                return s;
            }
        }
        return null;
    }

    public List<Student> getStudents() { return students; }
    public List<Course>  getCourses()  { return courses;  }

    private Student findStudentByID(String studentID) {
        for (Student s : students) {
            if (s.getStudentID().equalsIgnoreCase(studentID)) {
                return s;
            }
        }
        return null;
    }

    private Course findCourseByID(String courseID) {
        for (Course c : courses) {
            if (c.getCourseID().equalsIgnoreCase(courseID)) {
                return c;
            }
        }
        return null;
    }
}