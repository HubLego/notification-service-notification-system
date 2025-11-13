package com.example.notification.repository;

import com.example.notification.model.PushInbox;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PushInboxRepository extends JpaRepository<PushInbox, UUID> {
    boolean existsByKeyAndValue(String key, String value);

    @Query("""
            select n from PushInbox n
            where n.processed = false
            order by n.createdAt asc
            """)
    List<PushInbox> findBatch(Pageable pageable);
}
