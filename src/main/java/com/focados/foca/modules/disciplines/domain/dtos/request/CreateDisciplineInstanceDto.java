package com.focados.foca.modules.disciplines.domain.dtos.request;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class CreateDisciplineInstanceDto {
    private UUID disciplineTemplateId;
    private UUID userCourseId;
    private UUID periodInstanceId;
    private LocalDate plannedStart;
    private LocalDate plannedEnd;
    private Integer assessmentsCount;
}