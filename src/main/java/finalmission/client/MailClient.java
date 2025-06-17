package finalmission.client;

public interface MailClient {

    void send(String to, String title, String content);
}
