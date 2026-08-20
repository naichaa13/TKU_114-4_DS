class Task {
    private final String id;
    private final String title;

    Task(String id, String title) {
        this.id = id;
        this.title = title;
    }

    String getId() {
        return id;
    }

    String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Task{id='" + id + "', title='" + title + "'}";
    }
}

// 鏈結節點類別
class TaskNode {
    Task task;
    TaskNode next;

    TaskNode(Task task) {
        this.task = task;
        this.next = null;
    }
}

class TaskLinkedList {
    private TaskNode head;
    private int size;

    // 檢查 id 是否已經存在（確保重複 id 不得加入）
    boolean containsId(String id) {
        TaskNode current = head;
        while (current != null) {
            if (current.task.getId().equals(id)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    // 前端新增
    boolean addFirst(Task task) {
        if (task == null || containsId(task.getId())) {
            return false;
        }
        TaskNode newNode = new TaskNode(task);
        newNode.next = head;
        head = newNode;
        size++;
        return true;
    }

    // 尾端新增
    boolean addLast(Task task) {
        if (task == null || containsId(task.getId())) {
            return false;
        }
        TaskNode newNode = new TaskNode(task);
        if (head == null) {
            head = newNode;
        } else {
            TaskNode current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
        return true;
    }

    // 依 id 查詢
    Task findById(String id) {
        if (id == null) {
            return null;
        }
        TaskNode current = head;
        while (current != null) {
            if (current.task.getId().equals(id)) {
                return current.task;
            }
            current = current.next;
        }
        return null;
    }

    // 依 id 刪除
    boolean removeById(String id) {
        if (head == null || id == null) {
            return false;
        }
        // 刪除 head（頭部）
        if (head.task.getId().equals(id)) {
            head = head.next;
            size--;
            return true;
        }
        // 刪除 middle（中間）或 tail（尾端）
        TaskNode current = head;
        while (current.next != null) {
            if (current.next.task.getId().equals(id)) {
                current.next = current.next.next;
                size--;
                return true;
            }
            current = current.next;
        }
        return false; // 找不到 id
    }

    // 在指定 id 後方插入
    boolean insertAfter(String existingId, Task task) {
        if (task == null || existingId == null || containsId(task.getId())) {
            return false;
        }
        TaskNode current = head;
        while (current != null) {
            if (current.task.getId().equals(existingId)) {
                TaskNode newNode = new TaskNode(task);
                newNode.next = current.next;
                current.next = newNode;
                size++;
                return true;
            }
            current = current.next;
        }
        return false; // 找不到 existingId
    }

    // 取得目前大小
    int size() {
        return size;
    }

    void printAll() {
        System.out.print("TaskList [size=" + size + "]: ");
        TaskNode current = head;
        while (current != null) {
            System.out.print(current.task + (current.next != null ? " -> " : ""));
            current = current.next;
        }
        System.out.println();
    }
}

public class LinkedTaskListSystem {
    public static void main(String[] args) {
        TaskLinkedList list = new TaskLinkedList();
        System.out.println("=== 單向鏈結清單系統測試 ===");

        // 【測試一：空 list 測試】
        System.out.println("1. 測試空 list 查詢 T1：" + list.findById("T1"));
        System.out.println("2. 測試空 list 刪除 T1：" + list.removeById("T1"));
        System.out.println("空 list 大小：" + list.size());
        list.printAll();
        System.out.println();

        // 建立資料並加入（含重複 ID 測試）
        list.addLast(new Task("T01", "任務一"));
        list.addLast(new Task("T02", "任務二"));
        list.addLast(new Task("T03", "任務三"));

        // 測試重複 ID 不得加入
        boolean dupResult = list.addLast(new Task("T02", "重複的任務二"));
        System.out.println("嘗試加入重複 ID 'T02' 結果：" + dupResult);
        list.printAll();
        System.out.println();

        // 【測試二：找不到 ID 測試】
        System.out.println("尋找不存在的 ID 'T99'：" + list.findById("T99"));
        System.out.println("刪除不存在的 ID 'T99'：" + list.removeById("T99"));
        System.out.println();

        // 【測試三：刪除 head 測試】
        System.out.println("--- 刪除 Head (T01) ---");
        System.out.println("刪除結果：" + list.removeById("T01"));
        list.printAll();
        System.out.println();

        // 重新補回節點以便後續測試
        list.addFirst(new Task("T01", "任務一"));
        list.addLast(new Task("T04", "任務四"));
        list.printAll(); // 目前順序: T01 -> T02 -> T03 -> T04

        // 【測試四：刪除 middle 測試】
        System.out.println("--- 刪除 Middle (T02) ---");
        System.out.println("刪除結果：" + list.removeById("T02"));
        list.printAll();
        System.out.println();

        // 【測試五：刪除 tail 測試】
        System.out.println("--- 刪除 Tail (T04) ---");
        System.out.println("刪除結果：" + list.removeById("T04"));
        list.printAll();
        System.out.println("最終清單大小：" + list.size());
    }
}