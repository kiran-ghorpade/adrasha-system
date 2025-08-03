package com.adrasha.analytics.event;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.adrasha.analytics.service.MemberAnalyticsService;
import com.adrasha.core.config.RabbitMQConfig;
import com.adrasha.core.dto.event.MemberCreatedEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AnalyticsEventListener {
	
	private final MemberAnalyticsService memberAnalyticsService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void onMemberCreated(MemberCreatedEvent message) {
    	
        memberAnalyticsService.createNewMemberRecord(message);
    }
}
