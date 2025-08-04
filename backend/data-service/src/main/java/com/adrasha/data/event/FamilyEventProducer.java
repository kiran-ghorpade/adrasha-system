package com.adrasha.data.event;

import java.time.Instant;
import java.util.Objects;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.adrasha.core.config.RabbitMQConfig;
import com.adrasha.core.dto.event.FamilyCreatedEvent;
import com.adrasha.core.dto.event.FamilyDeletedEvent;
import com.adrasha.core.dto.event.FamilyUpdatedEvent;
import com.adrasha.core.dto.event.keys.FamilyEventRoutingKey;
import com.adrasha.data.model.Family;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FamilyEventProducer {

	private final RabbitTemplate rabbitTemplate;

	public void sendCreatedEvent(Family family) {
		FamilyCreatedEvent event = FamilyCreatedEvent.builder().ashaId(family.getAshaId())
				.povertyStatus(family.getPovertyStatus())
				.createdAt(Instant.now()).build();

		rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, FamilyEventRoutingKey.FAMILY_CREATED.key(), event);
	}

	public void sendUpdatedEvent(Family oldFamily, Family newFamily) {

		boolean changed = !Objects.equals(oldFamily.getPovertyStatus(), newFamily.getPovertyStatus());

		if (!changed) {
			return;
		}

		FamilyUpdatedEvent event = FamilyUpdatedEvent.builder().ashaId(newFamily.getAshaId())
				.oldPovertyStatus(oldFamily.getPovertyStatus())
				.newPovertyStatus(newFamily.getPovertyStatus())
				.createdAt(Instant.now()).build();

		rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, FamilyEventRoutingKey.FAMILY_UPDATED.key(), event);
	}

	public void sendDeletedEvent(Family family) {
		FamilyDeletedEvent event = FamilyDeletedEvent.builder().ashaId(family.getAshaId())
				.povertyStatus(family.getPovertyStatus())
				.createdAt(Instant.now()).build();

		rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, FamilyEventRoutingKey.FAMILY_DELETED.key(), event);
	}
}