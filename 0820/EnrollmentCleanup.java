import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class EnrollmentCleanup {
    public static void main(String[] args) {
        List<String> rawNames = new ArrayList<>();
        rawNames.add("Amy");
        rawNames.add("Ben");
        rawNames.add(null);
        rawNames.add("Amy");
        rawNames.add("   ");
        rawNames.add("Cara");
        rawNames.add(null);
        rawNames.add("Ben");

        System.out.println("【清理前名單】");
        System.out.println(rawNames);
        System.out.println("原始筆數：" + rawNames.size());
        System.out.println();

        // 使用 Iterator 安全移除不合法資料 (null 或空白字串)
        Iterator<String> iterator = rawNames.iterator();
        while (iterator.hasNext()) {
            String name = iterator.next();
            if (name == null || name.isBlank()) {
                iterator.remove();
            }
        }

        System.out.println("【清理不合法資料後名單】");
        System.out.println(rawNames);
        System.out.println();

        // 使用 Set 找出重複姓名
        Set<String> uniqueNames = new HashSet<>();
        Set<String> duplicateNames = new HashSet<>();

        for (String name : rawNames) {
            // 如果 add 失敗，代表 Set 已經存在該名字，即為重複
            if (!uniqueNames.add(name)) {
                duplicateNames.add(name);
            }
        }

        System.out.println("【重複報告】");
        if (duplicateNames.isEmpty()) {
            System.out.println("名單中沒有重複的姓名。");
        } else {
            System.out.println("發現重複的姓名：" + duplicateNames);
        }
    }
}