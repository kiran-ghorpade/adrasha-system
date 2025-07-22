### Controller

```java
@RestController
@RequestMapping("/analytics")
@SecurityRequirement(name = "BearerAuthentication")
@ApiResponses({
    @ApiResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
})
@Tag(name = "Reports")
public class ReportsController {

    @Autowired
    private ReportService reportService;

    @PostMapping("/reports/generate")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = ReportResponseDTO.class)))
    public ResponseEntity<ReportResponseDTO> generateReport(@RequestBody ReportRequestDTO request,
                                                            @RequestParam String reportType) {
        ReportResponseDTO response = reportService.generateReport(request, reportType);
        return ResponseEntity.ok(response);
    }
}
```

## service

```java
@Service
public class ReportService {

    @Autowired
    private ReportDataService reportDataService;

    @Autowired
    private ReportFormattingService reportFormattingService;

    @Autowired
    private ReportStorageService reportStorageService;

    public ReportResponseDTO generateReport(ReportRequestDTO request, String reportType) {
        List<Map<String, Object>> reportData = reportDataService.fetchData(request, reportType);
        String formattedReport = reportFormattingService.formatReport(reportData, request);
        String reportFile = reportStorageService.storeReport(formattedReport);

        return new ReportResponseDTO("Report generated successfully", reportFile);
    }
}
```

## data service

```java
@Service
public class ReportDataService {

    @Autowired
    private FamilyDataClient familyDataClient;

    @Autowired
    private HealthCenterDataClient healthCenterDataClient;

    @Autowired
    private MemberDataClient memberDataClient;

    public List<Map<String, Object>> fetchData(ReportRequestDTO request, String reportType) {
        List<Map<String, Object>> data = new ArrayList<>();

        if ("admin".equalsIgnoreCase(reportType)) {
            // Fetch Admin data from different clients
            data.addAll(fetchAdminData(request));
        } else if ("asha".equalsIgnoreCase(reportType)) {
            // Fetch ASHA data from different clients
            data.addAll(fetchAshaData(request));
        }
        // Add more conditions as needed

        return data;
    }

    private List<Map<String, Object>> fetchAdminData(ReportRequestDTO request) {
        // Logic to fetch data for Admin report from various clients
    }

    private List<Map<String, Object>> fetchAshaData(ReportRequestDTO request) {
        // Logic to fetch data for ASHA report from various clients
    }
}
```

## formatting service

```java
@Service
public class ReportFormattingService {

    public String formatReport(List<Map<String, Object>> reportData, ReportRequestDTO request) {
        // 1. Create the report structure (title, table, etc.)
        ReportGenerator generator = ReportGenerator.createDocument()
                .addTitle("Report Title: " + request.getEntityType())
                .addDefaultHeader() // Add header here
                .addTable(ReportTableGenerator.createTable()
                    .addHeaders(request.getFields())
                    .addData(reportData)
                    .build()) // Add the table with data
                .addDefaultFooter(); // Add footer

        // 2. Generate the PDF and return Base64 string
        return generator.generatedPdf();
    }
}
```

## storage service

```java
@Service
public class ReportStorageService {

    @Value("${report.storage.path}")
    private String storagePath;

    public String storeReport(String base64Report) {
        // Logic to save the Base64 encoded report to disk, cloud, or database
        String filePath = storagePath + "/report_" + System.currentTimeMillis() + ".pdf";
        // Save the file at the given path (either as a Base64 string or PDF file)
        saveToFileSystem(filePath, base64Report);
        return filePath;
    }

    private void saveToFileSystem(String filePath, String base64Report) {
        // Logic to decode Base64 and save it as a .pdf file
    }
}
```
