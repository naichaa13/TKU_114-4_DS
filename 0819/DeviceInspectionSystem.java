abstract class Device {
    private String name;

    Device(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }

    abstract void runDiagnostic();
}

class Laptop extends Device {
    Laptop(String name) {
        super(name);
    }

    @Override
    void runDiagnostic() {
        System.out.println("Laptop " + getName() + " diagnostic: Battery and CPU normal.");
    }
}

class Printer extends Device {
    Printer(String name) {
        super(name);
    }

    @Override
    void runDiagnostic() {
        System.out.println("Printer " + getName() + " diagnostic: Ink levels checked.");
    }

    void cleanPrintHead() {
        System.out.println("-> Cleaning print head for " + getName() + "...");
    }
}

class Router extends Device {
    Router(String name) {
        super(name);
    }

    @Override
    void runDiagnostic() {
        System.out.println("Router " + getName() + " diagnostic: Network connection stable.");
    }
}

public class DeviceInspectionSystem {
    public static void main(String[] args) {
        Device[] devices = {
                new Laptop("MacBook"),
                new Printer("HP LaserJet"),
                new Router("ASUS Wi-Fi"),
                new Printer("Epson Inkjet")
        };

        for (Device device : devices) {
            // 每個物件都以 polymorphism 執行 runDiagnostic()
            device.runDiagnostic();

            if (device instanceof Printer printer) {
                printer.cleanPrintHead();
            }

            System.out.println("--------------------");
        }
    }
}