package com.university.emailservice.service;

import com.university.emailservice.dto.EmailMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
public class EmailSenderService {

    @Value("${email.simulation-mode:true}")
    private boolean simulationMode;

    public void sendEmail(EmailMessage message) {
        if (simulationMode) {
            simulateEmailSending(message);
        } else {
            sendRealEmail(message);
        }
    }

    private void simulateEmailSending(EmailMessage message) {
        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        log.info("═══════════════════════════════════════════════════");
        log.info("📧 SIMULACIÓN DE ENVÍO DE EMAIL");
        log.info("═══════════════════════════════════════════════════");
        log.info("⏰ Timestamp: {}", timestamp);
        log.info("📨 Para: {}", message.getTo());
        log.info("👤 Usuario: {}", message.getUserName());
        log.info("📋 Asunto: {}", message.getSubject());
        log.info("📝 Mensaje:");
        log.info("---------------------------------------------------");
        log.info("{}", message.getBody());
        log.info("---------------------------------------------------");
        log.info("Email simulado enviado exitosamente");
        log.info("═══════════════════════════════════════════════════\n");
    }

    private void sendRealEmail(EmailMessage message) {
        log.warn("Envío real de emails no implementado aún. Usando simulación.");
        simulateEmailSending(message);
    }
}