package com.example.notification.repository;

import com.example.notification.model.EmailInbox;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface EmailInboxRepository extends JpaRepository<EmailInbox, UUID> {
    boolean existsByKeyAndValue(String key, String value);

    @Query("""
            select n from EmailInbox n
            where n.processed = false
            order by n.createdAt asc
            """)
    List<EmailInbox> findBatch(Pageable pageable);
}
