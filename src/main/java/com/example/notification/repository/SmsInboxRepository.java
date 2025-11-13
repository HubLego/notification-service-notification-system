package com.example.notification.repository;

import com.example.notification.model.SmsInbox;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface SmsInboxRepository extends JpaRepository<SmsInbox, UUID> {
    boolean existsByKeyAndValue(String key, String value);

    @Query("""
            select n from SmsInbox n
            where n.processed = false
            order by n.createdAt asc
            """)
    List<SmsInbox> findBatch(Pageable pageable);
}
