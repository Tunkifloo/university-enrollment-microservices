package com.university.auditservice.controller;

import com.university.auditservice.domain.AuditLog;
import com.university.auditservice.domain.EventType;
import com.university.auditservice.service.AuditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/audit")
@RequiredArgsConstructor
public class AuditController {

    private final AuditService auditService;

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "audit-service");
        response.put("message", "Audit Service está funcionando correctamente");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/logs")
    public ResponseEntity<List<AuditLog>> getAllAuditLogs() {
        log.info("GET /audit/logs - Obteniendo todos los logs de auditoría");
        List<AuditLog> logs = auditService.getAllAuditLogs();
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/logs/event-type/{eventType}")
    public ResponseEntity<List<AuditLog>> getAuditLogsByEventType(
            @PathVariable EventType eventType) {
        log.info("GET /audit/logs/event-type/{} - Obteniendo logs por tipo de evento", eventType);
        List<AuditLog> logs = auditService.getAuditLogsByEventType(eventType);
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/logs/user/{userId}")
    public ResponseEntity<List<AuditLog>> getAuditLogsByUser(
            @PathVariable Long userId) {
        log.info("GET /audit/logs/user/{} - Obteniendo logs por usuario", userId);
        List<AuditLog> logs = auditService.getAuditLogsByUser(userId);
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/logs/email/{email}")
    public ResponseEntity<List<AuditLog>> getAuditLogsByEmail(
            @PathVariable String email) {
        log.info("GET /audit/logs/email/{} - Obteniendo logs por email", email);
        List<AuditLog> logs = auditService.getAuditLogsByEmail(email);
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/logs/date-range")
    public ResponseEntity<List<AuditLog>> getAuditLogsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        log.info("GET /audit/logs/date-range - Obteniendo logs entre {} y {}", start, end);
        List<AuditLog> logs = auditService.getAuditLogsByDateRange(start, end);
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStatistics() {
        log.info("GET /audit/stats - Obteniendo estadísticas de auditoría");

        List<AuditLog> allLogs = auditService.getAllAuditLogs();

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalEvents", allLogs.size());
        stats.put("eventsByType", countByEventType(allLogs));

        if (!allLogs.isEmpty()) {
            stats.put("latestEvent", allLogs.get(allLogs.size() - 1).getTimestamp());
            stats.put("oldestEvent", allLogs.get(0).getTimestamp());
        }

        return ResponseEntity.ok(stats);
    }

    private Map<EventType, Long> countByEventType(List<AuditLog> logs) {
        Map<EventType, Long> counts = new HashMap<>();
        for (AuditLog log : logs) {
            counts.merge(log.getEventType(), 1L, Long::sum);
        }
        return counts;
    }
}