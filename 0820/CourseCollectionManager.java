import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

class CourseEnrollment {
    private final String studentId;
    private final String name;
    private int score;
    private final Set<String> tags = new HashSet<>();

    CourseEnrollment(String studentId, String name, int score) {
        this.studentId = studentId;
        this.name = name;
        this.score = Math.max(0, Math.min(100, score));
    }

    String getStudentId() {
        return studentId;
    }

    String getName() {
        return name;
    }

    int getScore() {
        return score;
    }

    void setScore(int score) {
        this.score = Math.max(0, Math.min(100, score));
    }

    void addTag(String tag) {
        if (tag != null && !tag.isBlank()) {
            tags.add(tag.toLowerCase().trim());
        }
    }

    boolean hasTag(String tag) {
        return tag != null && tags.contains(tag.toLowerCase().trim());
    }

    @Override
    public String toString() {
        return studentId + " " + name + " score=" + score + " tags=" + tags;
    }
}

class RegistrationSystem {
    private final List<CourseEnrollment> order = new ArrayList<>();
    private final Set<String> registeredIds = new HashSet<>();
    private final Map<String, CourseEnrollment> byId = new HashMap<>();

    // 報名註冊（若學號已存在則拒絕）
    boolean enroll(CourseEnrollment enrollment) {
        if (enrollment == null || !registeredIds.add(enrollment.getStudentId())) {
            return false;
        }
        order.add(enrollment);
        byId.put(enrollment.getStudentId(), enrollment);
        return true;
    }

    // 依學號尋找
    CourseEnrollment find(String studentId) {
        return byId.get(studentId);
    }

    // 新成績
    boolean updateScore(String studentId, int score) {
        CourseEnrollment enrollment = find(studentId);
        if (enrollment == null) {
            return false;
        }
        enrollment.setScore(score);
        return true;
    }

    // 依標籤查詢，回傳新 List 避免暴露內部 order
    List<CourseEnrollment> findByTag(String tag) {
        List<CourseEnrollment> result = new ArrayList<>();
        if (tag == null || tag.isBlank()) {
            return result;
        }
        String targetTag = tag.toLowerCase().trim();
        for (CourseEnrollment enrollment : order) {
            if (enrollment.hasTag(targetTag)) {
                result.add(enrollment);
            }
        }
        return result;
    }

    Map<String, Integer> scoreDistribution() {
        Map<String, Integer> distribution = new HashMap<>();
        distribution.put("A", 0);
        distribution.put("B", 0);
        distribution.put("C", 0);
        distribution.put("D", 0);
        distribution.put("F", 0);

        for (CourseEnrollment e : order) {
            int score = e.getScore();
            if (score >= 90) {
                distribution.put("A", distribution.get("A") + 1);
            } else if (score >= 80) {
                distribution.put("B", distribution.get("B") + 1);
            } else if (score >= 70) {
                distribution.put("C", distribution.get("C") + 1);
            } else if (score >= 60) {
                distribution.put("D", distribution.get("D") + 1);
            } else {
                distribution.put("F", distribution.get("F") + 1);
            }
        }
        return distribution;
    }

    // 取得前 count 名，若 count 大於人數則回傳所有資料
    List<CourseEnrollment> top(int count) {
        List<CourseEnrollment> ranked = ranking();
        if (count >= ranked.size()) {
            return ranked;
        }
        return new ArrayList<>(ranked.subList(0, count));
    }

    // 取得依成績排序的完整排名 (Copy)
    List<CourseEnrollment> ranking() {
        List<CourseEnrollment> result = new ArrayList<>(order);
        result.sort(Comparator.comparingInt(CourseEnrollment::getScore)
                .reversed()
                .thenComparing(CourseEnrollment::getStudentId));
        return result;
    }

    // 移除低分並確保 List、Set、Map 保持一致
    void removeBelow(int minimum) {
        order.removeIf(enrollment -> enrollment.getScore() < minimum);
        syncCollections();
    }

    // 重建 Set 與 Map 確保三者同步
    private void syncCollections() {
        registeredIds.clear();
        byId.clear();
        for (CourseEnrollment enrollment : order) {
            registeredIds.add(enrollment.getStudentId());
            byId.put(enrollment.getStudentId(), enrollment);
        }
    }
}

public class CourseCollectionManager {
    public static void main(String[] args) {
        RegistrationSystem system = new RegistrationSystem();

        CourseEnrollment e1 = new CourseEnrollment("S101", "Amy", 88);
        CourseEnrollment e2 = new CourseEnrollment("S102", "Ben", 45); // F
        CourseEnrollment e3 = new CourseEnrollment("S103", "Cara", 92); // A
        CourseEnrollment e4 = new CourseEnrollment("S104", "Dan", 75); // C
        CourseEnrollment e5 = new CourseEnrollment("S105", "Eva", 92); // 同分 (Cara 92)
        CourseEnrollment e6 = new CourseEnrollment("S106", "Fay", 62); // D

        e1.addTag("Java");
        e1.addTag("JAVA");
        e1.addTag("   ");
        e3.addTag("Tree");
        e5.addTag("Tree");
        e6.addTag("Database");

        System.out.println("enroll S101 (Amy)：" + system.enroll(e1));
        System.out.println("enroll S101 重複學號測試：" + system.enroll(new CourseEnrollment("S101", "AmyClone", 100))); // 應為
                                                                                                                  // false
        system.enroll(e2);
        system.enroll(e3);
        system.enroll(e4);
        system.enroll(e5);
        system.enroll(e6);
        System.out.println();

        System.out.println("更新 S102 (Ben) 成績從 45 到 65： " + system.updateScore("S102", 65));
        System.out.println();

        System.out.println("【標籤查詢：Tree】");
        for (CourseEnrollment e : system.findByTag("Tree")) {
            System.out.println("  - " + e);
        }
        System.out.println();

        System.out.println("【成績等第分佈統計】");
        System.out.println(system.scoreDistribution());
        System.out.println();

        System.out.println("【前 3 名排名】");
        for (CourseEnrollment e : system.top(3)) {
            System.out.println("  - " + e);
        }
        System.out.println();

        System.out.println("執行 removeBelow(60)（清除低於 60 分的學生）...");
        system.removeBelow(60);

        System.out.println("【清理後的完整排名與同步檢查】");
        for (CourseEnrollment e : system.ranking()) {
            System.out.println("  - " + e);
        }
        System.out.println("尋找已被移除的 S102 (Ben)：" + system.find("S102")); // 應為 null，代表 Map 已同步清理
    }
}