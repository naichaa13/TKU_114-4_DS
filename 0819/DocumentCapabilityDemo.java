interface Exportable {
    String export();
}

interface Compressible {
    void compress();
}

class BackupDocument implements Exportable, Compressible {
    private String title;
    private int sizeInMb;

    BackupDocument(String title, int sizeInMb) {
        this.title = title;
        this.sizeInMb = Math.max(1, sizeInMb);
    }

    @Override
    public String export() {
        return "Exporting document: " + title + " (size: " + sizeInMb + "MB)";
    }

    @Override
    public void compress() {
        sizeInMb = sizeInMb / 2;
        System.out.println(title + " compressed. New size: " + sizeInMb + "MB");
    }

    void showInfo() {
        System.out.println("Document Title: " + title + ", Current Size: " + sizeInMb + "MB");
    }
}

public class DocumentCapabilityDemo {
    public static void main(String[] args) {
        BackupDocument document = new BackupDocument("DatabaseBackup", 100);

        // 使用不同 interface reference 指向同一個物件
        Exportable exportable = document;
        Compressible compressible = document;

        // 透過 Exportable reference 只能呼叫 export()
        System.out.println(exportable.export());

        // 透過 Compressible reference 只能呼叫 compress()
        compressible.compress();

        // 再次查看物件狀態
        document.showInfo();
    }
}