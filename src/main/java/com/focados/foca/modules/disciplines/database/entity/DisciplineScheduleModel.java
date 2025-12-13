package com.focados.foca.modules.disciplines.database.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "discipline_schedules")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class DisciplineScheduleModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "discipline_instance_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private DisciplineInstanceModel disciplineInstance;

    @Column(nullable = false)
    private int weekday;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column
    private LocalTime endTime;
}