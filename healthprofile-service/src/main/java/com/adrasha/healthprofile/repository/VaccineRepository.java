package com.adrasha.healthprofile.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adrasha.healthprofile.model.Vaccine;

public interface VaccineRepository extends JpaRepository<Vaccine, UUID>{

}
