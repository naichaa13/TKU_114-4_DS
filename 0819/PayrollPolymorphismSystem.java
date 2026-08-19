abstract class Employee {
    private String id;
    private String name;

    Employee(String id, String name) {
        this.id = id;
        this.name = name;
    }

    String getLabel() {
        return id + " " + name;
    }

    abstract int calculatePay();
}

class MonthlyEmployee extends Employee {
    private int monthlySalary;

    MonthlyEmployee(String id, String name, int monthlySalary) {
        super(id, name);
        this.monthlySalary = Math.max(0, monthlySalary);
    }

    @Override
    int calculatePay() {
        return monthlySalary;
    }
}

class HourlyEmployee extends Employee {
    private int hours;
    private int hourlyRate;

    HourlyEmployee(String id, String name, int hours, int hourlyRate) {
        super(id, name);
        this.hours = Math.max(0, hours);
        this.hourlyRate = Math.max(0, hourlyRate);
    }

    @Override
    int calculatePay() {
        return hours * hourlyRate;
    }
}

class SalesEmployee extends Employee {
    private int baseSalary;
    private int salesAmount;
    private int commissionRate;

    SalesEmployee(String id, String name, int baseSalary, int salesAmount, int commissionRate) {
        super(id, name);
        this.baseSalary = Math.max(0, baseSalary);
        this.salesAmount = Math.max(0, salesAmount);
        this.commissionRate = Math.max(0, Math.min(100, commissionRate));
    }

    @Override
    int calculatePay() {
        return baseSalary + salesAmount * commissionRate / 100;
    }
}

public class PayrollPolymorphismSystem {
    public static void main(String[] args) {
        Employee[] employees = {
                new MonthlyEmployee("E101", "Amy", 50000),
                new HourlyEmployee("E102", "Ben", 80, 220),
                new SalesEmployee("E103", "Chris", 30000, 200000, 10),
                new MonthlyEmployee("E104", "Diana", 62000)
        };

        int totalPayroll = 0;
        int maxPay = -1;
        Employee topEarners = null;

        for (Employee employee : employees) {
            int pay = employee.calculatePay();
            totalPayroll += pay;
            System.out.println(employee.getLabel() + " 薪資：" + pay);

            if (pay > maxPay) {
                maxPay = pay;
                topEarners = employee;
            }
        }

        System.out.println("--------------------");
        System.out.println("薪資總額：" + totalPayroll);
        if (topEarners != null) {
            System.out.println("最高薪資員工：" + topEarners.getLabel() + "（$" + maxPay + "）");
        }
    }
}