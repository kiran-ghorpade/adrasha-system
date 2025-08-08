package com.adrasha.data.service.impl;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.adrasha.core.dto.filter.HealthRecordFilterDTO;
import com.adrasha.core.dto.reports.HealthRecordReportDTO;
import com.adrasha.core.dto.reports.MemberReportDTO;
import com.adrasha.core.dto.response.HealthRecordResponseDTO;
import com.adrasha.core.exception.NotFoundException;
import com.adrasha.core.utils.ExampleMatcherUtils;
import com.adrasha.data.event.HealthRecordEventProducer;
import com.adrasha.data.health.records.dto.HealthRecordCreateDTO;
import com.adrasha.data.health.records.dto.HealthRecordUpdateDTO;
import com.adrasha.data.model.HealthRecord;
import com.adrasha.data.repository.HealthRecordRepository;
import com.adrasha.data.repository.MemberRepository;
import com.adrasha.data.service.HealthRecordService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HealthRecordServiceImpl implements HealthRecordService {

	private final HealthRecordRepository healthRecordRepository;
	private final MemberRepository memberRepository;
	private final ModelMapper mapper;
	private final HealthRecordEventProducer eventProducer;

	@Override
	public Page<HealthRecordResponseDTO> getHealthRecordPage(HealthRecordFilterDTO filterDTO, Pageable pageable) {

		HealthRecord exampleRecord = mapper.map(filterDTO, HealthRecord.class);

		Example<HealthRecord> example = Example.of(exampleRecord, ExampleMatcherUtils.getDefaultMatcher());

		Page<HealthRecord> page = healthRecordRepository.findAll(example, pageable);

		return page.map(record -> mapper.map(record, HealthRecordResponseDTO.class));
	}

	@Override
	public List<HealthRecordReportDTO> getHealthRecordList(HealthRecordFilterDTO filterDTO) {
		HealthRecord exampleRecord = mapper.map(filterDTO, HealthRecord.class);

		Example<HealthRecord> example = Example.of(exampleRecord, ExampleMatcherUtils.getDefaultMatcher());

		List<HealthRecord> list = healthRecordRepository.findAll(example);
		
		return list.stream()
				.map(record ->{
					HealthRecordReportDTO out = mapper.map(record, HealthRecordReportDTO.class);
					out.setMemberName(memberRepository.findById(record.getMemberId())
									.map(member -> member.getName().getFullName()).orElse("Name Not Found"));
					return out;
				})
				.toList();	}

	@Override
	public Long getHealthRecordCount(HealthRecordFilterDTO filterDTO) {
		HealthRecord exampleRecord = mapper.map(filterDTO, HealthRecord.class);

		Example<HealthRecord> example = Example.of(exampleRecord, ExampleMatcherUtils.getDefaultMatcher());
		
		return healthRecordRepository.count(example);
	}

	@Override
	public HealthRecordResponseDTO getHealthRecord(UUID healthRecordId) {
		return healthRecordRepository.findById(healthRecordId)
				.map(record -> mapper.map(record, HealthRecordResponseDTO.class))
				.orElseThrow(() -> new NotFoundException("error.healthRecord.notFound"));
	}

	@Override
	public HealthRecordResponseDTO createHealthRecord(HealthRecordCreateDTO createDTO) {

		if (!memberRepository.existsById(createDTO.getMemberId())) {
			throw new NotFoundException("error.healthRecord.notFound");
		}
		
		HealthRecord healthRecord = mapper.map(createDTO, HealthRecord.class);
		HealthRecord newRecord = healthRecordRepository.save(healthRecord);
		
		eventProducer.sendCreatedEvent(newRecord);
		return mapper.map(newRecord, HealthRecordResponseDTO.class);
	}

	@Override
	public HealthRecordResponseDTO updateHealthRecord(UUID healthRecordId, HealthRecordUpdateDTO updateDTO) {
		HealthRecord healthRecord = healthRecordRepository.findById(healthRecordId)
				.orElseThrow(() -> new NotFoundException("error.healthRecord.notFound"));

		HealthRecord oldHealthRecord = healthRecord;
		
		mapper.map(updateDTO, healthRecord);
		HealthRecord savedRecord = healthRecordRepository.save(healthRecord);
		eventProducer.sendUpdatedEvents(oldHealthRecord, savedRecord);
		
		return mapper.map(savedRecord, HealthRecordResponseDTO.class);
	}

	@Override
	public HealthRecordResponseDTO deleteHealthRecord(UUID healthRecordId) {
		HealthRecord healthRecord = healthRecordRepository.findById(healthRecordId)
				.orElseThrow(() -> new NotFoundException("error.healthRecord.notFound"));

		healthRecordRepository.delete(healthRecord);
		eventProducer.sendDeletedEvents(healthRecord);
		
		return mapper.map(healthRecord, HealthRecordResponseDTO.class);
	}
}
