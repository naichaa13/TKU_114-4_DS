import java.util.ArrayDeque;
import java.util.Deque;

public class BracketMatchingDemo {

    static boolean isBalanced(String expression) {
        return firstErrorIndex(expression) == -1;
    }

    static int firstErrorIndex(String expression) {
        if (expression == null) {
            return 0;
        }

        Deque<Character> stack = new ArrayDeque<>();
        for (int i = 0; i < expression.length(); i++) {
            char symbol = expression.charAt(i);

            if (symbol == '(' || symbol == '[' || symbol == '{') {
                stack.push(symbol);
            } else if (symbol == ')' || symbol == ']' || symbol == '}') {
                // 如果 stack 為空，或者彈出的左括號與當前右括號不匹配，則回傳當前 index i
                if (stack.isEmpty() || !matches(stack.pop(), symbol)) {
                    return i;
                }
            }
        }

        // 如果檢查完後 stack 還有剩，代表還留有左括號，回傳 expression.length()
        if (!stack.isEmpty()) {
            return expression.length();
        }

        return -1; // 全部正確
    }

    static boolean matches(char open, char close) {
        return (open == '(' && close == ')')
                || (open == '[' && close == ']')
                || (open == '{' && close == '}');
    }

    public static void main(String[] args) {
        String[] expressions = {
                "{[()]}", "([)]", "(()", "a + (b * c)", ""
        };

        for (String expression : expressions) {
            int errorIndex = firstErrorIndex(expression);
            System.out.println("expression: \"" + expression + "\" -> firstErrorIndex: " + errorIndex);
        }
    }
}