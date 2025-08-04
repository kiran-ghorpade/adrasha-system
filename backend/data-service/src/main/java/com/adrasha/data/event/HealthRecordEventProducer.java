package com.adrasha.data.event;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.adrasha.core.config.RabbitMQConfig;
import com.adrasha.core.dto.event.HealthRecordCreatedEvent;
import com.adrasha.core.dto.event.HealthRecordDeletedEvent;
import com.adrasha.core.dto.event.keys.HealthRecordEventRoutingKey;
import com.adrasha.data.model.HealthRecord;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HealthRecordEventProducer {

	private final RabbitTemplate rabbitTemplate;

	public void sendCreatedEvent(HealthRecord healthRecord) {
	    if (healthRecord.getNcdList() != null && !healthRecord.getNcdList().isEmpty()) {
	        for (UUID ncdId : healthRecord.getNcdList()) {
	            HealthRecordCreatedEvent event = HealthRecordCreatedEvent.builder()
	                    .ashaId(healthRecord.getAshaId())
	                    .ncd(ncdId)
	                    .createdAt(healthRecord.getCreatedAt())
	                    .build();

	            rabbitTemplate.convertAndSend(
	                    RabbitMQConfig.EXCHANGE, 
	                    HealthRecordEventRoutingKey.HEALTH_RECORD_CREATED.key(),
	                    event
	            );
	        }
	    }
	}


	public void sendUpdatedEvents(HealthRecord oldRecord, HealthRecord newRecord) {
		Set<UUID> oldNcds = oldRecord.getNcdList() != null ? oldRecord.getNcdList() : Set.of();
		Set<UUID> newNcds = newRecord.getNcdList() != null ? newRecord.getNcdList() : Set.of();

		Set<UUID> added = new HashSet<>(newNcds);
		added.removeAll(oldNcds);

		Set<UUID> removed = new HashSet<>(oldNcds);
		removed.removeAll(newNcds);

		// Send added events
		for (UUID addedNcd : added) {
			HealthRecordCreatedEvent event = HealthRecordCreatedEvent.builder()
					.ashaId(newRecord.getAshaId())
					.ncd(addedNcd)
					.createdAt(newRecord.getCreatedAt())
					.build();

			rabbitTemplate.convertAndSend(
					RabbitMQConfig.EXCHANGE,
					HealthRecordEventRoutingKey.HEALTH_RECORD_CREATED.key(),
					event
			);
		}

		// Send deleted events
		for (UUID removedNcd : removed) {
			HealthRecordDeletedEvent event = HealthRecordDeletedEvent.builder()
					.ashaId(oldRecord.getAshaId())
					.ncd(removedNcd)
					.createdAt(oldRecord.getCreatedAt())
					.build();

			rabbitTemplate.convertAndSend(
					RabbitMQConfig.EXCHANGE,
					HealthRecordEventRoutingKey.HEALTH_RECORD_DELETED.key(),
					event
			);
		}
	}

	public void sendDeletedEvents(HealthRecord healthRecord) {
		if (healthRecord.getNcdList() == null || healthRecord.getNcdList().isEmpty()) return;

		for (UUID ncd : healthRecord.getNcdList()) {
			HealthRecordDeletedEvent event = HealthRecordDeletedEvent.builder()
					.ashaId(healthRecord.getAshaId())
					.ncd(ncd)
					.createdAt(healthRecord.getCreatedAt())
					.build();

			rabbitTemplate.convertAndSend(
					RabbitMQConfig.EXCHANGE,
					HealthRecordEventRoutingKey.HEALTH_RECORD_DELETED.key(),
					event
			);
		}
	}
}
