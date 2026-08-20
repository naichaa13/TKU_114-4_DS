import java.util.ArrayList;
import java.util.List;

public class RawTypeSafetyDemo {

    // 1. 使用 Raw Type 的範例（會產生 unchecked 警告，且錯誤延後到執行期）
    static void rawTypeExample() {
        // 使用未指定型態的 Raw Type
        List raw = new ArrayList();
        raw.add("Amy");
        raw.add(100); // 放入 Integer，編譯器不會阻止

        try {
            // 試圖將 Integer 強制轉型為 String，導致執行期發生 ClassCastException
            String value = (String) raw.get(1);
            System.out.println(value);
        } catch (ClassCastException exception) {
            System.out.println("raw type error: Integer cannot become String");
        }
    }

    // 2. 正確使用 Generic 的範例（編譯時期安全）
    static void genericExample() {
        List<String> names = new ArrayList<>();
        names.add("Amy");
        names.add("Ben");
        System.out.println(names);
    }

    // 3. 實作變化後的安全示範：改用 List<String> 封鎖不合法的型態
    static void genericFixedExample() {
        List<String> fixedNames = new ArrayList<>();
        fixedNames.add("Amy");
        fixedNames.add("Ben");
        System.out.println("fixed names: " + fixedNames);
    }

    public static void main(String[] args) {
        System.out.println("--- 1. Raw Type 示範 ---");
        rawTypeExample();

        System.out.println("\n--- 2. Generic 示範 ---");
        genericExample();

        System.out.println("\n--- 3. 改良後的 Generic 示範 ---");
        genericFixedExample();
    }
}