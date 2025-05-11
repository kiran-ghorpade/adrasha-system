package com.adrasha.ashaservice.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adrasha.ashaservice.model.Asha;

@Repository
public interface AshaRepository extends JpaRepository<Asha, UUID>{

	public Optional<Asha> findByMobileNumber(String mobileNumber);
}
