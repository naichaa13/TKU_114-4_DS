import java.util.Arrays;

class CircularQueue<T> {
    private final Object[] data;
    private int front;
    private int rear;
    private int size;

    @SuppressWarnings("unchecked")
    CircularQueue(int capacity) {
        data = new Object[Math.max(1, capacity)];
    }

    boolean enqueue(T value) {
        if (isFull() || value == null) {
            return false;
        }
        data[rear] = value;
        rear = (rear + 1) % data.length;
        size++;
        return true;
    }

    @SuppressWarnings("unchecked")
    T dequeue() {
        if (isEmpty()) {
            return null;
        }
        T value = (T) data[front];
        data[front] = null; // 釋放參考
        front = (front + 1) % data.length;
        size--;
        return value;
    }

    @SuppressWarnings("unchecked")
    T peek() {
        return isEmpty() ? null : (T) data[front];
    }

    boolean isEmpty() {
        return size == 0;
    }

    boolean isFull() {
        return size == data.length;
    }

    int size() {
        return size;
    }

    void printState(String actionLabel) {
        System.out.println(String.format("%-15s -> 陣列: %s, front=%d, rear=%d, size=%d",
                actionLabel, Arrays.toString(data), front, rear, size));
    }
}

public class CircularQueuePractice {
    public static void main(String[] args) {
        // 以容量 4 建立 CircularQueue<String>
        CircularQueue<String> queue = new CircularQueue<>(4);
        System.out.println("--- 開始執行指定操作序列 ---");

        queue.enqueue("A");
        queue.printState("enqueue A");

        queue.enqueue("B");
        queue.printState("enqueue B");

        queue.enqueue("C");
        queue.printState("enqueue C");

        queue.dequeue();
        queue.printState("dequeue");

        queue.dequeue();
        queue.printState("dequeue");

        queue.enqueue("D");
        queue.printState("enqueue D");

        queue.enqueue("E");
        queue.printState("enqueue E");

        queue.enqueue("F");
        queue.printState("enqueue F");

        queue.dequeue();
        queue.printState("dequeue");

        queue.enqueue("G");
        queue.printState("enqueue G");

        // 最後依 FIFO 順序取出所有元素
        System.out.println("\n--- 依 FIFO 順序取出所有剩餘元素 ---");
        while (!queue.isEmpty()) {
            System.out.println("依序取出: " + queue.dequeue());
        }
        System.out.println("取出完畢後是否為空：" + queue.isEmpty());
    }
}