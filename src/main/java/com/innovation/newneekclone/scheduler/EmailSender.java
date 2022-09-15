package com.innovation.newneekclone.scheduler;

import com.innovation.newneekclone.dto.MailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSender {

    private final JavaMailSender javaMailSender;
    private static final String FROM_ADDRESS = "newneekclone07@gmail.com";
    public void sendMail(MailDto mailDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(FROM_ADDRESS);
        message.setTo(mailDto.getToAddress());
        message.setSubject(mailDto.getTitle());
        message.setText(mailDto.getMessage());

        try {
            javaMailSender.send(message);
        } catch (Exception e) {
            System.out.println("메일이 존재하지 않습니다.");
        }

    }
}
