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
import com.adrasha.masterdata.model.Country;
import com.adrasha.masterdata.repository.CountryRepository;
import com.adrasha.masterdata.service.CountryService;

// TODO : Review class 
@Service
public class CountryServiceImpl implements  CountryService{

	@Autowired
	private CountryRepository  countryRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Page<Country> getAllCountries(Pageable pageable) {

		return countryRepository.findAll(pageable);
	}

	@Override
	public Country getCountry(UUID countryId) {

		return countryRepository.findById(countryId)
				.orElseThrow(() -> new NotFoundException("Country Not Found with id : " + countryId));
	}

	@Override
	public Country createCountry(Country country) {
		
		Optional<Country> existingRequest = countryRepository.findByName(country.getName());
		
	  	if(existingRequest.isPresent()) {
	  		throw new AlreadyExistsException("Country with name : "+ country.getName()+" already exist.");
	  	}

		return countryRepository.save(country);
	}

	@Override
	public Country updateCountry(UUID countryId, Country updatedCountry) {
		Country country = getCountry(countryId);
		modelMapper.map(updatedCountry, country);
		return countryRepository.save(country);
	}

	@Override
	public void deleteCountry(UUID countryId) {
		Country country = getCountry(countryId);
		countryRepository.delete(country);
	}

}
