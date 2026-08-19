interface DeliveryMethod {
    int calculateFee(int weight);

    String getEstimateDescription();

    String getName();
}

class HomeDelivery implements DeliveryMethod {
    private int baseFee;

    HomeDelivery(int baseFee) {
        this.baseFee = Math.max(0, baseFee);
    }

    @Override
    public int calculateFee(int weight) {
        int safeWeight = Math.max(0, weight);
        return baseFee + safeWeight * 15;
    }

    @Override
    public String getEstimateDescription() {
        return "約 1-2 個工作天送達";
    }

    @Override
    public String getName() {
        return "宅配";
    }
}

class ConvenienceStorePickup implements DeliveryMethod {
    private int fixedFee;

    ConvenienceStorePickup(int fixedFee) {
        this.fixedFee = Math.max(0, fixedFee);
    }

    @Override
    public int calculateFee(int weight) {
        return fixedFee;
    }

    @Override
    public String getEstimateDescription() {
        return "約 2-3 個工作天送達指定門市";
    }

    @Override
    public String getName() {
        return "超商取貨";
    }
}

class SelfPickup implements DeliveryMethod {
    @Override
    public int calculateFee(int weight) {
        return 0; // 自取免運費
    }

    @Override
    public String getEstimateDescription() {
        return "即日起可至門市自行取貨";
    }

    @Override
    public String getName() {
        return "自取";
    }
}

class OrderService {
    private String orderId;
    private int weight;
    private DeliveryMethod deliveryMethod; // 透過 Composition 保存介面

    OrderService(String orderId, int weight, DeliveryMethod deliveryMethod) {
        this.orderId = orderId;
        this.weight = Math.max(0, weight);
        this.deliveryMethod = deliveryMethod;
    }

    void processOrder() {
        int fee = deliveryMethod.calculateFee(weight);
        System.out.println("訂單編號：" + orderId);
        System.out.println("配送方式：" + deliveryMethod.getName());
        System.out.println("商品重量：" + weight + " kg");
        System.out.println("計算運費：" + fee + " 元");
        System.out.println("預估說明：" + deliveryMethod.getEstimateDescription());
        System.out.println("--------------------");
    }
}

public class DeliveryStrategySystem {
    public static void main(String[] args) {
        OrderService order1 = new OrderService("O201", 5, new HomeDelivery(100));
        OrderService order2 = new OrderService("O202", 3, new ConvenienceStorePickup(60));
        OrderService order3 = new OrderService("O203", 10, new SelfPickup());

        order1.processOrder();
        order2.processOrder();
        order3.processOrder();
    }
}