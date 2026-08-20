import java.util.ArrayList;
import java.util.List;

class Product {
    private final String id;
    private final String name;
    private final int price;

    Product(String id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
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

    @Override
    public String toString() {
        return "Product{id='" + id + "', name='" + name + "', price=" + price + "}";
    }
}

// 2. 建立通用泛型類別 Repository<T>
class Repository<T> {
    private final List<T> items = new ArrayList<>();

    void add(T item) {
        if (item != null) {
            items.add(item);
        }
    }

    T get(int index) {
        if (index < 0 || index >= items.size()) {
            return null;
        }
        return items.get(index);
    }

    boolean remove(T item) {
        return items.remove(item);
    }

    T remove(int index) {
        if (index < 0 || index >= items.size()) {
            return null;
        }
        return items.remove(index);
    }

    // 取得目前數量
    int size() {
        return items.size();
    }

    @Override
    public String toString() {
        return items.toString();
    }
}

public class GenericRepositorySystem {
    public static void main(String[] args) {
        System.out.println("--- 測試 Repository<String> ---");
        Repository<String> stringRepo = new Repository<>();
        stringRepo.add("Java");
        stringRepo.add("Data Structures");
        stringRepo.add("Algorithms");

        System.out.println("字串倉儲內容：" + stringRepo);
        System.out.println("倉儲大小：" + stringRepo.size());
        System.out.println("索引 1 的元素：" + stringRepo.get(1));

        stringRepo.remove("Java");
        System.out.println("移除 'Java' 後的內容：" + stringRepo);
        System.out.println();

        System.out.println("--- 測試 Repository<Product> ---");
        Repository<Product> productRepo = new Repository<>();
        productRepo.add(new Product("P01", "筆記型電腦", 35000));
        productRepo.add(new Product("P02", "機械鍵盤", 2500));
        productRepo.add(new Product("P03", "無線滑鼠", 1200));

        System.out.println("商品倉儲大小：" + productRepo.size());
        System.out.println("索引 0 的商品：" + productRepo.get(0));

        Product removed = productRepo.remove(1);
        System.out.println("被移除的商品：" + removed);
        System.out.println("移除後的商品倉儲內容：");
        for (int i = 0; i < productRepo.size(); i++) {
            System.out.println("  - " + productRepo.get(i));
        }
    }
}