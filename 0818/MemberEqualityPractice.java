import java.util.Objects;

class LibraryMember {
    private final String memberId;
    private String name;
    private String email;

    LibraryMember(String memberId, String name, String email) {
        this.memberId = memberId == null || memberId.isBlank() ? "UNKNOWN" : memberId;
        this.name = name == null || name.isBlank() ? "Unknown" : name;
        this.email = email == null || email.isBlank() ? "N/A" : email;
    }

    @Override
    public String toString() {
        return "Member{id='" + memberId + "', name='" + name + "', email='" + email + "'}";
    }

    @Override
    public boolean equals(Object other) {
        // 比較參考位址
        if (this == other) {
            return true;
        }
        // 處理 null 以及型態檢查
        if (!(other instanceof LibraryMember member)) {
            return false;
        }
        // 根據業務邏輯，僅比較 memberId
        return Objects.equals(memberId, member.memberId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId);
    }
}

public class MemberEqualityPractice {
    public static void main(String[] args) {
        LibraryMember a = new LibraryMember("M001", "Amy", "amy@email.com");
        LibraryMember b = new LibraryMember("M001", "Amy", "new-email@email.com");
        LibraryMember c = a;

        System.out.println("物件 a: " + a);
        System.out.println("物件 b: " + b);

        System.out.println("\n(a == b): " + (a == b)); // false
        System.out.println("(a.equals(b)): " + a.equals(b)); // true

        System.out.println("(a == c): " + (a == c)); // true
        System.out.println("(a.equals(null)): " + a.equals(null)); // false
    }
}