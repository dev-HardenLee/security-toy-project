package com.example.test.dto;

import lombok.*;

import java.util.List;
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class OrgChartDTO {

    private String name;

    private String title;

    private List<OrgChartDTO> children;

}// OrgChartDTO
