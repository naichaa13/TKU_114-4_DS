import java.util.Arrays;

class CircularIntQueue {
    private final int[] data;
    private int front;
    private int rear;
    private int size;

    CircularIntQueue(int capacity) {
        data = new int[Math.max(1, capacity)];
    }

    boolean enqueue(int value) {
        if (isFull()) {
            return false;
        }
        data[rear] = value;
        rear = (rear + 1) % data.length;
        size++;
        return true;
    }

    Integer dequeue() {
        if (isEmpty()) {
            return null;
        }
        int value = data[front];
        data[front] = 0;
        front = (front + 1) % data.length;
        size--;
        return value;
    }

    Integer peek() {
        return isEmpty() ? null : data[front];
    }

    boolean isEmpty() {
        return size == 0;
    }

    boolean isFull() {
        return size == data.length;
    }

    void clear() {
        Arrays.fill(data, 0); // 將底層陣列所有格子清為 0
        front = 0;
        rear = 0;
        size = 0;
    }

    void printState() {
        System.out.println(Arrays.toString(data)
                + " front=" + front + " rear=" + rear + " size=" + size);
    }
}

public class CircularArrayQueueDemo {
    public static void main(String[] args) {
        CircularIntQueue queue = new CircularIntQueue(3);
        queue.enqueue(10);
        queue.enqueue(20);
        queue.printState();

        System.out.println("dequeue=" + queue.dequeue());
        queue.enqueue(30);
        queue.enqueue(40);
        queue.printState();

        System.out.println("full=" + queue.isFull());
        System.out.println("enqueue 50=" + queue.enqueue(50));
        System.out.println("peek=" + queue.peek());
        System.out.println();

        // 新增clear() 方法
        System.out.println("--- 測試 clear() 狀態 ---");
        queue.clear();
        queue.printState();
        System.out.println("清空後 isEmpty=" + queue.isEmpty());
        System.out.println("清空後 dequeue=" + queue.dequeue()); // 應回傳 null
        System.out.println("清空後 peek=" + queue.peek()); // 應回傳 null
    }
}