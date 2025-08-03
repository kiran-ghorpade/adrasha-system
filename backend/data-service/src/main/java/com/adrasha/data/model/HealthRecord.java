package com.adrasha.data.model;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

import com.adrasha.core.model.Auditable;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class HealthRecord extends Auditable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

    @Column(nullable = false)
	private UUID memberId;
    
    @Column(nullable = false)
	private UUID ashaId;
	
    @Column(nullable = false)
	private Instant recordedAt;
	
    private Boolean pregnant;
	
    private Double height;

    private Double weight;
	
    /**
     * Set of UUIDs for Non-Communicable Diseases associated with this record.
     */
    @ElementCollection
    private Set<UUID> ncdList;
}
