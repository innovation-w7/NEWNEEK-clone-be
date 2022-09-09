package com.innovation.newneekclone.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MailScheduler {

    @Scheduled(cron = "0 0 7 * * *") // 아침 7시
    public void sendNewsEmail() {


    }
}
