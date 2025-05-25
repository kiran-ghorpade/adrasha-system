package com.adrasha.pregnancyservice.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adrasha.pregnancyservice.model.Pregnancy;

public interface PregnancyRepository extends JpaRepository<Pregnancy, UUID>{

	List<Pregnancy> findByMotherId(UUID motherId);
}
