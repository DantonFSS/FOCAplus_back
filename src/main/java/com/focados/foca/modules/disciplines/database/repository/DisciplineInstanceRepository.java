package com.focados.foca.modules.disciplines.database.repository;

import com.focados.foca.modules.disciplines.database.entity.DisciplineInstanceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DisciplineInstanceRepository extends JpaRepository<DisciplineInstanceModel, UUID> {
    List<DisciplineInstanceModel> findByUserCourseId(UUID userCourseId);
    List<DisciplineInstanceModel> findByPeriodInstanceId(UUID periodInstanceId);
    long countByDisciplineTemplateId(UUID disciplineTemplateId);
    Optional<DisciplineInstanceModel> findByUserCourseIdAndDisciplineTemplateId(UUID userCourseId, UUID disciplineTemplateId);

    @Query("SELECT di FROM DisciplineInstanceModel di " +
            "WHERE di.periodInstance.id = :periodInstanceId " +
            "AND (di.disciplineTemplate.archived = false OR di.disciplineTemplate.archived IS NULL)")
    List<DisciplineInstanceModel> findByPeriodInstanceIdWithNonArchivedTemplates(@Param("periodInstanceId") UUID periodInstanceId);

}