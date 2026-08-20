public class GenericArrayTools {

    static <T> int countMatches(T[] data, T target) {
        if (data == null || data.length == 0) {
            return 0;
        }
        int count = 0;
        for (T value : data) {
            if (value == null) {
                if (target == null) {
                    count++;
                }
            } else if (value.equals(target)) {
                count++;
            }
        }
        return count;
    }

    // 取得陣列最後一個元素
    static <T> T last(T[] data) {
        if (data == null || data.length == 0) {
            return null;
        }
        return data[data.length - 1];
    }

    // 交換陣列中兩個 index 的元素
    static <T> void swap(T[] data, int first, int second) {
        if (data == null || data.length == 0) {
            return;
        }
        // 檢查 index 是否超出邊界
        if (first < 0 || first >= data.length || second < 0 || second >= data.length) {
            return;
        }
        T temp = data[first];
        data[first] = data[second];
        data[second] = temp;
    }

    public static void main(String[] args) {
        String[] names = { "Amy", "Ben", "Cara", "Ben" };
        Integer[] scores = { 82, 75, 91, 75 };

        // 測試 countMatches
        System.out.println("Ben 出現次數：" + countMatches(names, "Ben"));
        System.out.println("75 分出現次數：" + countMatches(scores, 75));

        // 測試 last
        System.out.println("最後一個名字：" + last(names));
        System.out.println("最後一個分數：" + last(scores));

        // 測試 swap
        System.out.println("交換前 names[0] 與 names[1]：" + names[0] + ", " + names[1]);
        swap(names, 0, 1);
        System.out.println("交換後 names[0] 與 names[1]：" + names[0] + ", " + names[1]);

        // 測試防呆：不合法的 index 應不作動或安全返回
        swap(names, -1, 10);
        System.out.println("不合法 index 測試後陣列長度：" + names.length);
    }
}