package com.adrasha.analytics.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.adrasha.core.dto.filter.UserFilterDTO;
import com.adrasha.core.dto.response.HealthCenterResponseDTO;
import com.adrasha.core.dto.response.UserResponseDTO;
import com.adrasha.core.model.Role;
import com.adrasha.core.utils.PaginationUtils;
import com.adrasha.reports.client.HealthCenterDataClient;
import com.adrasha.reports.client.UserDataClient;

@Service
public class UserAnalyticsService {

	@Autowired
	private UserDataClient userDataClient;

	@Autowired
	private HealthCenterDataClient healthCenterDataClient;
	
	private long getCount(UserFilterDTO filterDTO) {
		return userDataClient.getCount(filterDTO).getOrDefault("count", 0L);
	}

	public long getTotalFamiles() {
		UserResponseDTO dataPage = userDataClient.getCurrentUserDetails();
		HealthCenterResponseDTO healthInfo = healthCenterDataClient.get(dataPage.getHealthCenterId());
		return healthInfo.getTotalFamilies();
	}

	public long getTotalUsers() {
		return this.getCount(null);
	}

	public Map<Role, Long> getRoleDistribution() {

		Set<Role> excludedRoles = Set.of(Role.USER, Role.SYSTEM);

		Sort sort = Sort.by(Direction.DESC, "name");
		List<UserResponseDTO> data = PaginationUtils.getAllRecords(userDataClient::getAll, null, sort);

		Map<Role, Long> result = data.stream()
					.flatMap(user-> user.getRoles().stream())
					.filter(role -> !excludedRoles.contains(role))
					.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		
		return result;
	}
}
