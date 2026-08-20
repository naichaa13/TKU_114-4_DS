class ArrayStack<T> {
    private Object[] data;
    private int size;

    @SuppressWarnings("unchecked")
    ArrayStack(int capacity) {
        // Java 不允許直接建立泛型陣列 (new T[])，故使用 Object[] 並在需要時轉型
        data = new Object[Math.max(1, capacity)];
    }

    boolean push(T value) {
        if (value == null || isFull()) {
            return false;
        }
        data[size] = value;
        size++;
        return true;
    }

    @SuppressWarnings("unchecked")
    T pop() {
        if (isEmpty()) {
            return null;
        }
        size--;
        T value = (T) data[size];
        data[size] = null; // 避免記憶體流失 (Memory Leak)
        return value;
    }

    @SuppressWarnings("unchecked")
    T peek() {
        if (isEmpty()) {
            return null;
        }
        return (T) data[size - 1];
    }

    int size() {
        return size;
    }

    boolean isEmpty() {
        return size == 0;
    }

    boolean isFull() {
        return size == data.length;
    }
}

public class GenericArrayStackDemo {
    public static void main(String[] args) {
        System.out.println("--- 測試 ArrayStack<String> ---");
        ArrayStack<String> stringStack = new ArrayStack<>(2);

        System.out.println("push Java：" + stringStack.push("Java"));
        System.out.println("push DS：" + stringStack.push("DS"));
        System.out.println("push Extra（超過容量）：" + stringStack.push("Extra")); // 應為 false

        System.out.println("isFull：" + stringStack.isFull());
        System.out.println("peek 頂端：" + stringStack.peek());
        System.out.println("pop 彈出：" + stringStack.pop());
        System.out.println("pop 彈出：" + stringStack.pop());
        System.out.println("isEmpty：" + stringStack.isEmpty());
        System.out.println();

        System.out.println("--- 測試 ArrayStack<Integer> ---");
        ArrayStack<Integer> intStack = new ArrayStack<>(3);

        System.out.println("push 100：" + intStack.push(100));
        System.out.println("push 200：" + intStack.push(200));
        System.out.println("size：" + intStack.size());

        System.out.println("peek 頂端：" + intStack.peek());
        System.out.println("pop 彈出：" + intStack.pop());
        System.out.println("剩餘 size：" + intStack.size());
    }
}