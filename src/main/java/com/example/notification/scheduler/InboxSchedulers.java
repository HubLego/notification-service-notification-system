package com.example.notification.scheduler;

import com.example.notification.config.InboxProperties;
import com.example.notification.model.EmailInbox;
import com.example.notification.model.PushInbox;
import com.example.notification.model.SmsInbox;
import com.example.notification.model.TelegramInbox;
import com.example.notification.repository.EmailInboxRepository;
import com.example.notification.repository.PushInboxRepository;
import com.example.notification.repository.SmsInboxRepository;
import com.example.notification.repository.TelegramInboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class InboxSchedulers {

    private final InboxProperties props;
    private final SmsInboxRepository smsRepo;
    private final EmailInboxRepository emailRepo;
    private final PushInboxRepository pushRepo;
    private final TelegramInboxRepository tgRepo;

    @Scheduled(fixedDelayString = "${inbox.delay-ms}")
    public void processSms() {
        List<SmsInbox> batch = smsRepo.findBatch(PageRequest.of(0, props.getBatchSize()));
        for (SmsInbox m : batch) {
            try {
                log.info("Обработано событие: Key: {}, Payload: {}, topic: {}", m.getKey(), m.getValue(), m.getTopic());
                m.setProcessed(true);
            } catch (Exception e) {
                m.setAttempt(m.getAttempt() + 1);
                log.error("Ошибка обработки sms_inbox: key={}, err={}", m.getKey(), e.toString());
            }
        }
        smsRepo.saveAll(batch);
    }

    @Scheduled(fixedDelayString = "${inbox.delay-ms}")
    public void processEmail() {
        List<EmailInbox> batch = emailRepo.findBatch(PageRequest.of(0, props.getBatchSize()));
        for (EmailInbox m : batch) {
            try {
                log.info("Обработано событие: Key: {}, Payload: {}, topic: {}", m.getKey(), m.getValue(), m.getTopic());
                m.setProcessed(true);
            } catch (Exception e) {
                m.setAttempt(m.getAttempt() + 1);
                log.error("Ошибка обработки email_inbox: key={}, err={}", m.getKey(), e.toString());
            }
        }
        emailRepo.saveAll(batch);
    }

    @Scheduled(fixedDelayString = "${inbox.delay-ms}")
    public void processPush() {
        List<PushInbox> batch = pushRepo.findBatch(PageRequest.of(0, props.getBatchSize()));
        for (PushInbox m : batch) {
            try {
                log.info("Обработано событие: Key: {}, Payload: {}, topic: {}", m.getKey(), m.getValue(), m.getTopic());
                m.setProcessed(true);
            } catch (Exception e) {
                m.setAttempt(m.getAttempt() + 1);
                log.error("Ошибка обработки push_inbox: key={}, err={}", m.getKey(), e.toString());
            }
        }
        pushRepo.saveAll(batch);
    }

    @Scheduled(fixedDelayString = "${inbox.delay-ms}")
    public void processTelegram() {
        List<TelegramInbox> batch = tgRepo.findBatch(PageRequest.of(0, props.getBatchSize()));
        for (TelegramInbox m : batch) {
            try {
                log.info("Обработано событие: Key: {}, Payload: {}, topic: {}", m.getKey(), m.getValue(), m.getTopic());
                m.setProcessed(true);
            } catch (Exception e) {
                m.setAttempt(m.getAttempt() + 1);
                log.error("Ошибка обработки telegram_inbox: key={}, err={}", m.getKey(), e.toString());
            }
        }
        tgRepo.saveAll(batch);
    }
}
