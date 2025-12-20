package com.omurkrmn.library_management.messasing.consumer;

import com.omurkrmn.library_management.entity.AuditLog;
import com.omurkrmn.library_management.messasing.event.LibraryEvent;
import com.omurkrmn.library_management.repository.AuditLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LibraryEventConsumer {

    private final AuditLogRepository auditLogRepository;

    public LibraryEventConsumer(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    @RabbitListener(queues = "RabbitMQConfig.QUEUE")
    public void consume(LibraryEvent libraryEvent) {

        log.info("EVENT RECEIVED -> type: {}, message: {}",
                libraryEvent.type(), libraryEvent.message());

        AuditLog logEntity = new AuditLog(
                null,
                libraryEvent.type(),
                libraryEvent.message(),
                libraryEvent.time()
        );
        auditLogRepository.save(logEntity);
    }
}
