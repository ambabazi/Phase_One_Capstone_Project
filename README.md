# Phase_One_Capstone_Project

## DESCRIPTION

A Mini university system that will allow user to interact with the system through the menu, enroll student, track their grades, and also save the data into a file that acts as a database.


# 🎓 University Management System

A Java console application for managing students, courses, and enrollments at a university. Built with object-oriented principles including inheritance, abstraction, custom exceptions, and file persistence.

---

## 📁 Project Structure

```
src/
├── Main.java
├── exceptions/
│   ├── CourseIsFull.java
│   └── StudentEnrolled.java
├── files/
│   └── FileManager.java
├── manager/
│   └── UniversityManager.java
├── model/
│   ├── Person.java         (abstract)
│   ├── Student.java        (abstract, extends Person)
│   ├── Undergraduate.java  (extends Student)
│   ├── Graduate.java       (extends Student)
│   ├── Instructor.java     (extends Person)
│   └── Course.java
└── ui/
    └── Menu.java
```

---

## ✨ Features

- **Register Students** — Add undergraduate or graduate students with name, email, ID, GPA, and department
- **Create Courses** — Define courses with ID, name, credits, and max capacity
- **Enroll Students** — Enroll a student in a course with duplicate and capacity checks
- **View Student Records** — Display full student profile including enrolled courses, grades, and tuition
- **Dean's List** — Generate a list of all students with GPA above 3.5
- **Average GPA by Department** — Calculate the average GPA for any department
- **Top Performing Student** — Find the student with the highest GPA
- **Save & Load Data** — Persist students and courses to CSV files (`students.csv`, `courses.csv`)

---

## 🏗️ OOP Design

### Inheritance Hierarchy

```
Person (abstract)
├── Student (abstract)
│   ├── Undergraduate
│   └── Graduate
└── Instructor
```

### Key Design Decisions

| Concept | Implementation |
|---|---|
| Abstraction | `Person` and `Student` are abstract classes |
| Polymorphism | `calculateTuition()` is overridden in each student subclass |
| Encapsulation | All fields are private with getters/setters |
| Custom Exceptions | `CourseIsFull`, `StudentEnrolled` for meaningful error handling |

### Tuition Calculation

- **Undergraduate** — Flat rate of **100,000 RWF** regardless of courses
- **Graduate** — **60,000 RWF per credit** + a fixed **50,000 RWF research fee**

---

## 🚀 Getting Started

### Prerequisites

- Java 17 or higher
- Any Java IDE (IntelliJ IDEA, Eclipse, VS Code) or the command line

### Running the Application

**Via IDE:** Open the project, navigate to `Main.java`, and run it.

**Via command line:**
```bash
# Compile
javac -d out src/**/*.java src/Main.java

# Run
java -cp out Main
```

---

## 💾 Data Persistence

On exit (option 8), data is saved to two CSV files in the project root:

- `students.csv` — stores type, name, email, ID, GPA, and department
- `courses.csv` — stores course ID, name, credits, and max capacity

These files are automatically loaded on the next startup.

**Example `students.csv`:**
```
Undergraduate,Alice Uwimana,alice@uni.ac.rw,S001,3.8,Computer Science
Graduate,Bob Nkurunziza,bob@uni.ac.rw,S002,3.6,Engineering
```

**Example `courses.csv`:**
```
CS101,Introduction to Programming,3,30
MATH201,Linear Algebra,4,25
```

---

## 🖥️ Menu Options

```
│  1. Register Student                │
│  2. Create Course                   │
│  3. Enroll Student in Course        │
│  4. View Student Record             │
│  5. Generate Dean's List (GPA>3.5)  │
│  6. Average GPA by Department       │
│  7. Top Performing Student          │
│  8. Save and Exit                   │
```

---

## ⚠️ Exception Handling

| Exception | Trigger |
|---|---|
| `CourseIsFull` | Enrollment attempted when course has reached max capacity |
| `StudentEnrolled` | Student tries to enroll in a course they're already in |
| `IllegalArgumentException` | Student ID or Course ID not found during enrollment |

---

## 📝 Notes

- GPA is automatically recalculated when a student receives a grade (grades of `0.0` are treated as "not yet graded" and excluded from GPA calculation)
- Student IDs and Course IDs are **case-insensitive** for lookups
- The `Instructor` model is defined but not yet integrated into the menu — it can be extended in future versions

---

## 👤 Author

Built as a Java OOP project demonstrating real-world university management concepts.