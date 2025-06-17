package com.adrasha.masterdata.location.repository;

import org.springframework.stereotype.Repository;

import com.adrasha.masterdata.base.repository.MasterRepository;
import com.adrasha.masterdata.location.model.Country;

@Repository
public interface CountryRepository extends MasterRepository<Country>{

}
