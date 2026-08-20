import java.util.ArrayDeque;
import java.util.Deque;

class TextEditor {
    private final Deque<String> undoStack = new ArrayDeque<>();
    private final Deque<String> redoStack = new ArrayDeque<>();
    private String currentState = "（空白文件）";

    // 新增操作：執行新動作時，必須清空 redo stack
    void executeAction(String action) {
        if (action == null || action.isBlank()) {
            return;
        }
        // 將當前的狀態推入 undo stack
        undoStack.push(currentState);
        currentState = action;

        // 新增操作後，清空 redo stack
        redoStack.clear();

        System.out.println("執行新操作 -> [" + currentState + "]");
        printStatus();
    }

    // Undo 操作：將當前狀態移到 redo stack，並恢復上一個 undo 狀態
    void undo() {
        if (undoStack.isEmpty()) {
            System.out.println("Undo 失敗：已經沒有可以復原的操作。");
            return;
        }
        // 將當前狀態壓入 redo stack
        redoStack.push(currentState);
        // 從 undo stack 彈出上一個狀態
        currentState = undoStack.pop();

        System.out.println("執行 Undo 復原");
        printStatus();
    }

    // Redo 操作：將當前狀態移回 undo stack，並恢復 redo 狀態
    void redo() {
        if (redoStack.isEmpty()) {
            System.out.println("Redo 失敗：已經沒有可以重做的操作。");
            return;
        }
        // 將當前狀態壓入 undo stack
        undoStack.push(currentState);
        // 從 redo stack 彈出要重做的狀態
        currentState = redoStack.pop();

        System.out.println("執行 Redo 重做");
        printStatus();
    }

    void printStatus() {
        System.out.println("  [當前狀態] 內容: " + currentState);
        System.out.println("  [Undo Stack 歷程] " + undoStack);
        System.out.println("  [Redo Stack 歷程] " + redoStack);
        System.out.println("----------------------------------------");
    }
}

public class TextEditorHistory {
    public static void main(String[] args) {
        TextEditor editor = new TextEditor();
        System.out.println("--- 文字編輯器 Undo/Redo 系統測試 ---");
        editor.printStatus();

        // 測試 1：連續新增操作
        editor.executeAction("輸入：Hello");
        editor.executeAction("輸入：Hello World");
        editor.executeAction("刪除 World");

        // 在空 stack 時試圖 undo / redo
        System.out.println(">>> 嘗試在剛開始時測試連續 Undo 與 Redo");
        editor.undo(); // 回到 "輸入：Hello World"
        editor.undo(); // 回到 "輸入：Hello"
        editor.undo(); // 回到 "（空白文件）"

        // 此時 undo 已空，再次 undo 應被防呆攔截
        editor.undo();
        System.out.println();

        // 執行 Redo
        System.out.println(">>> 測試 Redo 重做");
        editor.redo(); // 重做回到 "輸入：Hello"
        editor.redo(); // 重做回到 "輸入：Hello World"

        // 在中間插入新操作，驗證 redo stack 是否被清空
        System.out.println(">>> 在有 redo 紀錄時執行新操作（應清空 Redo）");
        editor.executeAction("插入標題：Java Guide");

        // 嘗試 Redo（此時 redo 應該已被清空，無法重做）
        editor.redo();
    }
}