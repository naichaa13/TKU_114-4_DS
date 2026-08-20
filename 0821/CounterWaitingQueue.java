import java.util.ArrayDeque;
import java.util.Deque;

class Customer {
    private final String ticketNumber;
    private final String name;

    Customer(String ticketNumber, String name) {
        this.ticketNumber = ticketNumber;
        this.name = name;
    }

    String getTicketNumber() {
        return ticketNumber;
    }

    String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "號碼牌=" + ticketNumber + ", 姓名=" + name;
    }
}

class CounterQueue {
    private final Deque<Customer> waitingQueue = new ArrayDeque<>();

    // 加入隊列 (Enqueue 於尾端)
    void addCustomer(Customer customer) {
        if (customer != null) {
            waitingQueue.offerLast(customer);
            System.out.println("成功取號：[" + customer + "]");
        }
    }

    // 查看下一位 (Peek 隊首，空隊列回傳 null)
    Customer peekNext() {
        return waitingQueue.peekFirst();
    }

    // 服務下一位 (Poll 隊首，空隊列回傳 null)
    Customer serveNext() {
        return waitingQueue.pollFirst();
    }

    // 顯示目前等候數
    int getWaitingCount() {
        return waitingQueue.size();
    }

    // 檢查是否為空
    boolean isEmpty() {
        return waitingQueue.isEmpty();
    }
}

public class CounterWaitingQueue {
    public static void main(String[] args) {
        CounterQueue counter = new CounterQueue();

        System.out.println("--- 櫃台等候 Queue 測試 ---");

        // 測試空隊列時的操作
        System.out.println("目前等候人數：" + counter.getWaitingCount());
        System.out.println("查看下一位：" + (counter.peekNext() == null ? "目前無等候顧客" : counter.peekNext()));
        System.out.println("服務下一位：" + (counter.serveNext() == null ? "目前無顧客可服務" : counter.serveNext()));
        System.out.println();

        // 加入顧客
        counter.addCustomer(new Customer("A001", "Amy"));
        counter.addCustomer(new Customer("A002", "Ben"));
        counter.addCustomer(new Customer("A003", "Cara"));

        System.out.println("\n目前等候人數：" + counter.getWaitingCount());

        // 查看下一位
        Customer next = counter.peekNext();
        System.out.println("下一位將被服務的是：" + next);

        // 服務下一位 (FIFO 順序：Amy 先被服務)
        System.out.println("\n開始叫號服務...");
        Customer served1 = counter.serveNext();
        System.out.println("正在為 [" + served1 + "] 辦理業務。");

        System.out.println("剩餘等候人數：" + counter.getWaitingCount());
        System.out.println("目前的下一位：" + counter.peekNext());

        // 再服務一位 (Ben)
        Customer served2 = counter.serveNext();
        System.out.println("正在為 [" + served2 + "] 辦理業務。");
        System.out.println("剩餘等候人數：" + counter.getWaitingCount());
    }
}