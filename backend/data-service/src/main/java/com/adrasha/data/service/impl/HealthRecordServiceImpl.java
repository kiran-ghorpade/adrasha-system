package com.adrasha.data.service.impl;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.adrasha.core.exception.NotFoundException;
import com.adrasha.data.model.HealthRecord;
import com.adrasha.data.repository.HealthRecordRepository;
import com.adrasha.data.repository.MemberRepository;
import com.adrasha.data.service.HealthRecordService;

@Service
public class HealthRecordServiceImpl implements HealthRecordService {

	@Autowired
	private HealthRecordRepository healthRecordRepository;
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Page<HealthRecord> getAllRecords(Example<HealthRecord> example, Pageable pageable) {

		return healthRecordRepository.findAll(example, pageable);
	}

	@Override
	public HealthRecord getHealthRecord(UUID memberId) {

		return healthRecordRepository.findByMemberId(memberId)
				.orElseThrow(() -> new NotFoundException("HealthRecord Not Found with id : " + memberId));
	}

	@Override
	public HealthRecord createHealthRecord(HealthRecord healthRecord) {
		
		if(!memberRepository.existsById(healthRecord.getMemberId())) {
			throw new NotFoundException("Member Not Found with Id : "+ healthRecord.getMemberId());
		}
		
		

		return healthRecordRepository.save(healthRecord);
	}

	@Override
	public HealthRecord updateHealthRecord(UUID healthRecordId, HealthRecord updatedHealthRecordDetails) {
		HealthRecord healthRecord = getHealthRecord(healthRecordId);
		modelMapper.map(updatedHealthRecordDetails, healthRecord);
		return healthRecordRepository.save(healthRecord);
	}

	@Override
	public HealthRecord deleteHealthRecord(UUID healthRecordId) {
		HealthRecord healthRecord = getHealthRecord(healthRecordId);
		healthRecordRepository.delete(healthRecord);
		return healthRecord;
	}

}
