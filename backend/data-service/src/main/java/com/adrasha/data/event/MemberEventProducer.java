package com.adrasha.data.event;

import java.time.Instant;
import java.util.Objects;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.adrasha.core.config.RabbitMQConfig;
import com.adrasha.core.dto.event.MemberCreatedEvent;
import com.adrasha.core.dto.event.MemberDeletedEvent;
import com.adrasha.core.dto.event.MemberUpdatedEvent;
import com.adrasha.core.dto.event.keys.MemberEventRoutingKey;
import com.adrasha.core.model.AgeGroup;
import com.adrasha.data.model.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberEventProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendCreatedEvent(Member member) {
        MemberCreatedEvent event = MemberCreatedEvent.builder()
            .ashaId(member.getAshaId())
            .ageGroup(AgeGroup.fromAge(member.getAge()))
            .gender(member.getGender())
            .aliveStatus(member.getAliveStatus())
            .createdAt(member.getCreatedAt())
            .build();

        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, MemberEventRoutingKey.MEMBER_CREATED.key(), event);
    }

    
    public void sendUpdatedEvent(Member oldMember, Member newMember) {
    	AgeGroup oldGroup = AgeGroup.fromAge(oldMember.getAge());
    	AgeGroup newGroup = AgeGroup.fromAge(newMember.getAge());
    	
        boolean changed = !Objects.equals(oldMember.getAliveStatus(), newMember.getAliveStatus()) ||
                          !Objects.equals(oldMember.getGender(), newMember.getGender()) ||
                          !Objects.equals(oldGroup, newGroup);

        if (!changed) {
            return;
        }

        MemberUpdatedEvent event = MemberUpdatedEvent.builder()
            .ashaId(newMember.getAshaId())
            .oldAliveStatus(oldMember.getAliveStatus())
            .newAliveStatus(newMember.getAliveStatus())
            .oldGender(oldMember.getGender())
            .newGender(newMember.getGender())
            .oldAgeGroup(oldGroup)
            .newAgeGroup(newGroup)
            .createdAt(Instant.now())
            .build();

        rabbitTemplate.convertAndSend(
            RabbitMQConfig.EXCHANGE,
            MemberEventRoutingKey.MEMBER_UPDATED.key(),
            event
        );
    }

    public void sendDeletedEvent(Member member) {
        MemberDeletedEvent event = MemberDeletedEvent.builder()
            .ashaId(member.getAshaId())
            .ageGroup(AgeGroup.fromAge(member.getAge()))
            .gender(member.getGender())
            .aliveStatus(member.getAliveStatus())
            .createdAt(member.getCreatedAt())
            .build();

        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, MemberEventRoutingKey.MEMBER_CREATED.key(), event);
    }
}
