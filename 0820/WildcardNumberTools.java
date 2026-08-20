import java.util.ArrayList;
import java.util.List;

public class WildcardNumberTools {

    static double average(List<? extends Number> values) {
        if (values == null || values.isEmpty()) {
            return 0.0;
        }
        double total = 0.0;
        for (Number value : values) {
            total += value.doubleValue();
        }
        return total / values.size();
    }

    static double maximum(List<? extends Number> values) {
        if (values == null || values.isEmpty()) {
            return Double.NaN;
        }
        double max = values.get(0).doubleValue();
        for (Number value : values) {
            if (value.doubleValue() > max) {
                max = value.doubleValue();
            }
        }
        return max;
    }

    static void addRange(List<? super Integer> target, int start, int end) {
        if (target == null || start > end) {
            return;
        }
        for (int i = start; i <= end; i++) {
            target.add(i);
        }
    }

    public static void main(String[] args) {
        List<Integer> intList = new ArrayList<>(List.of(10, 20, 30));
        List<Double> doubleList = new ArrayList<>(List.of(1.5, 2.5, 5.0));
        List<Number> numberList = new ArrayList<>();

        System.out.println("intList 平均值：" + average(intList));
        System.out.println("doubleList 平均值：" + average(doubleList));
        System.out.println("空 list 平均值：" + average(new ArrayList<>()));

        System.out.println("intList 最大值：" + maximum(intList));
        System.out.println("doubleList 最大值：" + maximum(doubleList));
        System.out.println("空 list 最大值：" + maximum(new ArrayList<>()));

        addRange(intList, 31, 33);
        addRange(numberList, 1, 3);

        System.out.println("addRange 後 intList：" + intList);
        System.out.println("addRange 後 numberList：" + numberList);

        addRange(intList, 10, 5);
        System.out.println("不合法範圍 (10 to 5) 後 intList 大小是否維持不變：" + intList.size());
    }
}