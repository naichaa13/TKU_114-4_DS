class Account {
    private final String accountId;
    private final String owner;
    private int balance;

    Account(String accountId, String owner, int initialBalance) {
        this.accountId = accountId == null || accountId.isBlank() ? "UNKNOWN" : accountId;
        this.owner = owner == null || owner.isBlank() ? "Unknown" : owner;
        this.balance = Math.max(0, initialBalance);
    }

    String getAccountId() {
        return accountId;
    }

    int getBalance() {
        return balance;
    }

    boolean deposit(int amount) {
        if (amount <= 0) {
            return false;
        }
        balance += amount;
        return true;
    }

    boolean withdraw(int amount) {
        if (amount <= 0 || amount > balance) {
            return false;
        }
        balance -= amount;
        return true;
    }

    @Override
    public String toString() {
        return "Account[" + accountId + "] Owner: " + owner + ", Balance: $" + balance;
    }
}

class TransferService {
    boolean transfer(Account source, Account target, int amount) {
        // 檢查 null、同物件、金額與餘額
        if (source == null || target == null || source == target || amount <= 0 || source.getBalance() < amount) {
            return false;
        }

        // 執行轉帳：先扣款，再入帳
        // 因為前面已經確保餘額足夠，withdraw 不會失敗
        boolean withdrawn = source.withdraw(amount);
        if (!withdrawn) {
            return false;
        }

        boolean deposited = target.deposit(amount);
        if (!deposited) {
            // 保險機制：若目標入帳失敗，將款項退回來源（通常金融系統會用到）
            source.deposit(amount);
            return false;
        }

        return true;
    }
}

public class AccountTransferService {
    public static void main(String[] args) {
        Account accA = new Account("A001", "UserA", 1000);
        Account accB = new Account("A002", "UserB", 500);
        TransferService service = new TransferService();

        System.out.println("初始狀態:");
        System.out.println(accA);
        System.out.println(accB);

        // 測試成功轉帳
        boolean t1 = service.transfer(accA, accB, 300);
        System.out.println("\n成功轉帳 300 (A -> B): " + t1);
        System.out.println(accA);
        System.out.println(accB);

        // 測試餘額不足
        boolean t2 = service.transfer(accB, accA, 10000);
        System.out.println("\n失敗轉帳 10000 (B 餘額不足): " + t2);

        // 測試同帳戶轉帳
        boolean t3 = service.transfer(accA, accA, 100);
        System.out.println("\n失敗轉帳 (自己轉給自己): " + t3);

        // 測試 null 目標
        boolean t4 = service.transfer(accA, null, 100);
        System.out.println("\n失敗轉帳 (目標為 null): " + t4);

        System.out.println("\n--- 最終狀態 ---");
        System.out.println(accA);
        System.out.println(accB);
    }
}