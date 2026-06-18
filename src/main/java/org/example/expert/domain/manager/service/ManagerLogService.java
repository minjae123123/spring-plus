package org.example.expert.domain.manager.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.manager.entity.ManagerRegistrationLog;
import org.example.expert.domain.manager.repository.ManagerRegistrationLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ManagerLogService {

    private final ManagerRegistrationLogRepository managerRegistrationLogRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveManagerRegistrationLog(AuthUser authUser, long todoId, Long managerUserId) {
        managerRegistrationLogRepository.save(new ManagerRegistrationLog(
                authUser.getId(),
                authUser.getEmail(),
                todoId,
                managerUserId
        ));
    }
}