package com.focados.foca.modules.disciplines.domain.dtos.mappers;

import com.focados.foca.modules.disciplines.database.entity.DisciplineTemplateModel;
import com.focados.foca.modules.disciplines.domain.dtos.response.DisciplineTemplateResponseDto;

public class DisciplineTemplateMapper {
    public static DisciplineTemplateResponseDto toResponse(DisciplineTemplateModel model) {
        DisciplineTemplateResponseDto dto = new DisciplineTemplateResponseDto();
        dto.setId(model.getId());
        dto.setPeriodTemplateId(model.getPeriodTemplate().getId());
        dto.setName(model.getName());
        dto.setNotes(model.getNotes() != null ? model.getNotes() : "");
        return dto;
    }
}