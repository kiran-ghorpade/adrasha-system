package com.adrasha.data.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.adrasha.core.model.AliveStatus;
import com.adrasha.core.model.Gender;
import com.adrasha.data.model.Member;

public interface MemberRepository extends JpaRepository<Member, UUID> {

	boolean existsByAdharNumber(String adharNumber);

//	---------------------------------------------------------------------------
	// Analytics
	
	@Query("SELECT m.gender, COUNT(m) FROM Member m GROUP BY m.gender")
	List<Object[]> countByGender();


	@Query("SELECT m.aliveStatus, COUNT(m) FROM Member m GROUP BY m.aliveStatus")
	List<Object[]>countByAliveStatus();

	Long countByDateOfBirthBetween(LocalDate start, LocalDate end);
	
//---------------------------------------------------------------------------
	// Reports

	List<Member> findByAshaIdAndFamilyIdAndGenderAndAliveStatusAndDateOfBirthBetween(
			UUID ashaId,
		    UUID familyId,
		    Gender gender,
		    AliveStatus status,
		    LocalDate minDateOfBirth,
		    LocalDate maxDateOfBirth
		    );
}
