package com.focados.foca.modules.disciplines.domain.dtos.response;

import java.util.UUID;

public record DisciplineTeacherResponseDTO(
        UUID id,
        UUID disciplineInstanceId,
        String teacherName
) {}