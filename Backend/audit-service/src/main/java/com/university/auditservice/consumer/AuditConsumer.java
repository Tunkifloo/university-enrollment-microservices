package com.university.auditservice.consumer;

import com.university.auditservice.dto.AuditEvent;
import com.university.auditservice.service.AuditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuditConsumer {

    private final AuditService auditService;

    @KafkaListener(
            topics = "${kafka.topics.audit}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consumeAuditEvent(
            @Payload AuditEvent event,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
            @Header(KafkaHeaders.OFFSET) long offset) {

        try {
            log.info("═══════════════════════════════════════════════════");
            log.info("EVENTO RECIBIDO DE KAFKA");
            log.info("═══════════════════════════════════════════════════");
            log.info("🔥 Topic: {}", topic);
            log.info("📂 Partition: {}", partition);
            log.info("📍 Offset: {}", offset);
            log.info("📋 Event Type: {}", event.getEventType());
            log.info("👤 User: {} ({})", event.getUserEmail(), event.getUserId());
            log.info("⚡ Action: {}", event.getAction());
            log.info("📝 Details: {}", event.getDetails());
            log.info("═══════════════════════════════════════════════════");

            auditService.logEvent(event);

            log.info("Evento procesado y almacenado exitosamente\n");

        } catch (Exception e) {
            log.error("Error al procesar evento de auditoría: {}", e.getMessage(), e);
            throw e; // Re-lanzar para que Kafka reintente
        }
    }

    @KafkaListener(
            topics = "${kafka.topics.user-registered}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consumeUserRegisteredEvent(@Payload AuditEvent event) {
        try {
            log.info("👤 Usuario registrado: {}", event.getUserEmail());
            auditService.logEvent(event);
        } catch (Exception e) {
            log.error("Error procesando registro de usuario: {}", e.getMessage(), e);
            throw e;
        }
    }

    @KafkaListener(
            topics = "${kafka.topics.faculty-created}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consumeFacultyCreatedEvent(@Payload AuditEvent event) {
        try {
            log.info("Facultad creada: {}", event.getDetails());
            auditService.logEvent(event);
        } catch (Exception e) {
            log.error("Error procesando creación de facultad: {}", e.getMessage(), e);
            throw e;
        }
    }
}
