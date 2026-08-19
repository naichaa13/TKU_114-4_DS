interface ReportExporter {
    String export(String title, int[] values);

    String getFormatName();
}

class CsvExporter implements ReportExporter {
    @Override
    public String export(String title, int[] values) {
        String data = (values == null) ? "" : intArrayToString(values, ",");
        return "CSV Report [" + title + "] -> " + data;
    }

    @Override
    public String getFormatName() {
        return "csv";
    }

    private String intArrayToString(int[] values, String delimiter) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            sb.append(values[i]);
            if (i < values.length - 1) {
                sb.append(delimiter);
            }
        }
        return sb.toString();
    }
}

class JsonExporter implements ReportExporter {
    @Override
    public String export(String title, int[] values) {
        String data = (values == null) ? "[]" : intArrayToJson(values);
        return "JSON Report {\"title\":\"" + title + "\", \"values\":" + data + "}";
    }

    @Override
    public String getFormatName() {
        return "json";
    }

    private String intArrayToJson(int[] values) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < values.length; i++) {
            sb.append(values[i]);
            if (i < values.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}

class TextExporter implements ReportExporter {
    @Override
    public String export(String title, int[] values) {
        String data = (values == null) ? "no data" : intArrayToString(values, " ");
        return "TEXT Report - " + title + " | Values: " + data;
    }

    @Override
    public String getFormatName() {
        return "text";
    }

    private String intArrayToString(int[] values, String delimiter) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            sb.append(values[i]);
            if (i < values.length - 1) {
                sb.append(delimiter);
            }
        }
        return sb.toString();
    }
}

public class ReportExporterFactory {
    static ReportExporter createExporter(String format) {
        if (format == null) {
            return new TextExporter();
        }

        String lowerFormat = format.trim().toLowerCase();
        if ("csv".equals(lowerFormat)) {
            return new CsvExporter();
        } else if ("json".equals(lowerFormat)) {
            return new JsonExporter();
        }

        // 不支援的 format 或預設均回傳 TextExporter
        return new TextExporter();
    }

    // 只依賴介面，主流程不使用 instanceof
    static void exportReport(ReportExporter exporter, String title, int[] values) {
        String result = exporter.export(title, values);
        System.out.println(result);
    }

    public static void main(String[] args) {
        int[] scores = { 85, 90, 78, 92 };
        int[] emptyValues = null;

        // 測試建立與匯出（包含不支援的格式及 null 邊界條件）
        ReportExporter csv = createExporter("csv");
        ReportExporter json = createExporter("JSON");
        ReportExporter unknown = createExporter("xml"); // 不支援，預設回傳 TextExporter
        ReportExporter nullFormat = createExporter(null);

        exportReport(csv, "Math Scores", scores);
        exportReport(json, "English Scores", scores);
        exportReport(unknown, "Science Scores", emptyValues); // 測試 values 為 null
        exportReport(nullFormat, "History Scores", scores);
    }
}