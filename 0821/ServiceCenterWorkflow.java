import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class ServiceTicket {
    private final String ticketId;
    private final String customerName;
    private final String serviceType;
    private boolean completed;

    ServiceTicket(String ticketId, String customerName, String serviceType) {
        this.ticketId = ticketId;
        this.customerName = customerName;
        this.serviceType = serviceType;
        this.completed = false;
    }

    String getTicketId() {
        return ticketId;
    }

    String getCustomerName() {
        return customerName;
    }

    String getServiceType() {
        return serviceType;
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
        return "票號=" + ticketId + ", 客戶=" + customerName + ", 業務=" + serviceType + ", 已完成=" + completed;
    }
}

class ServiceCenterSystem {
    private final Map<String, ServiceTicket> ticketMap = new HashMap<>();
    private final Set<String> registeredIds = new HashSet<>();
    private final Deque<ServiceTicket> waitingQueue = new ArrayDeque<>();
    private final Deque<ServiceTicket> completedStack = new ArrayDeque<>();

    // 防止重複 id，加入 Map、Set 與 Waiting Queue
    boolean createTicket(ServiceTicket ticket) {
        if (ticket == null || ticket.getTicketId() == null || registeredIds.contains(ticket.getTicketId())) {
            System.out.println("建立失敗：票號 [" + (ticket != null ? ticket.getTicketId() : "null") + "] 重複或資料無效。");
            return false;
        }
        registeredIds.add(ticket.getTicketId());
        ticketMap.put(ticket.getTicketId(), ticket);
        waitingQueue.offerLast(ticket);
        System.out.println("成功建立票券：" + ticket);
        return true;
    }

    // 處理下一位，從 Queue 移出，標記完成，推入 Stack
    ServiceTicket processNext() {
        ServiceTicket ticket = waitingQueue.pollFirst();
        if (ticket == null) {
            System.out.println("處理失敗：目前等待 Queue 為空，無人等候。");
            return null;
        }
        ticket.complete();
        completedStack.push(ticket);
        System.out.println("【叫號處理】正在為 [" + ticket.getTicketId() + " " + ticket.getCustomerName() + "] 辦理業務。");
        return ticket;
    }

    // 取消等待票券：只能作用於尚未處理的 ticket
    boolean cancelWaiting(String ticketId) {
        if (ticketId == null || !ticketMap.containsKey(ticketId)) {
            System.out.println("取消失敗：找不到票號 [" + ticketId + "]。");
            return false;
        }
        ServiceTicket ticket = ticketMap.get(ticketId);
        // 檢查是否還在 waitingQueue 中（即尚未被處理）
        if (ticket.isCompleted()) {
            System.out.println("取消失敗：票號 [" + ticketId + "] 已經完成處理，無法取消等待。");
            return false;
        }

        // 從 waitingQueue 中移除 (利用 Deque 的 remove 實作)
        boolean removedFromQueue = waitingQueue.remove(ticket);
        if (removedFromQueue) {
            ticketMap.remove(ticketId);
            registeredIds.remove(ticketId);
            System.out.println("已成功取消尚未處理的票券：" + ticket);
            return true;
        }

        System.out.println("取消失敗：該票券不在等候佇列中。");
        return false;
    }

    // 復原最近完成 ：將最後完成的 ticket 放回 waiting queue 前端
    boolean undoLastCompletion() {
        ServiceTicket ticket = completedStack.pollFirst(); // 或 pop()
        if (ticket == null) {
            System.out.println("Undo 失敗：完成歷程 Stack 為空，無法復原。");
            return false;
        }
        ticket.reopen();
        waitingQueue.offerFirst(ticket); // 放回 waiting queue 前端優先處理
        System.out.println("【復原 Undo】已將票號 [" + ticket.getTicketId() + "] 撤銷完成狀態，並放回等待佇列前端。");
        return true;
    }

    // id 查詢
    ServiceTicket findById(String ticketId) {
        return ticketMap.get(ticketId);
    }

    void printSummary() {
        System.out.println("===== 服務中心系統狀態摘要 =====");
        System.out.println("總管理票數 (Map)：" + ticketMap.size());
        System.out.println("等待佇列人數 (Queue)：" + waitingQueue.size());
        System.out.println("完成歷程筆數 (Stack)：" + completedStack.size());
        System.out.println("--------------------------------");
    }
}

public class ServiceCenterWorkflow {
    public static void main(String[] args) {
        ServiceCenterSystem system = new ServiceCenterSystem();

        System.out.println("--- 1. 測試建立票券與重複 ID 拒絕 ---");
        system.createTicket(new ServiceTicket("TK101", "Amy", "開戶"));
        system.createTicket(new ServiceTicket("TK102", "Ben", "存款"));
        system.createTicket(new ServiceTicket("TK103", "Cara", "匯款"));

        // 測試重複 ID 拒絕
        system.createTicket(new ServiceTicket("TK101", "Amy 重複", "貸款"));
        System.out.println();

        system.printSummary();
        System.out.println();

        System.out.println("--- 2. 測試空 Queue 與叫號處理 ---");
        // 先建立一個情境來測空 Queue，此時我們把目前的人都處理完後來測
        system.processNext(); // 處理 TK101
        system.processNext(); // 處理 TK102
        system.processNext(); // 處理 TK103

        // 此時 Queue 已空，測試「空 Queue 叫號」
        system.processNext();
        System.out.println();

        System.out.println("--- 3. 測試取消不存在的 ID 與取消未處理票券 ---");
        system.createTicket(new ServiceTicket("TK104", "Dan", "換卡"));
        system.createTicket(new ServiceTicket("TK105", "Eva", "理財"));

        // 測試取消不存在的 ID
        system.cancelWaiting("TK999");

        // 測試取消尚未處理的 ticket (TK105 Eva)
        system.cancelWaiting("TK105");
        System.out.println();

        system.printSummary();
        System.out.println();

        System.out.println("--- 4. 測試連續兩次 Undo ---");
        // 先處理掉 TK104，讓 Stack 有資料
        system.processNext(); // 處理 TK104

        system.printSummary();

        // 測試連續兩次 undo（第一次會成功復原 TK104，第二次因為 Stack 為空會觸發防呆）
        System.out.println("執行第 1 次 Undo：");
        system.undoLastCompletion();

        System.out.println("執行第 2 次 Undo（此時 Stack 應已空）：");
        system.undoLastCompletion();
    }
}