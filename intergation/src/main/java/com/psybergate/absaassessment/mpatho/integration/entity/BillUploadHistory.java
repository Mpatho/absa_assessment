package com.psybergate.absaassessment.mpatho.integration.entity;

import com.psybergate.absaassessment.mpatho.integration.entity.common.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "BillUploadHistory")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BillUploadHistory extends BaseEntity {

    @Getter
    @Setter
    @Column(name = "content")
    private String content;

    @Getter
    @Setter
    @Column(name = "submittedDate")
    private LocalDate submittedDate;

}
