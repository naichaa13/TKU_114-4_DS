class DigitalWallet {
    private final String walletId;
    private final String owner;
    private int balance;
    private int transactionCount;

    DigitalWallet(String walletId, String owner) {
        this.walletId = walletId == null || walletId.isBlank() ? "UNKNOWN" : walletId;
        this.owner = owner == null || owner.isBlank() ? "Unknown" : owner;
        this.balance = 0;
        this.transactionCount = 0;
    }

    boolean deposit(int amount) {
        if (amount <= 0) {
            return false;
        }
        balance += amount;
        transactionCount++;
        return true;
    }

    boolean pay(int amount) {
        if (amount <= 0 || amount > balance) {
            return false;
        }
        balance -= amount;
        transactionCount++;
        return true;
    }

    boolean refund(int amount) {
        if (amount <= 0) {
            return false;
        }
        balance += amount;
        transactionCount++;
        return true;
    }

    @Override
    public String toString() {
        return "Wallet[" + walletId + "] Owner: " + owner +
                ", Balance: " + balance + ", Total Transactions: " + transactionCount;
    }
}

public class DigitalWalletSystem {
    public static void main(String[] args) {
        DigitalWallet wallet = new DigitalWallet("W001", "Abby");
        System.out.println("儲值 1000: " + wallet.deposit(1000));
        System.out.println("付款 250: " + wallet.pay(250));
        System.out.println("付款 2000 (餘額不足): " + wallet.pay(2000));
        System.out.println("儲值 -500 (負數): " + wallet.deposit(-500));
        System.out.println("退款 50: " + wallet.refund(50));
        System.out.println("\n--- 最終狀態 ---");
        System.out.println(wallet);
    }
}