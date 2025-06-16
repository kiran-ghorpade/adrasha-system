package com.adrasha.masterdata.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adrasha.masterdata.model.Country;


@Repository
public interface CountryRepository extends JpaRepository<Country, UUID>{

	Optional<Country> findByName(String name);
}
