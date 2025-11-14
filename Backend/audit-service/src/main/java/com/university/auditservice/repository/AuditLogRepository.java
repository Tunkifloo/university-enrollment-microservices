package com.university.auditservice.repository;

import com.university.auditservice.domain.AuditLog;
import com.university.auditservice.domain.EventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    List<AuditLog> findByEventType(EventType eventType);

    List<AuditLog> findByUserId(Long userId);

    List<AuditLog> findByUserEmail(String userEmail);

    @Query("SELECT a FROM AuditLog a WHERE a.timestamp BETWEEN :start AND :end")
    List<AuditLog> findByTimestampBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT a FROM AuditLog a WHERE a.entityType = :entityType AND a.entityId = :entityId")
    List<AuditLog> findByEntity(String entityType, Long entityId);
}