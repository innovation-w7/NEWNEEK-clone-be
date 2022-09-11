package com.innovation.newneekclone.scheduler;

import com.innovation.newneekclone.dto.MailDto;
import com.innovation.newneekclone.entity.News;
import com.innovation.newneekclone.entity.Subscription;
import com.innovation.newneekclone.repository.NewsRepository;
import com.innovation.newneekclone.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MailScheduler {

    private final SubscriptionRepository subscriptionRepository;
    private final NewsRepository newsRepository;
    private final EmailSender emailSender;

    @Transactional
    @Scheduled(cron = "0 0 7 * * *") // 매일 아침 7시
    public void sendNewsEmail() {
        List<Subscription> subscribers = subscriptionRepository.findAll();
        for (Subscription subscriber : subscribers) {
            String email = subscriber.getEmail();
            String nickname = subscriber.getNickname();
            Long lastSentNewsId = subscriber.getLastSentNewsId();

            News news = newsRepository.findById(++lastSentNewsId)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 뉴스입니다."));

            subscriber.updateLastSentNewsId();

            MailDto mailDto = new MailDto(email, news.getTitle(), news.getContent());
            emailSender.sendMail(mailDto);
        }
        System.out.println("Sending Success");
    }
}
