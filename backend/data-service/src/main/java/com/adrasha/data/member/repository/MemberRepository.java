package com.adrasha.data.member.repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.adrasha.core.dto.reports.MemberReportDTO;
import com.adrasha.core.model.AliveStatus;
import com.adrasha.core.model.Gender;
import com.adrasha.data.member.model.Member;

public interface MemberRepository extends JpaRepository<Member, UUID> {

	boolean existsByAdharNumber(String adharNumber);

//	---------------------------------------------------------------------------
	// Analytics

	List<Member> findByAliveStatus(AliveStatus status);

	List<Member> findByIsFamilyHeadTrue();

	@Query("SELECT m.gender AS key, COUNT(m) AS value FROM Member m GROUP BY m.gender")
	Map<Gender, Long> countByGender();

	@Query("SELECT m.aliveStatus AS key, COUNT(m) AS value FROM Member m GROUP BY m.aliveStatus")
	Map<AliveStatus, Long> countByAliveStatus();

	@Query("SELECT " + "CASE " + " WHEN m.age >= 0 AND m.age < 1 THEN 'INFANT' "
			+ " WHEN m.age >= 1 AND m.age < 12 THEN 'CHILD' " + " WHEN m.age >= 12 AND m.age < 18 THEN 'TEEN' "
			+ " WHEN m.age >= 18 AND m.age < 35 THEN 'ADULT' "
			+ " WHEN m.age >= 35 AND m.age < 60 THEN 'MID_AGED_ADULT' " + " WHEN m.age >= 60 THEN 'SENIOR' "
			+ " ELSE 'UNKNOWN' END, COUNT(m) " + "FROM Member m GROUP BY " + "CASE "
			+ " WHEN m.age >= 0 AND m.age < 1 THEN 'INFANT' " + " WHEN m.age >= 1 AND m.age < 12 THEN 'CHILD' "
			+ " WHEN m.age >= 12 AND m.age < 18 THEN 'TEEN' " + " WHEN m.age >= 18 AND m.age < 35 THEN 'ADULT' "
			+ " WHEN m.age >= 35 AND m.age < 60 THEN 'MID_AGED_ADULT' " + " WHEN m.age >= 60 THEN 'SENIOR' "
			+ " ELSE 'UNKNOWN' END")
	List<Object[]> countByAgeGroup();

	long countByIsFamilyHeadTrue();
//---------------------------------------------------------------------------
	// Reports queries
	
	@Query("SELECT new com.adrasha.data.member.dto.MemberReportDTO(m.id, m.name, m.age, m.gender, m.aliveStatus) FROM Member m")
	List<MemberReportDTO> findAllMembersForReport();

	@Query("""
			    SELECT new com.adrasha.data.member.dto.MemberReportDTO(m.id, m.name, m.age, m.gender, m.aliveStatus)
			    FROM Member m WHERE m.age BETWEEN :minAge AND :maxAge
			""")
	List<MemberReportDTO> findMembersByAgeRangeForReport(@Param("minAge") int minAge, @Param("maxAge") int maxAge);

	@Query("""
			    SELECT new com.adrasha.data.member.dto.MemberReportDTO(m.id, m.name, m.age, m.gender, m.aliveStatus)
			    FROM Member m WHERE m.gender = :gender
			""")
	List<MemberReportDTO> findMembersByGenderForReport(@Param("gender") Gender gender);

	@Query("""
			    SELECT new com.adrasha.data.member.dto.MemberReportDTO(m.id, m.name, m.age, m.gender, m.aliveStatus)
			    FROM Member m WHERE m.aliveStatus = :status
			""")
	List<MemberReportDTO> findMembersByStatusForReport(@Param("status") AliveStatus status);
}
