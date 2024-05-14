package com.pchnch.spcurrencyexchange.repository;

import com.pchnch.spcurrencyexchange.model.Audit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditRepository extends JpaRepository<Audit, Long> {
}
