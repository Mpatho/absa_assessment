package com.psybergate.absaassessment.mpatho.integration.entity;

import com.psybergate.absaassessment.mpatho.integration.entity.common.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "holiday")
public class Holiday extends BaseEntity {

    @Getter
    @Setter
    @Column(name = "date", nullable = false)
    private LocalDate date;
}
