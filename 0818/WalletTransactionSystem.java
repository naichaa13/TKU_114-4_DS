final class WalletTransaction {
    private final int sequence;
    private final String type;
    private final int amount;
    private final int balanceAfter;

    WalletTransaction(int sequence, String type, int amount, int balanceAfter) {
        this.sequence = sequence;
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
    }

    @Override
    public String toString() {
        return sequence + " " + type + " " + amount
                + " balance=" + balanceAfter;
    }
}

class DigitalWallet {
    private final String walletId;
    private final String owner;
    private int balance;
    private final WalletTransaction[] transactions;
    private int transactionCount;

    DigitalWallet(String walletId, String owner, int historyCapacity) {
        this.walletId = walletId == null || walletId.isBlank()
                ? "UNKNOWN"
                : walletId;
        this.owner = owner == null || owner.isBlank() ? "Unknown" : owner;
        this.balance = 0;
        this.transactions = new WalletTransaction[Math.max(1, historyCapacity)];
        this.transactionCount = 0;
    }

    boolean deposit(int amount) {
        if (amount <= 0 || transactionCount >= transactions.length) {
            return false;
        }
        balance += amount;
        record("DEPOSIT", amount);
        return true;
    }

    boolean pay(int amount) {
        if (amount <= 0 || amount > balance
                || transactionCount >= transactions.length) {
            return false;
        }
        balance -= amount;
        record("PAY", amount);
        return true;
    }

    boolean refund(int amount) {
        if (amount <= 0 || transactionCount >= transactions.length) {
            return false;
        }
        balance += amount;
        record("REFUND", amount);
        return true;
    }

    // 新增：轉帳功能
    boolean transferTo(DigitalWallet target, int amount) {
        // 檢查目標是否存在、是否為自己、金額是否合法
        if (target == null || this == target || amount <= 0 || amount > balance) {
            return false;
        }
        // 檢查雙方是否有足夠空間記錄交易
        if (this.transactionCount >= this.transactions.length ||
                target.transactionCount >= target.transactions.length) {
            return false;
        }

        // 執行轉帳
        this.balance -= amount;
        this.record("TRANSFER_OUT", amount);

        target.balance += amount;
        target.record("TRANSFER_IN", amount);

        return true;
    }

    private void record(String type, int amount) {
        transactions[transactionCount] = new WalletTransaction(
                transactionCount + 1, type, amount, balance);
        transactionCount++;
    }

    void printStatement() {
        System.out.println(walletId + " owner=" + owner
                + " balance=" + balance);
        for (int i = 0; i < transactionCount; i++) {
            System.out.println(transactions[i]);
        }
    }
}

public class WalletTransactionSystem {
    public static void main(String[] args) {
        // 1. 建立兩個錢包物件
        DigitalWallet amy = new DigitalWallet("W001", "Amy", 5);
        DigitalWallet ben = new DigitalWallet("W002", "Ben", 5);

        // 2. 原有的測試情境 (Amy 的錢包)
        System.out.println("--- Amy 的操作 ---");
        System.out.println("deposit=" + amy.deposit(1000));
        System.out.println("pay 250=" + amy.pay(250));
        System.out.println("pay 900=" + amy.pay(900)); // 這會失敗，因為餘額不足
        System.out.println("refund=" + amy.refund(50));

        // 3. 新增的轉帳測試
        System.out.println("\n--- 轉帳測試 ---");
        // Amy 轉 100 給 Ben
        boolean success = amy.transferTo(ben, 100);
        System.out.println("Amy 轉帳給 Ben 100: " + success);

        // 4. 輸出最終狀態
        System.out.println("\n--- 最終對帳單 ---");
        amy.printStatement();
        ben.printStatement();
    }
}