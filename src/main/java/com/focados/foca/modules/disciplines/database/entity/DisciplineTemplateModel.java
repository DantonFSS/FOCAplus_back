package com.focados.foca.modules.disciplines.database.entity;

import com.focados.foca.modules.periods.database.entity.PeriodTemplateModel;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "discipline_templates")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class DisciplineTemplateModel {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "period_template_id", nullable = false)
    private PeriodTemplateModel periodTemplate;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT", nullable = true)
    private String notes;

    @OneToMany(
            mappedBy = "disciplineTemplate",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<DisciplineInstanceModel> disciplineInstances;
}