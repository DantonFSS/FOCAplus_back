package com.focados.foca.modules.courses.domain.dtos.request;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UpdateCourseInfoDto {

    private String institutionName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String address;
    private Boolean online;
    private List<String> phones;
    private List<String> emails;
}
