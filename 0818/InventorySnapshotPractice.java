import java.util.Arrays;

final class InventorySnapshot {
    private final String warehouseId;
    private final int[] quantities;

    // Constructor 使用 Defensive Copy 防止外部修改原陣列
    InventorySnapshot(String warehouseId, int[] quantities) {
        this.warehouseId = warehouseId == null || warehouseId.isBlank() ? "UNKNOWN" : warehouseId;
        // 邊界條件：處理 null 陣列，轉為長度為 0 的陣列
        this.quantities = (quantities == null) ? new int[0] : Arrays.copyOf(quantities, quantities.length);
    }

    // Getter 使用 Defensive Copy 防止外部取得 reference 後修改內部陣列
    int[] getQuantities() {
        return Arrays.copyOf(quantities, quantities.length);
    }

    int totalQuantity() {
        int total = 0;
        for (int q : quantities) {
            total += q;
        }
        return total;
    }

    int outOfStockCount() {
        int count = 0;
        for (int q : quantities) {
            if (q == 0) {
                count++;
            }
        }
        return count;
    }

    @Override
    public String toString() {
        return "Warehouse: " + warehouseId + " | Inventory: " + Arrays.toString(quantities);
    }
}

public class InventorySnapshotPractice {
    public static void main(String[] args) {
        int[] data = { 5, 0, 3, 0 };
        InventorySnapshot snapshot = new InventorySnapshot("WH-001", data);

        System.out.println(snapshot);
        System.out.println("總數量: " + snapshot.totalQuantity()); // 應為 8
        System.out.println("缺貨品項數: " + snapshot.outOfStockCount()); // 應為 2

        // 驗證 Immutable 特性：嘗試從外部修改原陣列
        data[0] = 999;
        System.out.println("\n--- 修改原始陣列後 ---");
        System.out.println("Snapshot 內部依然是: " + snapshot);

        // 驗證 Getter
        int[] copy = snapshot.getQuantities();
        copy[0] = 999;
        System.out.println("修改 Getter 取得的陣列後: " + snapshot);
    }
}