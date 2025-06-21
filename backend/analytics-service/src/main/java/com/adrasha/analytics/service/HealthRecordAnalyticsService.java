package com.adrasha.analytics.service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adrasha.analytics.client.HealthRecordClient;
import com.adrasha.analytics.client.NCDClient;
import com.adrasha.analytics.client.UserDataClient;
import com.adrasha.core.filter.dto.HealthRecordFilterDTO;
import com.adrasha.core.response.dto.HealthRecordResponseDTO;
import com.adrasha.core.response.dto.NCDResponseDTO;
import com.adrasha.core.response.dto.UserResponseDTO;
import com.adrasha.core.utils.PaginationUtils;

@Service
public class HealthRecordAnalyticsService {
	
	@Autowired
	private HealthRecordClient healthRecordClient;
	
	@Autowired
	private NCDClient ncdClient;
	
	@Autowired
	private UserDataClient userDataClient;
	
	private long getCount(HealthRecordFilterDTO filterDTO) {
		return healthRecordClient.getCount(filterDTO).getOrDefault("count", 0L);
	}
	
	private HealthRecordFilterDTO getAshaCriteria() {
		UserResponseDTO user = userDataClient.getCurrentUserDetails();
		
		return HealthRecordFilterDTO.builder()
				.ashaId(user.getId())
				.build();
	}
	
	public long getTotalRecords() {
				
		return this.getCount(this.getAshaCriteria());
	}
	
	public long getPregnancyCountByAsha() {
		
		UserResponseDTO user = userDataClient.getCurrentUserDetails();
		
		return this.getLatestRecordsStream()
				.filter(HealthRecordResponseDTO::getPregnant)
				.filter(record -> record.getAshaId().equals(user.getId()))
				.count();
	}
	
	public Stream<HealthRecordResponseDTO> getLatestRecordsStream(){
		//sorry for this bad code :)
		List<HealthRecordResponseDTO> records = PaginationUtils.getAllRecords(
														healthRecordClient::getAll, 
														this.getAshaCriteria(),
														null
												);
	
		// latest records
		return records.stream()
				.collect(Collectors.toMap(
							HealthRecordResponseDTO::getMemberId, //key
							Function.identity(), //value
							(r1, r2) -> r1.getCreatedAt().isAfter(r2.getCreatedAt()) ? r1 : r2) // called on conflict
				).values().stream();
	}
	
	public Map<String, Long> getNCDDistribution(){
		List<NCDResponseDTO> ncdData = PaginationUtils.getAllRecords(ncdClient::getAll, null, null);
				
		// lookup map for searching 
		Map<UUID, String> lookup = ncdData.stream()
								 .collect(Collectors.toMap(NCDResponseDTO::getId, NCDResponseDTO::getName));
		

		return	this.getLatestRecordsStream()
				.flatMap(record -> record.getNCDList().stream())
				.filter(Objects::nonNull)
				.map(ncdId -> lookup.getOrDefault(ncdId, "Other"))
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
	}
	
}