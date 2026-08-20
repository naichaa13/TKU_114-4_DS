import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ListImplementationLab {

    // 只接收 List<Integer> 的通用方法，執行各項集合操作
    static void testListOperations(String label, List<Integer> list) {
        System.out.println("=== " + label + " 測試開始 ===");

        // 尾端新增
        list.add(10);
        list.add(20);
        list.add(30);
        System.out.println("尾端新增後：" + list);

        // 指定位置插入 (在 index 1 插入 15)
        list.add(1, 15);
        System.out.println("指定位置插入 (index 1 = 15) 後：" + list);

        // 搜尋 (檢查是否包含 20，以及取得索引)
        boolean hasTwenty = list.contains(20);
        int indexOfTwenty = list.indexOf(20);
        System.out.println("是否包含 20：" + hasTwenty + "，其索引位置：" + indexOfTwenty);

        // 刪除索引 2 的元素
        Integer removed = list.remove(2);
        System.out.println("刪除索引 2 的元素 (" + removed + ") 後：" + list);

        // 總和計算
        int sum = 0;
        for (Integer value : list) {
            sum += value;
        }
        System.out.println("目前元素總和：" + sum);
        System.out.println("========================================\n");
    }

    public static void main(String[] args) {
        List<Integer> arrayList = new ArrayList<>();
        List<Integer> linkedList = new LinkedList<>();

        // 傳入共用 method 測試，確認功能結果完全一致
        testListOperations("ArrayList", arrayList);
        testListOperations("LinkedList", linkedList);

        System.out.println("【ArrayList 與 LinkedList 內部成本差異說明】");
        System.out.println("1. 隨機存取 (get/indexOf)：");
        System.out.println("   - ArrayList 基於連續陣列，透過 index 直接定位，成本為 O(1)。");
        System.out.println("   - LinkedList 基於雙向鏈結節點，必須從頭或尾逐一走訪，成本為 O(n)。");
        System.out.println("2. 中間插入與刪除 (add at index / remove)：");
        System.out.println("   - ArrayList 需要將插入/刪除點後方的所有元素進行記憶體搬移，成本為 O(n)。");
        System.out.println("   - LinkedList 在定位到目標節點後，僅需調整前後節點的指標參考，成本為 O(1)（但尋找該節點本身仍需 O(n)）。");
        System.out.println("3. 記憶體locality：");
        System.out.println("   - ArrayList 具有良好的記憶體區域性（Locality of Reference），CPU 快取命中率高。");
        System.out.println("   - LinkedList 每個節點分散在記憶體各處，指標額外消耗較多空間且快取效率較差。");
    }
}