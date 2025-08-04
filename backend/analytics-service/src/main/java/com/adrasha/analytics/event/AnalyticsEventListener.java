package com.adrasha.analytics.event;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.adrasha.analytics.service.MemberAnalyticsService;
import com.adrasha.core.config.RabbitMQConfig;
import com.adrasha.core.dto.event.MemberCreatedEvent;
import com.adrasha.core.dto.event.MemberDeletedEvent;
import com.adrasha.core.dto.event.MemberUpdatedEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AnalyticsEventListener {
	
	private final MemberAnalyticsService memberAnalyticsService;

	@RabbitListener(queues = RabbitMQConfig.QUEUE)
	public void onMemberCreated(MemberCreatedEvent event) {
		memberAnalyticsService.handleMemberCreated(event);
	}

	@RabbitListener(queues = RabbitMQConfig.QUEUE)
	public void onMemberUpdated(MemberUpdatedEvent event) {
		memberAnalyticsService.handleMemberUpdated(event);
	}

	@RabbitListener(queues = RabbitMQConfig.QUEUE)
	public void onMemberDeleted(MemberDeletedEvent event) {
		memberAnalyticsService.handleMemberDeleted(event);
	}

}
