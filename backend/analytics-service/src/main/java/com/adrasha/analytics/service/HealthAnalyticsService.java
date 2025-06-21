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
import com.adrasha.core.filter.dto.HealthRecordFilterDTO;
import com.adrasha.core.response.dto.HealthRecordResponseDTO;
import com.adrasha.core.response.dto.NCDResponseDTO;
import com.adrasha.core.utils.PaginationUtils;

@Service
public class HealthAnalyticsService {
	
	@Autowired
	private HealthRecordClient healthRecordClient;
	
	@Autowired
	private NCDClient ncdClient;
	
	private long getCount(HealthRecordFilterDTO filterDTO) {
		return healthRecordClient.getCount(filterDTO).getOrDefault("count", 0L);
	}
	
	public long getTotalRecords() {
		return this.getCount(null);
	}
	
	public long getPregnancyCount() {
		return this.getLatestRecordsStream()
				.filter(HealthRecordResponseDTO::getPregnant)
				.count();
	}
	
	public Stream<HealthRecordResponseDTO> getLatestRecordsStream(){
		//sorry for this bad code :)
		List<HealthRecordResponseDTO> records = PaginationUtils.getAllRecords(healthRecordClient::getAll, null, null);
	
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