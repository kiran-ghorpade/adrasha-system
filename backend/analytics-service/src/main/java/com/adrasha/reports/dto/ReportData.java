package com.adrasha.reports.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class ReportData {
    private List<String> headers;
    private List<List<Object>> rows;
}
