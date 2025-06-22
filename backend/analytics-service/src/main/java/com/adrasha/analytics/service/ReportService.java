//package com.adrasha.analytics.service;
//
//import java.lang.reflect.Field;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.stereotype.Service;
//
//import com.adrasha.analytics.client.FamilyDataClient;
//import com.adrasha.analytics.client.HealthCenterDataClient;
//import com.adrasha.analytics.client.HealthRecordClient;
//import com.adrasha.analytics.client.LocationClient;
//import com.adrasha.analytics.client.MemberDataClient;
//import com.adrasha.analytics.client.NCDClient;
//import com.adrasha.analytics.client.UserDataClient;
//import com.adrasha.analytics.dto.ReportRequestDTO;
//import com.adrasha.analytics.dto.ReportResponseDTO;
//import com.adrasha.analytics.utils.ReportGenerator;
//import com.adrasha.analytics.utils.ReportTableGenerator;
//import com.adrasha.core.filter.dto.FamilyDataFilterDTO;
//import com.adrasha.core.filter.dto.HealthCenterFilterDTO;
//import com.adrasha.core.filter.dto.HealthRecordFilterDTO;
//import com.adrasha.core.filter.dto.LocationFilterDTO;
//import com.adrasha.core.filter.dto.MemberDataFilterDTO;
//import com.adrasha.core.filter.dto.NCDFilterDTO;
//import com.adrasha.core.filter.dto.UserFilterDTO;
//import com.adrasha.core.response.dto.FamilyDataResponseDTO;
//import com.adrasha.core.response.dto.HealthCenterResponseDTO;
//import com.adrasha.core.response.dto.HealthRecordResponseDTO;
//import com.adrasha.core.response.dto.LocationResponseDTO;
//import com.adrasha.core.response.dto.MemberDataResponseDTO;
//import com.adrasha.core.response.dto.NCDResponseDTO;
//import com.adrasha.core.response.dto.UserResponseDTO;
//
//@Service
//public class ReportService {
//
//    @Autowired
//    private FamilyDataClient familyDataClient;
//
//    @Autowired
//    private MemberDataClient memberDataClient;
//
//    @Autowired
//    private HealthRecordClient healthRecordClient;
//
//    @Autowired
//    private HealthCenterDataClient healthCenterDataClient;
//
//    @Autowired
//    private LocationClient locationClient;
//
//    @Autowired
//    private NCDClient ncdClient;
//
//    @Autowired
//    private UserDataClient userDataClient;
//    
//    @Autowired
//    private ModelMapper modelMapper;
//
//    public ReportResponseDTO generateAshaReport(ReportRequestDTO request) {
//    	
//        List<Map<String, Object>> data = fetchAshaData(request);
//        
//        String report = ReportGenerator.createDocument()
//        					.addTitle("Report Title : "+ request.getEntityType())
//        					.addDefaultHeader()
//        					.addTable(
//        							ReportTableGenerator
//        							.createTable()
//        							.addHeaders(request.getFields())
//        							.addData(data)
//        							.build())
//        					.addDefaultFooter()
//        					.generatedPdf();
//
//        return ReportResponseDTO.builder()
//                .filename(generateFilename(request.getEntityType()))
//                .content(report)
//                .build();
//    }
//
//    private List<Map<String, Object>> fetchAdminData(ReportRequestDTO request) {
//        PageRequest pageable = PageRequest.of(0, Integer.MAX_VALUE);
//        
//        List<Map<String, Object>> result = Collections.emptyList();
//
//        HealthCenterFilterDTO hcFilter = modelMapper.map(request, HealthCenterFilterDTO.class);
//        Page<HealthCenterResponseDTO> hcPage = healthCenterDataClient.getAll(hcFilter, pageable);
//        result.addAll( hcPage.getContent().stream()
//                .map(this::toMap)
//                .collect(Collectors.toList()));
//
//        LocationFilterDTO locFilter = modelMapper.map(request, LocationFilterDTO.class);
//        Page<LocationResponseDTO> locPage = locationClient.getAll(locFilter, pageable);
//        result.addAll(locPage.getContent().stream()
//                .map(this::toMap)
//                .collect(Collectors.toList()));
//        
//        NCDFilterDTO ncdFilter =  modelMapper.map(request, NCDFilterDTO.class);
//        Page<NCDResponseDTO> ncdPage = ncdClient.getAll(ncdFilter, pageable);
//        result.addAll( ncdPage.getContent().stream()
//                .map(this::toMap)
//                .collect(Collectors.toList()));
//  
//        UserFilterDTO userFilter = modelMapper.map(request, UserFilterDTO.class);
//        Page<UserResponseDTO> userPage = userDataClient.getAll(userFilter, pageable);
//        result.addAll( userPage.getContent().stream()
//                .map(this::toMap)
//                .collect(Collectors.toList()));
//   
//        return result;
//    }
//
//    private List<Map<String, Object>> fetchAshaData(ReportRequestDTO request) {
//        PageRequest pageable = PageRequest.of(0, Integer.MAX_VALUE);
//        
//        List<Map<String, Object>> result = Collections.emptyList();
//
//        FamilyDataFilterDTO familyFilter = modelMapper.map(request, FamilyDataFilterDTO.class);
//        Page<FamilyDataResponseDTO> familyPage = familyDataClient.getAll(familyFilter, pageable);
//        result.addAll(familyPage.getContent().stream()
//                .map(this::toMap)
//                .collect(Collectors.toList()));
//
//        MemberDataFilterDTO memberFilter = modelMapper.map(request, MemberDataFilterDTO.class);
//        Page<MemberDataResponseDTO> memberPage = memberDataClient.getAll(memberFilter, pageable);
//        result.addAll( memberPage.getContent().stream()
//                .map(this::toMap)
//                .collect(Collectors.toList()));
//
//        HealthRecordFilterDTO healthRecordFilter = modelMapper.map(request, HealthRecordFilterDTO.class);
//        Page<HealthRecordResponseDTO> healthRecordPage = healthRecordClient.getAll(healthRecordFilter, pageable);
//        result.addAll( healthRecordPage.getContent().stream()
//                .map(this::toMap)
//                .collect(Collectors.toList()));
//
//        return result;
//    }
//
//    private Object convertValue(String value, Class<?> type) {
//        if (type == UUID.class) {
//        	UUID id;
//        	id.toString();
//            return UUID.fromString(value);
//        } else if (type.isEnum()) {
//            return Enum.valueOf((Class<Enum>) type, value.toUpperCase());
//        }
//        return value;
//    }
//
//    private Map<String, Object> toMap(Object dto) {
//        Map<String, Object> map = new HashMap<>();
//        for (Field field : dto.getClass().getDeclaredFields()) {
//            field.setAccessible(true);
//            try {
//                map.put(field.getName(), field.get(dto));
//            } catch (IllegalAccessException e) {
//               throw new RuntimeException("Error during mapping fetched data ", e);
//            }
//        }
//        return map;
//    }
//
//    private String generateFilename(String entityType) {
//        return entityType + "_report_" + System.currentTimeMillis();
//    }
//}