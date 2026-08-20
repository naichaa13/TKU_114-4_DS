import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CourseTagReport {
    public static void main(String[] args) {
        String[] tags = { "Java", "Tree", "Graph", "Java", "Tree", "Database", "Java" };

        List<String> tagList = new ArrayList<>();
        Set<String> uniqueTags = new HashSet<>();
        Map<String, Integer> tagCounts = new HashMap<>();

        for (String tag : tags) {
            tagList.add(tag);
            uniqueTags.add(tag);
            tagCounts.put(tag, tagCounts.getOrDefault(tag, 0) + 1);
        }

        System.out.println("【List 原始順序】");
        System.out.println(tagList);
        System.out.println("用途說明：保留標籤被加入時的先後順序與完整歷程（允許重複）。\n");

        System.out.println("【Set 不重複標籤】");
        System.out.println(uniqueTags);
        System.out.println("用途說明：快速過濾重複項目，只關注有哪些不同的標籤存在。\n");

        System.out.println("【Map 統計次數】");
        System.out.println(tagCounts);
        System.out.println("用途說明：透過 Unique Key 對應 Value，快速查詢每一個標籤分別出現了幾次。");
    }
}