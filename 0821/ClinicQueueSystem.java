import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

class Patient {
    private final String medicalId;
    private final String name;
    private final int age;

    Patient(String medicalId, String name, int age) {
        this.medicalId = medicalId;
        this.name = name;
        this.age = age;
    }

    String getMedicalId() {
        return medicalId;
    }

    String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "病歷號=" + medicalId + ", 姓名=" + name + ", 年齡=" + age;
    }
}

class ClinicQueue {
    private final Deque<Patient> waitingQueue = new ArrayDeque<>();
    private final List<Patient> completedList = new ArrayList<>();

    // 一般掛號
    boolean register(Patient patient) {
        if (patient == null) {
            return false;
        }
        // 檢查是否已有相同病歷號在排隊中
        for (Patient p : waitingQueue) {
            if (p.getMedicalId().equals(patient.getMedicalId())) {
                System.out.println("掛號失敗：病歷號 " + patient.getMedicalId() + " 已經在等候佇列中。");
                return false;
            }
        }
        waitingQueue.offerLast(patient);
        System.out.println("掛號成功：" + patient);
        return true;
    }

    // 取消指定病歷號
    boolean cancel(String medicalId) {
        if (medicalId == null || medicalId.isBlank()) {
            return false;
        }
        Iterator<Patient> iterator = waitingQueue.iterator();
        while (iterator.hasNext()) {
            Patient p = iterator.next();
            if (p.getMedicalId().equals(medicalId)) {
                iterator.remove();
                System.out.println("已成功取消病歷號 [" + medicalId + "] 的掛號。");
                return true;
            }
        }
        System.out.println("取消失敗：找不到病歷號 [" + medicalId + "] 的等候紀錄。");
        return false;
    }

    // 叫號
    Patient callNext() {
        Patient patient = waitingQueue.pollFirst();
        if (patient != null) {
            completedList.add(patient);
            System.out.println("【叫號】請 [" + patient.getMedicalId() + " " + patient.getName() + "] 進入診間看診。");
        } else {
            System.out.println("【叫號】目前候診室無人等候。");
        }
        return patient;
    }

    // 查看下一位 (Peek 隊首，不移除)
    Patient peekNext() {
        return waitingQueue.peekFirst();
    }

    void printCompletedList() {
        System.out.println("--- 當日完成看診清單 (共 " + completedList.size() + " 人) ---");
        for (int i = 0; i < completedList.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + completedList.get(i));
        }
    }

    // 顯示目前候診狀態
    void printWaitingQueue() {
        System.out.println("--- 目前候診佇列 (等待人數: " + waitingQueue.size() + ") ---");
        if (waitingQueue.isEmpty()) {
            System.out.println("  (目前無等候病患)");
        } else {
            for (Patient p : waitingQueue) {
                System.out.println("  - " + p);
            }
        }
    }
}

public class ClinicQueueSystem {
    public static void main(String[] args) {
        ClinicQueue clinic = new ClinicQueue();

        System.out.println("=== 診所掛號系統測試 ===");

        // 測試空佇列叫號與查看
        clinic.callNext();
        System.out.println("下一位：" + clinic.peekNext());
        System.out.println();

        // 進行一般掛號 (FIFO 順序測試)
        clinic.register(new Patient("P101", "Amy", 25));
        clinic.register(new Patient("P102", "Ben", 30));
        clinic.register(new Patient("P103", "Cara", 22));
        clinic.register(new Patient("P101", "Amy 重複掛號測試", 25)); // 應被拒絕
        System.out.println();

        clinic.printWaitingQueue();
        System.out.println("下一位候診：" + clinic.peekNext());
        System.out.println();

        // 取消指定病歷號 (取消中間的 P102 Ben)
        clinic.cancel("P102");
        clinic.cancel("P999"); // 取消不存在的號碼
        System.out.println();

        clinic.printWaitingQueue();
        System.out.println();

        // 叫號看診
        clinic.callNext();
        clinic.callNext();
        clinic.callNext(); // 測試候診室無人時叫號
        System.out.println();

        clinic.printCompletedList();
    }
}