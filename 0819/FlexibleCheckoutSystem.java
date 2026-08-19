interface PricingPolicy {
    int finalPrice(int originalPrice);
}

class StandardPricing implements PricingPolicy {
    @Override
    public int finalPrice(int originalPrice) {
        return Math.max(0, originalPrice);
    }
}

class VipPricing implements PricingPolicy {
    @Override
    public int finalPrice(int originalPrice) {
        return Math.max(0, originalPrice) * 85 / 100;
    }
}

// 新增：滿 2000 折 300 優惠策略
class ThresholdPricing implements PricingPolicy {
    @Override
    public int finalPrice(int originalPrice) {
        int safePrice = Math.max(0, originalPrice);
        if (safePrice >= 2000) {
            return safePrice - 300;
        }
        return safePrice;
    }
}

interface NotificationChannel {
    boolean send(String receiver, String message);
}

class EmailChannel implements NotificationChannel {
    @Override
    public boolean send(String receiver, String message) {
        if (receiver == null || !receiver.contains("@")) {
            return false;
        }
        System.out.println("EMAIL " + receiver + " -> " + message);
        return true;
    }
}

class SmsChannel implements NotificationChannel {
    @Override
    public boolean send(String receiver, String message) {
        if (receiver == null || receiver.isBlank()) {
            return false;
        }
        System.out.println("SMS " + receiver + " -> " + message);
        return true;
    }
}

class ConsoleChannel implements NotificationChannel {
    @Override
    public boolean send(String receiver, String message) {
        System.out.println("CONSOLE " + receiver + " -> " + message);
        return true;
    }
}

// 封裝結帳結果的類別
class CheckoutResult {
    private String orderId;
    private int originalPrice;
    private int finalPrice;
    private boolean notificationStatus;

    CheckoutResult(String orderId, int originalPrice, int finalPrice, boolean notificationStatus) {
        this.orderId = orderId;
        this.originalPrice = originalPrice;
        this.finalPrice = finalPrice;
        this.notificationStatus = notificationStatus;
    }

    @Override
    public String toString() {
        return "訂單編號: " + orderId +
                " | 原價: " + originalPrice +
                " | 實付: " + finalPrice +
                " | 通知狀態: " + (notificationStatus ? "成功" : "失敗");
    }
}

class CheckoutService {
    private PricingPolicy pricing;
    private NotificationChannel channel;

    CheckoutService(PricingPolicy pricing, NotificationChannel channel) {
        this.pricing = pricing;
        this.channel = channel;
    }

    // checkout() 回傳 CheckoutResult 物件
    CheckoutResult checkout(String orderId, int originalPrice, String receiver) {
        if (orderId == null || orderId.isBlank() || originalPrice < 0) {
            return new CheckoutResult(orderId, originalPrice, 0, false);
        }

        int amount = pricing.finalPrice(originalPrice);
        boolean status = channel.send(receiver, "order=" + orderId + ", amount=" + amount);

        return new CheckoutResult(orderId, originalPrice, amount, status);
    }
}

public class FlexibleCheckoutSystem {
    public static void main(String[] args) {
        PricingPolicy standard = new StandardPricing();
        PricingPolicy vip = new VipPricing();
        PricingPolicy threshold = new ThresholdPricing();

        NotificationChannel email = new EmailChannel();
        NotificationChannel sms = new SmsChannel();
        NotificationChannel console = new ConsoleChannel();

        // 測試至少六種不同的 Pricing / Channel 組合
        CheckoutService service1 = new CheckoutService(standard, email);
        CheckoutService service2 = new CheckoutService(standard, sms);
        CheckoutService service3 = new CheckoutService(vip, console);
        CheckoutService service4 = new CheckoutService(vip, email);
        CheckoutService service5 = new CheckoutService(threshold, sms);
        CheckoutService service6 = new CheckoutService(threshold, console);

        System.out.println("--- 執行六種組合測試 ---");
        System.out.println(service1.checkout("O001", 1000, "amy@example.com"));
        System.out.println(service2.checkout("O002", 1500, "0912345678"));
        System.out.println(service3.checkout("O003", 2500, "counter"));
        System.out.println(service4.checkout("O004", 3000, "ben@example.com"));
        System.out.println(service5.checkout("O005", 2200, "0987654321"));
        System.out.println(service6.checkout("O006", 1800, "counter"));
    }
}