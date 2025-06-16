package com.adrasha.masterdata.service.serviceImpl;

import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.adrasha.core.exception.AlreadyExistsException;
import com.adrasha.core.exception.NotFoundException;
import com.adrasha.masterdata.model.District;
import com.adrasha.masterdata.repository.DistrictRepository;
import com.adrasha.masterdata.service.DistrictService;

// TODO : Review class 
@Service
public class DistrictServiceImpl implements  DistrictService{

	@Autowired
	private DistrictRepository  districtRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Page<District> getAllDistricts(Pageable pageable, UUID countryId) {

		return districtRepository.findAll(pageable);
	}

	@Override
	public District getDistrict(UUID districtId) {

		return districtRepository.findById(districtId)
				.orElseThrow(() -> new NotFoundException("District Not Found with id : " + districtId));
	}

	@Override
	public District createDistrict(District district) {
		
		
		Optional<District> existingRequest = districtRepository.findByName(district.getName());
		
	  	if(existingRequest.isPresent()) {
	  		throw new AlreadyExistsException("District with name : "+ district.getName()+" already exist.");
	  	}

		return districtRepository.save(district);
	}

	@Override
	public District updateDistrict(UUID districtId, District updatedDistrict) {
		District district = getDistrict(districtId);
		modelMapper.map(updatedDistrict, district);
		return districtRepository.save(district);
	}

	@Override
	public void deleteDistrict(UUID districtId) {
		District district = getDistrict(districtId);
		districtRepository.delete(district);
	}

}
