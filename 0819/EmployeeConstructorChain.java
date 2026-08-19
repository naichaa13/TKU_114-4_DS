abstract class EmployeeBase {
    private String id;
    private String name;

    EmployeeBase(String id, String name) {
        this.id = id;
        this.name = name;
        System.out.println("EmployeeBase constructor: " + id + " " + name);
    }

    String getLabel() {
        return id + " " + name;
    }

    abstract int calculatePay();
}

class FullTimeEmployee extends EmployeeBase {
    private int monthlySalary;

    FullTimeEmployee(String id, String name, int monthlySalary) {
        super(id, name);
        this.monthlySalary = Math.max(0, monthlySalary);
        System.out.println("FullTimeEmployee constructor: salary=" + this.monthlySalary);
    }

    @Override
    int calculatePay() {
        return monthlySalary;
    }
}

class PartTimeEmployee extends EmployeeBase {
    private int hours;
    private int hourlyRate;

    PartTimeEmployee(String id, String name, int hours, int hourlyRate) {
        super(id, name);
        this.hours = Math.max(0, hours);
        this.hourlyRate = Math.max(0, hourlyRate);
        System.out.println("PartTimeEmployee constructor: hours=" + this.hours + ", rate=" + this.hourlyRate);
    }

    @Override
    int calculatePay() {
        return hours * hourlyRate;
    }
}

public class EmployeeConstructorChain {
    public static void main(String[] args) {
        System.out.println("--- 建立 FullTimeEmployee 物件 ---");
        EmployeeBase fullTime = new FullTimeEmployee("E101", "Amy", -50000);
        System.out.println(fullTime.getLabel() + " 薪資：" + fullTime.calculatePay());

        System.out.println("\n--- 建立 PartTimeEmployee 物件 ---");
        EmployeeBase partTime = new PartTimeEmployee("E102", "Ben", 40, -200);
        System.out.println(partTime.getLabel() + " 薪資：" + partTime.calculatePay());
    }
}