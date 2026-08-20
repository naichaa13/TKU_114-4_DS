import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CollectionChoiceReport {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("            Java 集合選擇報告與實作展示            ");
        System.out.println("==================================================\n");

        // --------------------------------------------------
        // 需求 1：保留搜尋紀錄且允許重複
        // --------------------------------------------------
        // 解析：搜尋紀錄需要依時間順序保留，且允許使用者搜尋相同的關鍵字多次。
        // 選擇：Interface 選擇 List，Implementation 選擇 ArrayList。
        List<String> searchHistory = new ArrayList<>();
        searchHistory.add("Java 17");
        searchHistory.add("Data Structures");
        searchHistory.add("Java 17"); // 允許重複

        System.out.println("【需求 1】保留搜尋紀錄且允許重複");
        System.out.println("  - 選擇 Interface : List");
        System.out.println("  - 選擇 Implementation : ArrayList");
        System.out.println("  - 操作結果 : " + searchHistory);
        System.out.println("--------------------------------------------------\n");

        // --------------------------------------------------
        // 需求 2：保存不重複會員編號
        // --------------------------------------------------
        // 解析：會員編號具備唯一性，不能重複註冊，且不強求排序。
        // 選擇：Interface 選擇 Set，Implementation 選擇 HashSet。
        Set<String> memberIds = new HashSet<>();
        boolean add1 = memberIds.add("M001");
        boolean add2 = memberIds.add("M002");
        boolean add3 = memberIds.add("M001"); // 重複加入，應為 false

        System.out.println("【需求 2】保存不重複會員編號");
        System.out.println("  - 選擇 Interface : Set");
        System.out.println("  - 選擇 Implementation : HashSet");
        System.out.println("  - 操作結果 : " + memberIds + " (重複加入 M001 結果: " + add3 + ")");
        System.out.println("--------------------------------------------------\n");

        // --------------------------------------------------
        // 需求 3：以學號查詢成績
        // --------------------------------------------------
        // 解析：需要建立 Key-Value 對應關係，透過學號（Key）以 O(1) 效能快速查出成績（Value）。
        // 選擇：Interface 選擇 Map，Implementation 選擇 HashMap。
        Map<String, Integer> studentScores = new HashMap<>();
        studentScores.put("S101", 92);
        studentScores.put("S102", 85);

        System.out.println("【需求 3】以學號查詢成績");
        System.out.println("  - 選擇 Interface : Map");
        System.out.println("  - 選擇 Implementation : HashMap");
        System.out.println("  - 操作結果 : 學號 S101 的成績為 " + studentScores.get("S101") + "，完整對應: " + studentScores);
        System.out.println("--------------------------------------------------\n");

        // --------------------------------------------------
        // 需求 4：依到達順序處理列印工作
        // --------------------------------------------------
        // 解析：印表機任務必須遵守先進先出（FIFO）原則，先送出的工作先列印。
        // 選擇：Interface 選擇 Deque (作為 Queue 使用)，Implementation 選擇 ArrayDeque。
        Deque<String> printQueue = new ArrayDeque<>();
        printQueue.offerLast("Document1.pdf");
        printQueue.offerLast("Report.docx");

        String nextPrint = printQueue.pollFirst(); // 取出最早到達的工作

        System.out.println("【需求 4】依到達順序處理列印工作 (Queue FIFO)");
        System.out.println("  - 選擇 Interface : Deque (Queue)");
        System.out.println("  - 選擇 Implementation : ArrayDeque");
        System.out.println("  - 操作結果 : 優先被處理的列印工作是 [" + nextPrint + "]，剩餘待列印: " + printQueue);
        System.out.println("--------------------------------------------------\n");

        // --------------------------------------------------
        // 需求 5：復原最近操作
        // --------------------------------------------------
        // 解析：復原功能（Undo）需要遵守後進先出（LIFO）原則，最近執行的動作最先被撤銷。
        // 選擇：Interface 選擇 Deque (作為 Stack 使用)，Implementation 選擇 ArrayDeque。
        Deque<String> undoStack = new ArrayDeque<>();
        undoStack.push("Type A");
        undoStack.push("Type B"); // 最近的操作

        String undoneAction = undoStack.pop(); // 彈出最近操作

        System.out.println("【需求 5】復原最近操作 (Stack LIFO)");
        System.out.println("  - 選擇 Interface : Deque (Stack)");
        System.out.println("  - 選擇 Implementation : ArrayDeque");
        System.out.println("  - 操作結果 : 被 Undo 撤銷的最近操作是 [" + undoneAction + "]，剩餘歷史: " + undoStack);
        System.out.println("================================0==================");
    }
}