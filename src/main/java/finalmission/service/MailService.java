package finalmission.service;

import finalmission.dto.MailRequestDto;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private final JavaMailSender javaMailSender;

    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendSimpleMailMessage(MailRequestDto mailRequestDto) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(mailRequestDto.sendTo());
        simpleMailMessage.setSubject(mailRequestDto.subject());
        simpleMailMessage.setText(mailRequestDto.text());
        javaMailSender.send(simpleMailMessage);
    }
}
