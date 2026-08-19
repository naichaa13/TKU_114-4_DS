interface MessageSender {
    boolean send(String receiver, String message);
}

class EmailSender implements MessageSender {
    @Override
    public boolean send(String receiver, String message) {
        if (receiver == null || receiver.isBlank() || !receiver.contains("@") ||
                message == null || message.isBlank()) {
            return false;
        }
        System.out.println("EMAIL to " + receiver + "：" + message);
        return true;
    }
}

class SmsSender implements MessageSender {
    @Override
    public boolean send(String receiver, String message) {
        if (receiver == null || receiver.isBlank() ||
                message == null || message.isBlank()) {
            return false;
        }
        System.out.println("SMS to " + receiver + "：" + message);
        return true;
    }
}

class ConsoleSender implements MessageSender {
    @Override
    public boolean send(String receiver, String message) {
        if (receiver == null || receiver.isBlank() ||
                message == null || message.isBlank()) {
            return false;
        }
        System.out.println("CONSOLE " + receiver + "：" + message);
        return true;
    }
}

public class MessageSenderSystem {
    static void notify(MessageSender sender, String receiver, String message) {
        boolean result = sender.send(receiver, message);
        System.out.println("發送結果：" + result);
    }

    public static void main(String[] args) {
        MessageSender email = new EmailSender();
        MessageSender sms = new SmsSender();
        MessageSender console = new ConsoleSender();

        notify(email, "amy@example.com", "Class starts");
        notify(sms, "0912345678", "Meeting at 3 PM");
        notify(console, "B113", "System reboot");

        notify(email, "   ", "Invalid test");
    }
}