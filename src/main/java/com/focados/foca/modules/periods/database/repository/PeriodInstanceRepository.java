package com.focados.foca.modules.periods.database.repository;

import com.focados.foca.modules.periods.database.entity.PeriodInstanceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PeriodInstanceRepository extends JpaRepository<PeriodInstanceModel, UUID> {
    List<PeriodInstanceModel> findByUserCourseIdOrderByPositionAsc(UUID userCourseId);
    Optional<PeriodInstanceModel> findByUserCourseIdAndPeriodTemplateId(UUID userCourseId, UUID periodTemplateId);
    long countByPeriodTemplateId(UUID periodTemplateId);

    @Query("SELECT pi FROM PeriodInstanceModel pi " +
            "WHERE pi.userCourse.id = :userCourseId " +
            "AND (pi.periodTemplate.archived = false OR pi.periodTemplate.archived IS NULL) " +
            "ORDER BY pi.position ASC")
    List<PeriodInstanceModel> findByUserCourseIdWithNonArchivedTemplates(@Param("userCourseId") UUID userCourseId);

}