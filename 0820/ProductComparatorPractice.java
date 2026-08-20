import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class StoreProduct implements Comparable<StoreProduct> {
    private final String id;
    private final String name;
    private final int price;
    private final int stock;

    StoreProduct(String id, String name, int price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    String getId() {
        return id;
    }

    String getName() {
        return name;
    }

    int getPrice() {
        return price;
    }

    int getStock() {
        return stock;
    }

    // Natural order：依 id 升冪
    @Override
    public int compareTo(StoreProduct other) {
        return this.id.compareTo(other.id);
    }

    @Override
    public String toString() {
        return "ID=" + id + ", 名稱=" + name + ", 價格=" + price + ", 庫存=" + stock;
    }
}

public class ProductComparatorPractice {
    public static void main(String[] args) {
        List<StoreProduct> originalProducts = new ArrayList<>();
        originalProducts.add(new StoreProduct("P103", "滑鼠", 500, 10));
        originalProducts.add(new StoreProduct("P101", "鍵盤", 1200, 5));
        originalProducts.add(new StoreProduct("P102", "螢幕", 5000, 2));
        originalProducts.add(new StoreProduct("P105", "耳機", 1200, 20)); // 與鍵盤同價 (1200)
        originalProducts.add(new StoreProduct("P104", "隨身碟", 300, 10)); // 與滑鼠同庫存 (10)

        System.out.println("【原始順序】");
        for (StoreProduct p : originalProducts) {
            System.out.println(p);
        }
        System.out.println();

        // 測試 Natural order（依 id 升冪）：排序前建立 copy
        List<StoreProduct> sortedById = new ArrayList<>(originalProducts);
        sortedById.sort(null); // 傳入 null 代表使用 Comparable 的 natural order
        System.out.println("【規則一：Natural order 依 id 升冪】");
        for (StoreProduct p : sortedById) {
            System.out.println(p);
        }
        System.out.println();

        // 測試 Comparator 一：依 price 升冪，同價時依 name
        List<StoreProduct> sortedByPrice = new ArrayList<>(originalProducts);
        Comparator<StoreProduct> byPriceAndName = Comparator
                .comparingInt(StoreProduct::getPrice)
                .thenComparing(StoreProduct::getName);
        sortedByPrice.sort(byPriceAndName);
        System.out.println("【規則二：依 price 升冪，同價時依 name】");
        for (StoreProduct p : sortedByPrice) {
            System.out.println(p);
        }
        System.out.println();

        // 測試 Comparator 二：依 stock 降冪，同庫存時依 id
        List<StoreProduct> sortedByStock = new ArrayList<>(originalProducts);
        Comparator<StoreProduct> byStockAndId = Comparator
                .comparingInt(StoreProduct::getStock)
                .reversed()
                .thenComparing(StoreProduct::getId);
        sortedByStock.sort(byStockAndId);
        System.out.println("【規則三：依 stock 降冪，同庫存時依 id】");
        for (StoreProduct p : sortedByStock) {
            System.out.println(p);
        }
        System.out.println();

        // 驗證原始順序是否被保留
        System.out.println("【驗證原始順序是否未被改變】");
        System.out.println("原始清單的第一筆 ID: " + originalProducts.get(0).getId() + " (應為 P103)");
    }
}