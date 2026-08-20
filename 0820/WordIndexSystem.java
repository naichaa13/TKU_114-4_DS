import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WordIndexSystem {
    public static void main(String[] args) {
        String[] sentences = {
                "Java is fun, and Java is powerful.",
                "Data structures and algorithms in Java.",
                "Fun with data structures!"
        };

        // 宣告 Map 統計次數、Set 保存不重複單字
        Map<String, Integer> wordCounts = new HashMap<>();
        Set<String> uniqueWords = new HashSet<>();

        // 處理每一句句子
        for (String sentence : sentences) {
            if (sentence == null || sentence.isBlank()) {
                continue;
            }

            // 忽略英文大小寫、句點、逗號等標點符號（將其取代為空字串或空白）
            String cleaned = sentence.toLowerCase().replaceAll("[,\\.!]", "");

            // 透過空白分割出單字
            String[] words = cleaned.split("\\s+");

            for (String word : words) {
                if (!word.isBlank()) {
                    // 加入不重複 Set
                    uniqueWords.add(word);
                    // 統計次數 Map
                    wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);
                }
            }
        }

        System.out.println("【不重複單字 (Set)】");
        System.out.println(uniqueWords);
        System.out.println();

        System.out.println("【單字出現次數統計 (Map)】");
        System.out.println(wordCounts);
        System.out.println();

        System.out.println("【出現至少兩次的單字】");
        for (Map.Entry<String, Integer> entry : wordCounts.entrySet()) {
            if (entry.getValue() >= 2) {
                System.out.println("  - " + entry.getKey() + " (出現 " + entry.getValue() + " 次)");
            }
        }
    }
}