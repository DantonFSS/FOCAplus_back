package com.focados.foca.modules.disciplines.domain.dtos.request;

import java.time.LocalTime;
import java.util.UUID;

public record CreateDisciplineScheduleDTO(
        UUID disciplineInstanceId,
        int weekday,
        LocalTime startTime,
        LocalTime endTime
) {}