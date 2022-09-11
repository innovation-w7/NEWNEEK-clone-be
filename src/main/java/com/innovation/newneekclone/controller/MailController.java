package com.innovation.newneekclone.controller;

import com.innovation.newneekclone.dto.MailDto;
import com.innovation.newneekclone.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class MailController {
    private final MailService mailService;

    @PostMapping("/mail")
    public void sendMail(@RequestBody MailDto mailDto) {
        mailService.sendMail(mailDto);
    }
}
