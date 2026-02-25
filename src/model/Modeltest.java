package model;

/**
 * Manual test runner for the model package.
 * Run this class directly — no test framework required.
 *
 * Each test prints PASS or FAIL with a description.
 * A summary at the end shows total passed/failed.
 */
public class ModelTest {

    static int passed = 0;
    static int failed = 0;

    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println("        MODEL PACKAGE TEST SUITE        ");
        System.out.println("========================================\n");

        testPerson();
        testStudent();
        testCourse();
        testInstructor();
        testUndergraduate();
        testGraduate();
        testCourseCapacity();
        testGPACalculation();
        testGraduateTuitionWithCourses();

        System.out.println("\n========================================");
        System.out.println("  RESULTS: " + passed + " passed, " + failed + " failed");
        System.out.println("========================================");
    }

    // ----------------------------------------------------------------
    // PERSON (via Instructor since Person is abstract)
    // ----------------------------------------------------------------
    static void testPerson() {
        System.out.println("--- Person Tests ---");

        Instructor p = new Instructor("Alice", "alice@uni.edu", "E001", "CS");

        // getName / getEmail
        assertEqual("Person.getName()", "Alice", p.getName());
        assertEqual("Person.getEmail()", "alice@uni.edu", p.getEmail());

        // FIX CHECK: setName used to be SetName (capital S) — this must compile
        p.setName("Alice Updated");
        assertEqual("Person.setName() works", "Alice Updated", p.getName());

        p.setEmail("updated@uni.edu");
        assertEqual("Person.setEmail() works", "updated@uni.edu", p.getEmail());

        // FIX CHECK: toString() must contain " | Email: " not just "Email:"
        String str = p.toString();
        assertContains("Person.toString() has ' | Email: ' separator", str, " | Email: ");
    }

    // ----------------------------------------------------------------
    // STUDENT
    // ----------------------------------------------------------------
    static void testStudent() {
        System.out.println("\n--- Student Tests ---");

        // FIX CHECK: Constructor no longer takes a GPA parameter
        Student s = new Undergraduate("Bob", "bob@uni.edu", "S001", "Math");

        assertEqual("Student.getStudentID()", "S001", s.getStudentID());
        assertEqual("Student.getDepartment()", "Math", s.getDepartment());

        // GPA starts at 0
        assertDoubleEqual("Student GPA starts at 0.0", 0.0, s.getGPA());

        // FIX CHECK: toString() must have " | ID: " not just "ID:"
        String str = s.toString();
        assertContains("Student.toString() has ' | ID: ' separator", str, " | ID: ");
    }

    // ----------------------------------------------------------------
    // COURSE
    // ----------------------------------------------------------------
    static void testCourse() {
        System.out.println("\n--- Course Tests ---");

        // FIX CHECK: maxCapacity must use the passed value, not hardcoded 60
        Course c = new Course("CS101", "Intro to CS", 3, 2);

        assertEqual("Course.getCourseID()", "CS101", c.getCourseID());
        assertEqual("Course.getCourseName()", "Intro to CS", c.getCourseName());
        assertIntEqual("Course.getCredits()", 3, c.getCredits());

        // FIX CHECK: maxCapacity should be 2, not 60
        assertIntEqual("Course.getMaxCapacity() uses constructor param (not hardcoded 60)", 2, c.getMaxCapacity());

        // FIX CHECK: toString() must have proper separators
        String str = c.toString();
        assertContains("Course.toString() has ' | Name: '", str, "| Name:");
        assertContains("Course.toString() has ' | Credits: '", str, "| Credits:");

        // Course starts empty
        assertFalse("Course is not full when empty", c.isFull());
    }

    // ----------------------------------------------------------------
    // COURSE CAPACITY ENFORCEMENT
    // ----------------------------------------------------------------
    static void testCourseCapacity() {
        System.out.println("\n--- Course Capacity Tests ---");

        Course c = new Course("CS102", "Data Structures", 3, 2);
        Student s1 = new Undergraduate("Student1", "s1@uni.edu", "S101", "CS");
        Student s2 = new Undergraduate("Student2", "s2@uni.edu", "S102", "CS");
        Student s3 = new Undergraduate("Student3", "s3@uni.edu", "S103", "CS");

        // FIX CHECK: addStudent() now enforces isFull() internally
        boolean r1 = c.addStudent(s1);
        boolean r2 = c.addStudent(s2);
        boolean r3 = c.addStudent(s3); // should be rejected

        assertTrue("First student enrolled successfully", r1);
        assertTrue("Second student enrolled successfully", r2);
        assertFalse("Third student rejected — course is full", r3);
        assertTrue("Course is full after 2 students", c.isFull());
        assertIntEqual("Course has exactly 2 students", 2, c.getStudents().size());
    }

    // ----------------------------------------------------------------
    // INSTRUCTOR
    // ----------------------------------------------------------------
    static void testInstructor() {
        System.out.println("\n--- Instructor Tests ---");

        Instructor i = new Instructor("Dr. Smith", "smith@uni.edu", "E999", "Physics");

        assertEqual("Instructor.getRole()", "Instructor", i.getRole());
        assertEqual("Instructor.getEmployeeID()", "E999", i.getEmployeeID());
        assertEqual("Instructor.getDepartment()", "Physics", i.getDepartment());

        // FIX CHECK: toString() must now include employeeID and department
        String str = i.toString();
        assertContains("Instructor.toString() shows employeeID", str, "E999");
        assertContains("Instructor.toString() shows department", str, "Physics");
    }

    // ----------------------------------------------------------------
    // UNDERGRADUATE
    // ----------------------------------------------------------------
    static void testUndergraduate() {
        System.out.println("\n--- Undergraduate Tests ---");

        // FIX CHECK: Constructor no longer takes GPA parameter
        Undergraduate u = new Undergraduate("Carol", "carol@uni.edu", "U001", "Biology");

        assertEqual("Undergraduate.getRole()", "Undergraduate", u.getRole());
        assertDoubleEqual("Undergraduate flat tuition = 100000", 100000.0, u.calculateTuition());
    }

    // ----------------------------------------------------------------
    // GRADUATE
    // ----------------------------------------------------------------
    static void testGraduate() {
        System.out.println("\n--- Graduate Tests ---");

        // FIX CHECK: Constructor no longer takes GPA parameter
        Graduate g = new Graduate("Dave", "dave@uni.edu", "G001", "Engineering");

        assertEqual("Graduate.getRole()", "Graduate", g.getRole());

        // No courses enrolled — should only charge research fee (50000)
        assertDoubleEqual("Graduate with no courses pays only research fee", 50000.0, g.calculateTuition());
    }

    // ----------------------------------------------------------------
    // GRADUATE TUITION WITH COURSES
    // ----------------------------------------------------------------
    static void testGraduateTuitionWithCourses() {
        System.out.println("\n--- Graduate Tuition With Courses ---");

        Graduate g = new Graduate("Eve", "eve@uni.edu", "G002", "Math");
        Course c1 = new Course("MATH501", "Advanced Calculus", 3, 30);
        Course c2 = new Course("MATH502", "Linear Algebra", 4, 30);

        g.addCourseGrade(c1, 90.0);
        g.addCourseGrade(c2, 85.0);

        // Expected: (3 + 4) * 60000 + 50000 = 470000
        double expected = (3 + 4) * 60000.0 + 50000.0;
        assertDoubleEqual("Graduate tuition with 7 credits = 470000", expected, g.calculateTuition());
    }

    // ----------------------------------------------------------------
    // GPA CALCULATION
    // ----------------------------------------------------------------
    static void testGPACalculation() {
        System.out.println("\n--- GPA Calculation Tests ---");

        Student s = new Undergraduate("Frank", "frank@uni.edu", "S200", "CS");
        Course c1 = new Course("CS201", "Algorithms", 3, 30);
        Course c2 = new Course("CS202", "OS", 3, 30);

        s.addCourseGrade(c1, 80.0);
        assertDoubleEqual("GPA after 1 course = 80.0", 80.0, s.getGPA());

        s.addCourseGrade(c2, 60.0);
        assertDoubleEqual("GPA after 2 courses = 70.0 (average)", 70.0, s.getGPA());
    }

    // ----------------------------------------------------------------
    // ASSERTION HELPERS
    // ----------------------------------------------------------------
    static void assertEqual(String label, String expected, String actual) {
        if (expected.equals(actual)) {
            System.out.println("  PASS: " + label);
            passed++;
        } else {
            System.out.println("  FAIL: " + label);
            System.out.println("        Expected: \"" + expected + "\"");
            System.out.println("        Actual:   \"" + actual + "\"");
            failed++;
        }
    }

    static void assertIntEqual(String label, int expected, int actual) {
        if (expected == actual) {
            System.out.println("  PASS: " + label);
            passed++;
        } else {
            System.out.println("  FAIL: " + label);
            System.out.println("        Expected: " + expected);
            System.out.println("        Actual:   " + actual);
            failed++;
        }
    }

    static void assertDoubleEqual(String label, double expected, double actual) {
        if (Math.abs(expected - actual) < 0.001) {
            System.out.println("  PASS: " + label);
            passed++;
        } else {
            System.out.println("  FAIL: " + label);
            System.out.println("        Expected: " + expected);
            System.out.println("        Actual:   " + actual);
            failed++;
        }
    }

    static void assertTrue(String label, boolean condition) {
        if (condition) {
            System.out.println("  PASS: " + label);
            passed++;
        } else {
            System.out.println("  FAIL: " + label + " (expected true, got false)");
            failed++;
        }
    }

    static void assertFalse(String label, boolean condition) {
        if (!condition) {
            System.out.println("  PASS: " + label);
            passed++;
        } else {
            System.out.println("  FAIL: " + label + " (expected false, got true)");
            failed++;
        }
    }

    static void assertContains(String label, String str, String substring) {
        if (str.contains(substring)) {
            System.out.println("  PASS: " + label);
            passed++;
        } else {
            System.out.println("  FAIL: " + label);
            System.out.println("        String:    \"" + str + "\"");
            System.out.println("        Must contain: \"" + substring + "\"");
            failed++;
        }
    }
}