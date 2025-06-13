package com.adrasha.health.service.serviceImpl;

import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.adrasha.core.exception.AlreadyExistsException;
import com.adrasha.core.exception.NotFoundException;
import com.adrasha.health.model.HealthInfo;
import com.adrasha.health.repository.HealthInfoRepository;
import com.adrasha.health.service.HealthInfoService;

@Service
public class HealthInfoServiceImpl implements HealthInfoService {

	@Autowired
	private HealthInfoRepository healthInfoRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Page<HealthInfo> getAllFamilies(Pageable pageable) {

		return healthInfoRepository.findAll(pageable);
	}

	@Override
	public HealthInfo getHealthInfo(UUID memberId) {

		return healthInfoRepository.findByMemberId(memberId)
				.orElseThrow(() -> new NotFoundException("HealthInfo Not Found with id : " + memberId));
	}

	@Override
	public HealthInfo createHealthInfo(HealthInfo healthInfo) {
		
		Optional<HealthInfo> exsistingHealthInfo = healthInfoRepository.findByMemberId(healthInfo.getMemberId());
		
		if(exsistingHealthInfo.isPresent()) {
			throw new AlreadyExistsException("HealthInfo with headId : "+ healthInfo.getMemberId()+" already present");
		}
		
		return healthInfoRepository.save(healthInfo);
	}

	@Override
	public HealthInfo updateHealthInfo(UUID healthInfoId, HealthInfo updatedHealthInfoDetails) {
		HealthInfo healthInfo = getHealthInfo(healthInfoId);
		modelMapper.map(updatedHealthInfoDetails, healthInfo);
		return healthInfoRepository.save(healthInfo);
	}

	@Override
	public HealthInfo deleteHealthInfo(UUID healthInfoId) {
		HealthInfo healthInfo = getHealthInfo(healthInfoId);
		healthInfoRepository.delete(healthInfo);
		return healthInfo;
	}

}
