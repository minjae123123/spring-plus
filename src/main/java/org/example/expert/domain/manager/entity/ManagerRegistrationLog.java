package org.example.expert.domain.manager.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.expert.domain.common.entity.Timestamped;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "log")
public class ManagerRegistrationLog extends Timestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long requesterId;

    @Column(nullable = false)
    private String requesterEmail;

    @Column(nullable = false)
    private Long todoId;

    @Column(nullable = false)
    private Long managerUserId;

    public ManagerRegistrationLog(Long requesterId, String requesterEmail, Long todoId, Long managerUserId) {
        this.requesterId = requesterId;
        this.requesterEmail = requesterEmail;
        this.todoId = todoId;
        this.managerUserId = managerUserId;
    }
}