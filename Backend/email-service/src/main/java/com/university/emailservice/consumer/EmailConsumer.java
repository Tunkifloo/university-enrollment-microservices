package com.university.emailservice.consumer;

import com.university.emailservice.dto.EmailMessage;
import com.university.emailservice.service.EmailSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailConsumer {

    private final EmailSenderService emailSenderService;

    @RabbitListener(queues = "${rabbitmq.queue.email}")
    public void consumeEmailMessage(EmailMessage message) {
        try {
            log.info("Mensaje recibido de RabbitMQ: {}", message.getTo());

            emailSenderService.sendEmail(message);

            log.info("Mensaje procesado exitosamente");

        } catch (Exception e) {
            log.error("Error al procesar mensaje de email: {}", e.getMessage(), e);
            throw e; // Re-lanzar para que RabbitMQ reintente
        }
    }
}