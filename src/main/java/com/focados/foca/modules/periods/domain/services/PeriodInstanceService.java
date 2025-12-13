package com.focados.foca.modules.periods.domain.services;

import com.focados.foca.modules.courses.database.entity.enums.UserCourseRole;
import com.focados.foca.modules.courses.database.repository.UserCourseRepository;
import com.focados.foca.modules.disciplines.domain.services.DisciplineInstanceService;
import com.focados.foca.modules.periods.database.entity.PeriodInstanceModel;
import com.focados.foca.modules.periods.database.entity.PeriodTemplateModel;
import com.focados.foca.modules.periods.database.repository.PeriodInstanceRepository;
import com.focados.foca.modules.periods.database.repository.PeriodTemplateRepository;
import com.focados.foca.modules.courses.database.entity.UserCourseModel;
import com.focados.foca.modules.periods.domain.dtos.mappers.PeriodInstanceMapper;
import com.focados.foca.modules.periods.domain.dtos.request.CreatePeriodInstanceDto;
import com.focados.foca.modules.periods.domain.dtos.request.UpdatePeriodInstanceDto;
import com.focados.foca.modules.periods.domain.dtos.response.PeriodInstanceResponseDto;
import com.focados.foca.modules.users.domain.services.AuthUtil;
import com.focados.foca.shared.common.utils.exceptions.UserNotAllowedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PeriodInstanceService {

    private final PeriodInstanceRepository periodInstanceRepository;
    private final PeriodTemplateRepository periodTemplateRepository;
    private final UserCourseRepository userCourseRepository;
    private final PeriodTemplateService periodTemplateService;
    private final DisciplineInstanceService disciplineInstanceService;

    public void createPeriodInstancesForUserCourse(UserCourseModel userCourse) {
        List<PeriodTemplateModel> templates = periodTemplateRepository
                .findByCourseTemplateIdOrderByPositionAsc(userCourse.getCourseTemplate().getId());

        for (PeriodTemplateModel template : templates) {
            PeriodInstanceModel instance = new PeriodInstanceModel();
            instance.setUserCourse(userCourse);
            instance.setPeriodTemplate(template);
            instance.setName(template.getName());
            instance.setPosition(template.getPosition());
            instance.setPlannedStart(template.getPlannedStart());
            instance.setPlannedEnd(template.getPlannedEnd());
            periodInstanceRepository.save(instance);

            disciplineInstanceService.createDisciplineInstancesForPeriod(instance, userCourse);
        }
    }

    public PeriodInstanceResponseDto create(CreatePeriodInstanceDto dto) {
        UserCourseModel userCourse = userCourseRepository.findById(dto.getUserCourseId())
                .orElseThrow(() -> new IllegalArgumentException("UserCourse não encontrado"));

        List<PeriodInstanceModel> existingPeriods = periodInstanceRepository
                .findByUserCourseIdOrderByPositionAsc(dto.getUserCourseId());

        PeriodTemplateModel auxPeriodTemplate = periodTemplateService.buildNextPeriod(
                userCourse.getCourseTemplate(),
                existingPeriods.stream().map(PeriodInstanceMapper::toTemplateAux).toList()
        );

        if (auxPeriodTemplate == null) {
            throw new IllegalArgumentException("Semestre excede a data final do curso.");
        }

        PeriodInstanceModel periodInstance = new PeriodInstanceModel();
        periodInstance.setUserCourse(userCourse);
        periodInstance.setPeriodTemplate(null);
        periodInstance.setName(auxPeriodTemplate.getName());
        periodInstance.setPosition(existingPeriods.size() + 1);
        periodInstance.setPlannedStart(auxPeriodTemplate.getPlannedStart());
        periodInstance.setPlannedEnd(auxPeriodTemplate.getPlannedEnd());

        periodInstanceRepository.save(periodInstance);
        return PeriodInstanceMapper.toResponse(periodInstance);
    }

    public List<PeriodInstanceResponseDto> getAll() {
        return periodInstanceRepository.findAll()
                .stream()
                .map(PeriodInstanceMapper::toResponse)
                .toList();
    }

    public List<PeriodInstanceResponseDto> getAllByUserCourseId(UUID userCourseId) {
        return periodInstanceRepository
                .findByUserCourseIdWithNonArchivedTemplates(userCourseId)
                .stream()
                .map(PeriodInstanceMapper::toResponse)
                .toList();
    }

    public PeriodInstanceResponseDto getById(UUID id) {
        PeriodInstanceModel instance = periodInstanceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Período não encontrado"));
        return PeriodInstanceMapper.toResponse(instance);
    }

    public PeriodInstanceResponseDto update(UUID id, UpdatePeriodInstanceDto dto) {
        PeriodInstanceModel instance = periodInstanceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Período não encontrado"));

        instance.setName(dto.getName());
        instance.setPlannedStart(dto.getPlannedStart());
        instance.setPlannedEnd(dto.getPlannedEnd());

        periodInstanceRepository.save(instance);
        return PeriodInstanceMapper.toResponse(instance);
    }

    public void deleteById(UUID id) {
        PeriodInstanceModel instance = periodInstanceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Período não encontrado"));

        UUID userId = AuthUtil.getAuthenticatedUserId();
        if (!instance.getUserCourse().getUser().getId().equals(userId)) {
            throw new UserNotAllowedException();
        }

        UserCourseModel userCourse = instance.getUserCourse();
        PeriodTemplateModel template = instance.getPeriodTemplate();

        if (template != null && userCourse.getRole() == UserCourseRole.OWNER) {
            long instanceCount = periodInstanceRepository.countByPeriodTemplateId(template.getId());

            if (instanceCount > 1) {
                template.setArchived(true);
                periodTemplateRepository.save(template);
            } else {
                periodTemplateRepository.delete(template);
                return;
            }
        }

        periodInstanceRepository.deleteById(id);
    }
}