package com.focados.foca.modules.disciplines.domain.services;

import com.focados.foca.modules.courses.database.entity.UserCourseModel;
import com.focados.foca.modules.courses.database.repository.UserCourseRepository;
import com.focados.foca.modules.disciplines.database.repository.DisciplineInstanceRepository;
import com.focados.foca.modules.disciplines.domain.dtos.mappers.DisciplineTemplateMapper;
import com.focados.foca.modules.disciplines.domain.dtos.request.BatchCreateDisciplineTemplateDto;
import com.focados.foca.modules.disciplines.domain.dtos.request.CreateDisciplineTemplateDto;
import com.focados.foca.modules.disciplines.domain.dtos.request.UpdateDisciplineTemplateDto;
import com.focados.foca.modules.disciplines.domain.dtos.response.DisciplineTemplateResponseDto;
import com.focados.foca.modules.disciplines.database.entity.DisciplineTemplateModel;
import com.focados.foca.modules.periods.database.entity.PeriodTemplateModel;
import com.focados.foca.modules.disciplines.database.repository.DisciplineTemplateRepository;
import com.focados.foca.modules.periods.database.repository.PeriodTemplateRepository;
import com.focados.foca.modules.users.domain.services.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DisciplineTemplateService {

    private final DisciplineTemplateRepository disciplineTemplateRepository;
    private final PeriodTemplateRepository periodTemplateRepository;
    private final DisciplineInstanceService disciplineInstanceService;
    private final DisciplineInstanceRepository disciplineInstanceRepository;
    private final UserCourseRepository userCourseRepository;

    public DisciplineTemplateResponseDto create(CreateDisciplineTemplateDto dto) {
        PeriodTemplateModel periodTemplate = periodTemplateRepository.findById(dto.getPeriodTemplateId())
                .orElseThrow(() -> new IllegalArgumentException("Período não encontrado"));

        DisciplineTemplateModel discipline = new DisciplineTemplateModel();
        discipline.setPeriodTemplate(periodTemplate);
        discipline.setName(dto.getName());

        disciplineTemplateRepository.save(discipline);

        disciplineInstanceService.createDisciplineInstanceForOwner(discipline);

        return DisciplineTemplateMapper.toResponse(discipline);
    }

    public List<DisciplineTemplateResponseDto> batchCreate(BatchCreateDisciplineTemplateDto dto) {
        var period = periodTemplateRepository.findById(dto.getPeriodTemplateId())
                .orElseThrow(() -> new IllegalArgumentException("Período não encontrado"));

        List<DisciplineTemplateModel> templates = new ArrayList<>();
        for (String name : dto.getNames()) {
            DisciplineTemplateModel discipline = new DisciplineTemplateModel();
            discipline.setPeriodTemplate(period);
            discipline.setName(name);
            disciplineTemplateRepository.save(discipline);
            templates.add(discipline);
        }
        disciplineInstanceService.createDisciplineInstancesForOwnerBatch(templates);

        return templates.stream()
                .map(DisciplineTemplateMapper::toResponse)
                .toList();
    }

    public List<DisciplineTemplateResponseDto> getAll() {
        return disciplineTemplateRepository.findAll()
                .stream()
                .map(DisciplineTemplateMapper::toResponse)
                .toList();
    }

    public List<DisciplineTemplateResponseDto> getAllByPeriodTemplateId(UUID periodTemplateId) {
        return disciplineTemplateRepository.findByPeriodTemplateId(periodTemplateId)
                .stream()
                .map(DisciplineTemplateMapper::toResponse)
                .toList();
    }

    public DisciplineTemplateResponseDto getById(UUID id) {
        DisciplineTemplateModel discipline = disciplineTemplateRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Disciplina não encontrada"));
        return DisciplineTemplateMapper.toResponse(discipline);
    }

    public DisciplineTemplateResponseDto update(UUID id, UpdateDisciplineTemplateDto dto) {
        DisciplineTemplateModel discipline = disciplineTemplateRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Disciplina não encontrada"));

        discipline.setName(dto.getName());
        discipline.setNotes(dto.getNotes());

        disciplineTemplateRepository.save(discipline);
        return DisciplineTemplateMapper.toResponse(discipline);
    }

    public void deleteById(UUID id) {
        DisciplineTemplateModel template = disciplineTemplateRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Disciplina não encontrada"));

        disciplineTemplateRepository.deleteById(id);
    }
}