import java.util.Arrays;

class DynamicArray<T> {
    private Object[] data;
    private int size;

    @SuppressWarnings("unchecked")
    DynamicArray(int initialCapacity) {
        data = new Object[Math.max(1, initialCapacity)];
    }

    // 尾端新增
    void add(T value) {
        ensureCapacity();
        data[size] = value;
        size++;
    }

    // 指定位置插入 (index 範圍允許 0 至 size)
    void add(int index, T value) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index=" + index + ", size=" + size);
        }
        ensureCapacity();
        // 將 index 後方的元素全部往後搬移一格
        for (int i = size; i > index; i--) {
            data[i] = data[i - 1];
        }
        data[index] = value;
        size++;
    }

    // 取得元素
    @SuppressWarnings("unchecked")
    T get(int index) {
        checkIndex(index);
        return (T) data[index];
    }

    // 修改元素
    @SuppressWarnings("unchecked")
    T set(int index, T value) {
        checkIndex(index);
        T oldValue = (T) data[index];
        data[index] = value;
        return oldValue;
    }

    // 刪除指定索引元素
    @SuppressWarnings("unchecked")
    T remove(int index) {
        checkIndex(index);
        T removedValue = (T) data[index];
        // 將 index 後方的元素全部往前搬移一格
        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
        }
        size--;
        data[size] = null; // 移除後最後一個無效格設為 null
        return removedValue;
    }

    int size() {
        return size;
    }

    int capacity() {
        return data.length;
    }

    // 檢查容量，若滿了則擴充為兩倍
    private void ensureCapacity() {
        if (size == data.length) {
            data = Arrays.copyOf(data, data.length * 2);
        }
    }

    // 檢查存取索引是否合法 (0 <= index < size)
    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index=" + index + ", size=" + size);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(data[i]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        return sb.append("]").toString();
    }
}

public class DynamicArrayPractice {
    public static void main(String[] args) {
        // 1. 使用 String 測試 DynamicArray
        System.out.println("--- 測試 DynamicArray<String> ---");
        DynamicArray<String> strArray = new DynamicArray<>(2);
        strArray.add("Java");
        strArray.add("Data");
        System.out
                .println("初始新增後：" + strArray + " (size=" + strArray.size() + ", capacity=" + strArray.capacity() + ")");

        // 觸發擴容 (capacity 2 -> 4)
        strArray.add("Structures");
        System.out
                .println("觸發擴容後：" + strArray + " (size=" + strArray.size() + ", capacity=" + strArray.capacity() + ")");

        // 測試指定位置插入
        strArray.add(1, "Awesome");
        System.out.println("在 index 1 插入 'Awesome' 後：" + strArray);

        // 測試 get 與 set
        System.out.println("取得 index 2：" + strArray.get(2));
        strArray.set(2, "Database");
        System.out.println("將 index 2 修改為 'Database' 後：" + strArray);

        // 測試 remove
        String removedStr = strArray.remove(1);
        System.out.println("移除 index 1 (" + removedStr + ") 後：" + strArray);
        System.out.println();

        // 2. 使用 Integer 測試與異常防呆
        System.out.println("--- 測試 DynamicArray<Integer> 與防呆 ---");
        DynamicArray<Integer> intArray = new DynamicArray<>(1);
        intArray.add(10);
        intArray.add(20);
        intArray.add(30);
        System.out.println("Integer 陣列：" + intArray + " (capacity=" + intArray.capacity() + ")");

        // 測試空結構刪除或不合法 index 異常防呆
        try {
            intArray.get(-1); // 測試 -1 索引
        } catch (IndexOutOfBoundsException e) {
            System.out.println("捕捉到預期例外 (index -1)：" + e.getMessage());
        }

        try {
            intArray.get(intArray.size()); // 測試等於 size 的不合法讀取索引
        } catch (IndexOutOfBoundsException e) {
            System.out.println("捕捉到預期例外 (index == size)：" + e.getMessage());
        }

        // 清空陣列測試空結構刪除防呆
        intArray.remove(2);
        intArray.remove(1);
        intArray.remove(0);
        System.out.println("清空後 size=" + intArray.size());

        try {
            intArray.remove(0); // 測試空結構刪除
        } catch (IndexOutOfBoundsException e) {
            System.out.println("捕捉到預期例外 (空結構刪除)：" + e.getMessage());
        }
    }
}