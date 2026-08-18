class Equipment {
    private String id;
    private String name;
    private int availableCount;

    Equipment(String id, String name, int availableCount) {
        this.id = id == null || id.isBlank() ? "Unknown" : id;
        this.name = name == null || name.isBlank() ? "Unknown" : name;
        this.availableCount = Math.max(0, availableCount);
    }

    boolean borrowOne() {
        if (availableCount <= 0) {
            return false;
        }
        availableCount--;
        return true;
    }

    void returnItems(int quantity) {
        if (quantity > 0) {
            availableCount += quantity;
        }
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Name: " + name + " | Available: " + availableCount;
    }
}

public class EquipmentInventory {
    public static void main(String[] args) {
        Equipment laptop = new Equipment("E001", "Laptop", 2);
        Equipment mouse = new Equipment("E002", "Mouse", 0);

        // 測試借用
        System.out.println("借用 Laptop: " + laptop.borrowOne()); // true
        System.out.println("借用 Laptop: " + laptop.borrowOne()); // true
        System.out.println("借用 Laptop: " + laptop.borrowOne()); // false (庫存不足)

        // 測試歸還
        mouse.returnItems(5);
        System.out.println("歸還 5 個 Mouse 後: " + mouse);

        // 輸出狀態
        System.out.println("--- 最終庫存 ---");
        System.out.println(laptop);
        System.out.println(mouse);
    }
}