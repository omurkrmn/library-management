package com.omurkrmn.library_management.repository;

import com.omurkrmn.library_management.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuditLogRepository extends JpaRepository<AuditLog, UUID> {
}
