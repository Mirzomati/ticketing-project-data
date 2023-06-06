package com.cydeo.entity;


import com.cydeo.enums.Status;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "projects")
@Where(clause = "is_deleted=false")
public class Project  extends BaseEntity {

    private String projectName;
    private String projectCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private User assignedManager;

    @Column(columnDefinition = "DATE")
    private LocalDate startDate;
    @Column(columnDefinition = "DATE")
    private LocalDate endDate;

    private String projectDetail;

    @Enumerated(EnumType.STRING)
    private Status projectStatus;


}
