
package org.example.expert.domain.manager.repository;

import org.example.expert.domain.manager.entity.ManagerRegistrationLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRegistrationLogRepository extends JpaRepository<ManagerRegistrationLog, Long> {
}