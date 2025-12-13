package com.focados.foca.modules.periods.database.entity;


import com.focados.foca.modules.courses.database.entity.UserCourseModel;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "period_instances")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PeriodInstanceModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_course_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserCourseModel userCourse;

    @ManyToOne(optional = true)
    @JoinColumn(name = "period_template_id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PeriodTemplateModel periodTemplate;

    @Column(name = "name")
    private String name;

    @Column(name = "position")
    private Integer position;

    @Column(name = "planned_start")
    private LocalDate plannedStart;

    @Column(name = "planned_end")
    private LocalDate plannedEnd;
}