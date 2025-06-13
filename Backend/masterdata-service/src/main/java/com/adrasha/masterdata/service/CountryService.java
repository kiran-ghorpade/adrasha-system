package com.adrasha.masterdata.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.adrasha.core.exception.AlreadyExistsException;
import com.adrasha.core.exception.NotFoundException;
import com.adrasha.masterdata.model.Country;

public interface CountryService {

	// country
	Page<Country> getAllCountries(Pageable pageable);
	
	Country getCountry(UUID id) throws NotFoundException;
	
	Country createCountry(Country country) throws AlreadyExistsException;
	
	Country updateCountry(UUID id, Country country) throws NotFoundException;
	
	void deleteCountry(UUID id) throws NotFoundException;
	
}
