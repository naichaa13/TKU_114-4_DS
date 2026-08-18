class Customer {
    private final String id;
    private final String name;

    Customer(String id, String name) {
        this.id = id == null || id.isBlank() ? "UNKNOWN" : id;
        this.name = name == null || name.isBlank() ? "Unknown" : name;
    }

    String label() {
        return id + " (" + name + ")";
    }
}

class OrderItem {
    private final String productName;
    private final int price;

    OrderItem(String productName, int price) {
        this.productName = productName == null || productName.isBlank() ? "Unknown" : productName;
        this.price = Math.max(0, price);
    }

    int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return productName + " ($" + price + ")";
    }
}

class CustomerOrder {
    private final String orderId;
    private final Customer customer;
    private final OrderItem[] items;
    private int itemCount;

    CustomerOrder(String orderId, Customer customer, int maxItems) {
        this.orderId = orderId == null || orderId.isBlank() ? "UNKNOWN" : orderId;
        this.customer = customer; // Composition: 組合 Customer 物件
        this.items = new OrderItem[Math.max(1, maxItems)];
        this.itemCount = 0;
    }

    boolean addItem(OrderItem item) {
        if (item == null || itemCount >= items.length) {
            return false;
        }
        items[itemCount++] = item;
        return true;
    }

    int calculateTotal() {
        int total = 0;
        for (int i = 0; i < itemCount; i++) {
            total += items[i].getPrice();
        }
        return total;
    }

    void printSummary() {
        System.out.println("Order ID: " + orderId);
        System.out.println("Customer: " + customer.label());
        System.out.println("Items:");
        for (int i = 0; i < itemCount; i++) {
            System.out.println(" - " + items[i]);
        }
        System.out.println("Total Amount: $" + calculateTotal());
    }
}

public class CustomerOrderSystem {
    public static void main(String[] args) {
        Customer alice = new Customer("C001", "Alice");
        CustomerOrder order = new CustomerOrder("O999", alice, 3);

        order.addItem(new OrderItem("Keyboard", 1200));
        order.addItem(new OrderItem("Mouse", 500));
        order.addItem(new OrderItem("Monitor", 3000));

        order.printSummary();
    }
}