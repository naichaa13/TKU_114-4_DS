class Instructor {
    private final String id;
    private final String name;

    Instructor(String id, String name) {
        this.id = id == null || id.isBlank() ? "UNKNOWN" : id;
        this.name = name == null || name.isBlank() ? "Unknown" : name;
    }

    String label() {
        return id + " " + name;
    }
}

class Course {
    private final String courseCode;
    private final String title;
    private final Instructor instructor;

    Course(String courseCode, String title, Instructor instructor) {
        this.courseCode = courseCode == null || courseCode.isBlank() ? "UNKNOWN" : courseCode;
        this.title = title == null || title.isBlank() ? "Untitled" : title;
        this.instructor = instructor == null ? new Instructor("I000", "Staff") : instructor;
    }

    String summary() {
        return courseCode + " | " + title + " | Instructor: " + instructor.label();
    }
}

public class CourseComposition {
    public static void main(String[] args) {
        Instructor profLin = new Instructor("T001", "Dr. Lin");

        Course java = new Course("CS101", "Java Programming", profLin);
        Course ds = new Course("DS201", "Data Structures", profLin);

        System.out.println(java.summary());
        System.out.println(ds.summary());
    }
}