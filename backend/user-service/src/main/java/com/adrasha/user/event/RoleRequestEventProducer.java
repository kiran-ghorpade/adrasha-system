package com.adrasha.user.event;

import java.time.Instant;
import java.util.Objects;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.adrasha.core.config.RabbitMQConfig;
import com.adrasha.core.dto.event.RoleRequestCreatedEvent;
import com.adrasha.core.dto.event.RoleRequestDeletedEvent;
import com.adrasha.core.dto.event.RoleRequestUpdatedEvent;
import com.adrasha.core.dto.event.keys.RoleRequestEventRoutingKey;
import com.adrasha.user.model.RoleRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleRequestEventProducer {

	private final RabbitTemplate rabbitTemplate;

	public void sendCreatedEvent(RoleRequest roleRequest) {
		RoleRequestCreatedEvent event = RoleRequestCreatedEvent.builder()
				.status(roleRequest.getStatus())
				.createdAt(Instant.now()).build();

		rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RoleRequestEventRoutingKey.ROLE_REQUEST_CREATED.key(), event);
	}

	public void sendUpdatedEvent(RoleRequest oldRoleRequest, RoleRequest newRoleRequest) {

		boolean changed = !Objects.equals(oldRoleRequest.getStatus(), newRoleRequest.getStatus());

		if (!changed) {
			return;
		}

		RoleRequestUpdatedEvent event = RoleRequestUpdatedEvent.builder()
				.oldRequestStatus(oldRoleRequest.getStatus())
				.newRequestStatus(newRoleRequest.getStatus())
				.createdAt(Instant.now()).build();

		rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RoleRequestEventRoutingKey.ROLE_REQUEST_UPDATED.key(), event);
	}

	public void sendDeletedEvent(RoleRequest roleRequest) {
		RoleRequestDeletedEvent event = RoleRequestDeletedEvent.builder()
				.status(roleRequest.getStatus())
				.createdAt(Instant.now()).build();

		rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RoleRequestEventRoutingKey.ROLE_REQUEST_DELETED.key(), event);
	}
}