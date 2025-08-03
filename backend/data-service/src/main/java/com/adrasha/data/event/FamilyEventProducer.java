package com.adrasha.data.event;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.adrasha.core.config.RabbitMQConfig;
import com.adrasha.core.dto.event.MemberCreatedEvent;
import com.adrasha.core.dto.event.MemberUpdatedEvent;
import com.adrasha.core.dto.event.keys.MemberEventRoutingKey;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FamilyEventProducer {

    private final RabbitTemplate rabbitTemplate;


    public void sendCreatedEvent(MemberCreatedEvent event) {
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.EXCHANGE,
            MemberEventRoutingKey.MEMBER_CREATED.key(),
            event
        );
    }
    
    public void sendUpdatedEvent(MemberUpdatedEvent event) {
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.EXCHANGE,
            MemberEventRoutingKey.MEMBER_UPDATED.key(),
            event
        );
    }
    
    public void sendDeletedEvent(MemberUpdatedEvent event) {
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.EXCHANGE,
            MemberEventRoutingKey.MEMBER_UPDATED.key(),
            event
        );
    }	
}
