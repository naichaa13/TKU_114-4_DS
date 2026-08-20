import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

class DeliveryTask {
    private final String id;
    private final String destination;
    private boolean completed;

    DeliveryTask(String id, String destination) {
        this.id = id;
        this.destination = destination;
        this.completed = false;
    }

    String getId() {
        return id;
    }

    String getDestination() {
        return destination;
    }

    boolean isCompleted() {
        return completed;
    }

    void complete() {
        this.completed = true;
    }

    void reopen() {
        this.completed = false;
    }

    @Override
    public String toString() {
        return "配送編號=" + id + ", 目的地=" + destination + ", 已完成=" + completed;
    }
}

class DeliveryManager {
    private final Map<String, DeliveryTask> taskMap = new HashMap<>();
    private final Deque<DeliveryTask> waitingQueue = new ArrayDeque<>();
    private final Deque<DeliveryTask> completedStack = new ArrayDeque<>();

    // 新增配送任務（重複 id 不得加入）
    boolean addTask(DeliveryTask task) {
        if (task == null || taskMap.containsKey(task.getId())) {
            System.out.println("新增失敗：配送編號 [" + (task != null ? task.getId() : "null") + "] 已存在或資料無效。");
            return false;
        }
        taskMap.put(task.getId(), task);
        waitingQueue.offerLast(task);
        System.out.println("成功新增任務：" + task);
        return true;
    }

    // 處理下一筆配送任務（從 Queue 取出，標記完成，推入 Stack）
    DeliveryTask processNext() {
        DeliveryTask task = waitingQueue.pollFirst();
        if (task == null) {
            System.out.println("處理失敗：目前沒有等待中的配送任務。");
            return null;
        }
        task.complete();
        completedStack.push(task);
        System.out.println("【配送處理】已完成任務：" + task);
        return task;
    }

    // Undo 復原最近完成的一筆任務（從 Stack 彈出，改回未完成，放回 Queue 前端）
    boolean undoLast() {
        DeliveryTask task = completedStack.pollFirst(); // 或 pop()
        if (task == null) {
            System.out.println("Undo 失敗：沒有已完成的任務可供復原。");
            return false;
        }
        task.reopen();
        waitingQueue.offerFirst(task); // 將未完成的任務放回等待佇列前端優先處理
        System.out.println("【復原 Undo】已將任務撤銷並放回等待佇列：" + task);
        return true;
    }

    // 依 id 查詢
    DeliveryTask findById(String id) {
        return taskMap.get(id);
    }

    void printSummary() {
        System.out.println("===== 物流系統狀態統計 =====");
        System.out.println("總任務數：" + taskMap.size());
        System.out.println("等待配送數（Queue）：" + waitingQueue.size());
        System.out.println("已完成歷程數（Stack）：" + completedStack.size());
        System.out.println("----------------------------");
    }
}

public class DeliveryWorkflowSystem {
    public static void main(String[] args) {
        DeliveryManager manager = new DeliveryManager();

        System.out.println("--- 1. 測試新增任務與重複 id 攔截 ---");
        manager.addTask(new DeliveryTask("D001", "台北市信義路"));
        manager.addTask(new DeliveryTask("D002", "台中市台灣大道"));
        manager.addTask(new DeliveryTask("D003", "高雄市中正三路"));

        // 測試重複 id 加入
        manager.addTask(new DeliveryTask("D001", "重複的台北地址"));
        System.out.println();

        manager.printSummary();
        System.out.println();

        System.out.println("--- 2. 測試查詢與配送處理 (FIFO) ---");
        System.out.println("查詢 D002：" + manager.findById("D002"));

        // 依序處理配送任務
        manager.processNext(); // 處理 D001
        manager.processNext(); // 處理 D002
        System.out.println();

        manager.printSummary();
        System.out.println();

        System.out.println("--- 3. 測試 Undo 復原功能 ---");
        manager.undoLast(); // 撤銷最近完成的 D002，使其回到等待佇列
        System.out.println("Undo 後查詢 D002 狀態：" + manager.findById("D002"));
        System.out.println();

        manager.printSummary();
    }
}