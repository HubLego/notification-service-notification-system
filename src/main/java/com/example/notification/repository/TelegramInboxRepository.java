package com.example.notification.repository;

import com.example.notification.model.TelegramInbox;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface TelegramInboxRepository extends JpaRepository<TelegramInbox, UUID> {
    boolean existsByKeyAndValue(String key, String value);

    @Query("""
            select n from TelegramInbox n
            where n.processed = false
            order by n.createdAt asc
            """)
    List<TelegramInbox> findBatch(Pageable pageable);
}
