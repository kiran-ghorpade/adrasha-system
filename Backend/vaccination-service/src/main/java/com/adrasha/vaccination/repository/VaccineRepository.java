package com.adrasha.vaccination.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adrasha.vaccination.model.Vaccine;

public interface VaccineRepository extends JpaRepository<Vaccine, UUID>{

}
