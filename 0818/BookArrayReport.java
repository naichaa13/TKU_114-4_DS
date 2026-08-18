class Book {
    private String id;
    private String name;
    private int price;
    private int stock;

    Book(String id, String name, int price, int stock) {
        this.id = id == null || id.isBlank() ? "UNKNOWN" : id;
        this.name = name == null || name.isBlank() ? "Untitled" : name;
        this.price = Math.max(0, price);
        this.stock = Math.max(0, stock);
    }

    int getPrice() {
        return price;
    }

    int getStock() {
        return stock;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | " + name + " | Price: $" + price + " | Stock: " + stock;
    }
}

public class BookArrayReport {
    public static void main(String[] args) {
        Book[] books = {
                new Book("B001", "Java Programming", 800, 5),
                new Book("B002", "Data Structures", 650, 2),
                new Book("B003", "Algorithms", 900, 3),
                new Book("B004", "Database Systems", 750, 10)
        };

        System.out.println("--- 所有書籍 ---");
        for (Book b : books) {
            System.out.println(b);
        }

        // 計算庫存總價值
        long totalValue = 0;
        for (Book b : books) {
            totalValue += (long) b.getPrice() * b.getStock();
        }
        System.out.println("\n總價值: $" + totalValue);

        // 找出價格最高的書
        Book mostExpensive = books[0];
        for (Book b : books) {
            if (b.getPrice() > mostExpensive.getPrice()) {
                mostExpensive = b;
            }
        }
        System.out.println("價格最高的書: " + mostExpensive);

        // 輸出庫存小於或等於 3 的書
        System.out.println("\n--- 低庫存清單 (<= 3) ---");
        for (Book b : books) {
            if (b.getStock() <= 3) {
                System.out.println(b);
            }
        }
    }
}