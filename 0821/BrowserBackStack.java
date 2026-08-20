import java.util.ArrayDeque;
import java.util.Deque;

class Browser {
    private final Deque<String> history = new ArrayDeque<>();
    private String currentPage = null;

    // 造訪新網頁
    void visit(String url) {
        if (url == null || url.isBlank()) {
            return;
        }
        // 如果原本有當前頁面，先把它推入歷史堆疊中作為上一頁
        if (currentPage != null) {
            history.push(currentPage);
        }
        currentPage = url;
        System.out.println("造訪網頁：" + currentPage);
    }

    // 返回上一頁
    void back() {
        if (history.isEmpty()) {
            System.out.println("返回失敗：目前已經是第一頁，沒有上一頁記錄。");
            return;
        }
        // 從堆疊頂端彈出最近的歷史頁面作為當前頁面
        String previousPage = history.pop();
        System.out.println("執行返回，從 [" + currentPage + "] 回到 [" + previousPage + "]");
        currentPage = previousPage;
    }

    // 取得當前網頁
    String current() {
        return currentPage == null ? "無目前網頁 (首頁)" : currentPage;
    }
}

public class BrowserBackStack {
    public static void main(String[] args) {
        Browser browser = new Browser();

        // 連續測試至少五個操作（包含空 Stack 測試、造訪與返回）
        System.out.println("--- 測試開始 ---");

        // 在空堆疊時試圖返回（測試空 Stack 防呆，不應丟出例外）
        browser.back();

        // 造訪第一個頁面
        browser.visit("home.html");
        System.out.println("目前頁面：" + browser.current());

        // 造訪第二個頁面
        browser.visit("products.html");

        // 造訪第三個頁面
        browser.visit("detail.html");
        System.out.println("目前頁面：" + browser.current());

        // 執行返回（回到 products.html）
        browser.back();
        System.out.println("目前頁面：" + browser.current());

        // 再次返回（回到 home.html）與造訪新頁面
        browser.back();
        browser.visit("contact.html");
        System.out.println("最終當前頁面：" + browser.current());
    }
}