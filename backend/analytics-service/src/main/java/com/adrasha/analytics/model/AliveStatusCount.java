package com.adrasha.analytics.model;

import java.time.Instant;
import java.util.UUID;

import com.adrasha.core.model.AliveStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
public class AliveStatusCount {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AliveStatus aliveStatus;

    @Column(nullable = false)
    private Long count;

    @Column(nullable = false)
    private Instant createdAt;
}
