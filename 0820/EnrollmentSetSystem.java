import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

class Enrollment {
    private final String studentId;
    private final String courseCode;
    private final String studentName;

    Enrollment(String studentId, String courseCode, String studentName) {
        this.studentId = studentId;
        this.courseCode = courseCode;
        this.studentName = studentName;
    }

    String getStudentId() {
        return studentId;
    }

    String getCourseCode() {
        return courseCode;
    }

    String getStudentName() {
        return studentName;
    }

    // 以 studentId + courseCode 作為身分判定基準
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Enrollment enrollment)) {
            return false;
        }
        return Objects.equals(studentId, enrollment.studentId) &&
                Objects.equals(courseCode, enrollment.courseCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, courseCode);
    }

    @Override
    public String toString() {
        return "學號=" + studentId + ", 課程=" + courseCode + ", 姓名=" + studentName;
    }
}

public class EnrollmentSetSystem {
    public static void main(String[] args) {
        Set<Enrollment> enrollments = new HashSet<>();

        Enrollment e1 = new Enrollment("S101", "CS101", "Amy");
        Enrollment e2 = new Enrollment("S101", "CS102", "Amy"); // 同一人，不同課程 -> 應成功
        Enrollment e3 = new Enrollment("S101", "CS101", "Amy"); // 同一人，同一課程 -> 應失敗（重複）

        System.out.println("新增 Amy 報名 CS101：" + enrollments.add(e1)); // true
        System.out.println("新增 Amy 報名 CS102（不同課程）：" + enrollments.add(e2)); // true
        System.out.println("再次新增 Amy 報名 CS101（重複）：" + enrollments.add(e3)); // false

        System.out.println("目前總報名數：" + enrollments.size()); // 2
        System.out.println();

        Enrollment duplicateQuery = new Enrollment("S101", "CS101", "Amy Chen (不同名字沒關係)");

        System.out.println("檢查是否包含 (S101, CS101)：" + enrollments.contains(duplicateQuery)); // true

        boolean isRemoved = enrollments.remove(duplicateQuery);
        System.out.println("取消 (S101, CS101) 報名結果：" + isRemoved); // true
        System.out.println("取消後總報名數：" + enrollments.size()); // 1
        System.out.println("目前剩餘報名：" + enrollments);
    }
}