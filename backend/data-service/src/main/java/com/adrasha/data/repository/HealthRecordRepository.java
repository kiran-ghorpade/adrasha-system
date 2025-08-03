package com.adrasha.data.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.adrasha.data.model.HealthRecord;

@Repository
public interface HealthRecordRepository extends JpaRepository<HealthRecord, UUID> {

	Optional<HealthRecord> findByMemberId(UUID memberId);

	// Analytics
	@Query("SELECT pregnant, COUNT(h) FROM HealthRecord h WHERE h.pregnant = true")
	Long countPregnant();

    @Query("SELECT ncd as key, COUNT(hr) FROM HealthRecord hr JOIN hr.ncdList ncd GROUP BY ncd")
    List<Object[]> countByNcd();

	List<HealthRecord> findByPregnantTrue();

    List<HealthRecord> findByNcdListNotEmpty();

  //---------------------------------------------------------------------------
  		// Reports

//  		@Query("""
//  			    SELECT new com.adrasha.data.member.dto.FamilyReportDTO(
//  			        m.houseId,
//  			        m.headMemberName,
//  			        m.povertyStatus
//  			    )
//  			    FROM Family m
//  			    WHERE (:ashaId IS NULL OR m.ashaId = :ashaId)
//  			      AND (:povertyStatus IS NULL OR m.povertyStatus = :povertyStatus)
//  			      
//  			""")
//  			List<FamilyReportDTO> findFamilyByFilters(
//  					 @Param("ashaId") UUID ashaId,
//  					 @Param("povertyStatus") PovertyStatus povertyStatus
//  			 );
}