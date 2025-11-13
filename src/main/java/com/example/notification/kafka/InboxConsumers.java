package com.example.notification.kafka;

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
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class InboxConsumers {

    private final SmsInboxRepository smsRepo;
    private final EmailInboxRepository emailRepo;
    private final PushInboxRepository pushRepo;
    private final TelegramInboxRepository tgRepo;

    @Transactional
    @KafkaListener(topics = "sms-events", groupId = "sms-inbox-group")
    public void onSms(ConsumerRecord<String, String> rec) {
        if (smsRepo.existsByKeyAndValue(rec.key(), rec.value())) return;
        smsRepo.save(SmsInbox.builder()
                .topic(rec.topic())
                .key(rec.key())
                .value(rec.value())
                .processed(false)
                .attempt(1)
                .build());
        log.info("Сохранено в sms_inbox: key={}, topic={}", rec.key(), rec.topic());
    }

    @Transactional
    @KafkaListener(topics = "email-events", groupId = "email-inbox-group")
    public void onEmail(ConsumerRecord<String, String> rec) {
        if (emailRepo.existsByKeyAndValue(rec.key(), rec.value())) return;
        emailRepo.save(EmailInbox.builder()
                .topic(rec.topic())
                .key(rec.key())
                .value(rec.value())
                .processed(false)
                .attempt(1)
                .build());
        log.info("Сохранено в email_inbox: key={}, topic={}", rec.key(), rec.topic());
    }

    @Transactional
    @KafkaListener(topics = "push-events", groupId = "push-inbox-group")
    public void onPush(ConsumerRecord<String, String> rec) {
        if (pushRepo.existsByKeyAndValue(rec.key(), rec.value())) return;
        pushRepo.save(PushInbox.builder()
                .topic(rec.topic())
                .key(rec.key())
                .value(rec.value())
                .processed(false)
                .attempt(1)
                .build());
        log.info("Сохранено в push_inbox: key={}, topic={}", rec.key(), rec.topic());
    }

    @Transactional
    @KafkaListener(topics = "telegram-events", groupId = "telegram-inbox-group")
    public void onTelegram(ConsumerRecord<String, String> rec) {
        if (tgRepo.existsByKeyAndValue(rec.key(), rec.value())) return;
        tgRepo.save(TelegramInbox.builder()
                .topic(rec.topic())
                .key(rec.key())
                .value(rec.value())
                .processed(false)
                .attempt(1)
                .build());
        log.info("Сохранено в telegram_inbox: key={}, topic={}", rec.key(), rec.topic());
    }
}
