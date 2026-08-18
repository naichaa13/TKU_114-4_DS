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

    int getSequence() {
        return sequence;
    }

    String getType() {
        return type;
    }

    int getAmount() {
        return amount;
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
        this.walletId = walletId == null || walletId.isBlank() ? "UNKNOWN" : walletId;
        this.owner = owner == null || owner.isBlank() ? "Unknown" : owner;
        this.balance = 0;
        this.transactions = new WalletTransaction[Math.max(1, historyCapacity)];
        this.transactionCount = 0;
    }

    boolean deposit(int amount) {
        // 檢查金額與陣列是否已滿
        if (amount <= 0 || transactionCount >= transactions.length) {
            return false;
        }
        balance += amount;
        record("DEPOSIT", amount);
        return true;
    }

    boolean pay(int amount) {
        if (amount <= 0 || amount > balance || transactionCount >= transactions.length) {
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

    boolean transferTo(DigitalWallet target, int amount) {
        // 檢查條件：目標有效、非自己、金額合法、雙方餘額與紀錄空間足夠
        if (target == null || this == target || amount <= 0 || amount > balance
                || transactionCount >= transactions.length
                || target.transactionCount >= target.transactions.length) {
            return false;
        }

        // 執行轉帳與雙方紀錄
        this.balance -= amount;
        this.record("TRANSFER_OUT", amount);

        target.balance += amount;
        target.record("TRANSFER_IN", amount);
        return true;
    }

    // 根據序號尋找交易，找不到回傳 null
    WalletTransaction findTransaction(int sequence) {
        for (int i = 0; i < transactionCount; i++) {
            if (transactions[i].getSequence() == sequence) {
                return transactions[i];
            }
        }
        return null;
    }

    // 計算指定交易類型的總金額
    int totalByType(String type) {
        int total = 0;
        for (int i = 0; i < transactionCount; i++) {
            if (transactions[i].getType().equals(type)) {
                total += transactions[i].getAmount();
            }
        }
        return total;
    }

    private void record(String type, int amount) {
        transactions[transactionCount] = new WalletTransaction(
                transactionCount + 1, type, amount, balance);
        transactionCount++;
    }

    void printStatement() {
        System.out.println(walletId + " owner=" + owner + " balance=" + balance);
        for (int i = 0; i < transactionCount; i++) {
            System.out.println(transactions[i]);
        }
    }
}

public class WalletHistoryManager {
    public static void main(String[] args) {
        DigitalWallet amy = new DigitalWallet("W001", "Amy", 3);
        DigitalWallet ben = new DigitalWallet("W002", "Ben", 3);

        System.out.println("deposit= " + amy.deposit(1000));
        System.out.println("pay= " + amy.pay(200));
        System.out.println("transfer= " + amy.transferTo(ben, 300));

        // 測試容量限制
        System.out.println("deposit overflow test= " + amy.deposit(500));

        System.out.println("\n--- 尋找交易測試 ---");
        System.out.println("尋找序號 2: " + amy.findTransaction(2));
        System.out.println("尋找不存在的序號 99: " + amy.findTransaction(99));

        System.out.println("\n--- 依類型計算總金額 ---");
        System.out.println("Amy PAY 總額: " + amy.totalByType("PAY"));
        System.out.println("Amy TRANSFER_OUT 總額: " + amy.totalByType("TRANSFER_OUT"));

        System.out.println("\n--- Amy Statement ---");
        amy.printStatement();
        System.out.println("\n--- Ben Statement ---");
        ben.printStatement();
    }
}