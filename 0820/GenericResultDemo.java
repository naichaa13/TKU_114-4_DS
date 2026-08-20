class Result<T> {
    private final boolean success;
    private final String message;
    private final T data;

    Result(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    boolean isSuccess() {
        return success;
    }

    String getMessage() {
        return message;
    }

    T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "success=" + success + ", message=" + message + ", data=" + data;
    }
}

public class GenericResultDemo {
    public static void main(String[] args) {
        Result<String> stringResult = new Result<>(true, "OK", "Java Generics");
        Result<Integer> integerResult = new Result<>(false, "Not Found", null);

        if (stringResult.isSuccess()) {
            String content = stringResult.getData();
            System.out.println("字串資料：" + content.toUpperCase());
        }

        if (!integerResult.isSuccess()) {
            System.out.println("錯誤訊息：" + integerResult.getMessage());
            System.out.println("資料是否為空：" + (integerResult.getData() == null));
        }

        System.out.println(stringResult);
        System.out.println(integerResult);
    }
}