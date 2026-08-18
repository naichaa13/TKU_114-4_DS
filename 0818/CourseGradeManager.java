class CourseGrade {
    private final String id;
    private final String name;
    private final int dailyScore;
    private final int midtermScore;
    private final int finalScore;
    private final int attendance;

    CourseGrade(String id, String name, int daily, int midterm, int fin, int attendance) {
        this.id = id == null || id.isBlank() ? "UNKNOWN" : id;
        this.name = name == null || name.isBlank() ? "Unknown" : name;
        this.dailyScore = Math.min(100, Math.max(0, daily));
        this.midtermScore = Math.min(100, Math.max(0, midterm));
        this.finalScore = Math.min(100, Math.max(0, fin));
        this.attendance = Math.min(100, Math.max(0, attendance));
    }

    String getName() {
        return name;
    }

    double calculateFinalScore() {
        return (dailyScore * 0.5) + (midtermScore * 0.2) + (finalScore * 0.2) + (attendance * 0.1);
    }

    String getLevel() {
        double score = calculateFinalScore();
        if (score >= 90)
            return "A";
        if (score >= 80)
            return "B";
        if (score >= 70)
            return "C";
        if (score >= 60)
            return "D";
        return "F";
    }

    @Override
    public String toString() {
        return String.format("ID: %s, Name: %-6s, Final: %5.1f, Level: %s",
                id, name, calculateFinalScore(), getLevel());
    }
}

public class CourseGradeManager {
    public static void main(String[] args) {
        CourseGrade[] students = {
                new CourseGrade("S001", "Amy", 85, 90, 88, 100),
                new CourseGrade("S002", "Ben", 50, 45, 55, 60),
                new CourseGrade("S003", "Cara", 95, 92, 98, 100),
                new CourseGrade("S004", "David", 60, 65, 58, 80),
                new CourseGrade("S005", "Eve", 40, 30, 35, 50)
        };

        double totalScore = 0;
        CourseGrade highest = students[0];

        System.out.println("--- 成績單 ---");
        for (CourseGrade s : students) {
            System.out.println(s);
            totalScore += s.calculateFinalScore();
            if (s.calculateFinalScore() > highest.calculateFinalScore()) {
                highest = s;
            }
        }

        System.out.println("\n平均總分: " + (totalScore / students.length));
        System.out.println("最高分學生: " + highest.getName() + " (" + highest.calculateFinalScore() + ")");

        System.out.println("\n--- 不及格名單 ---");
        for (CourseGrade s : students) {
            if (s.calculateFinalScore() < 60) {
                System.out.println(s.getName() + " (總分: " + s.calculateFinalScore() + ")");
            }
        }
    }
}