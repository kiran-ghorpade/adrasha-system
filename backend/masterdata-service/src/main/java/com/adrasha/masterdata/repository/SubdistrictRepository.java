package com.adrasha.masterdata.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adrasha.masterdata.model.Subdistrict;

public interface SubdistrictRepository extends JpaRepository<Subdistrict, UUID> {

	Optional<Subdistrict> findByName(String name);
}
