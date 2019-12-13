package com.psybergate.absaassessment.mpatho.integration.entity;

import com.psybergate.absaassessment.mpatho.integration.constant.Currency;
import com.psybergate.absaassessment.mpatho.integration.constant.MessageStatus;
import com.psybergate.absaassessment.mpatho.integration.entity.common.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "Bill")
public class Bill extends BaseEntity {

    @Getter
    @Setter
    @Column(name = "transactionReference")
    private String transactionReference;

    @Getter
    @Setter
    @Column(name = "clientSwiftAddress")
    private String clientSwiftAddress;

    @Getter
    @Setter
    @Column(name = "messageStatus")
    @Enumerated(value = EnumType.STRING)
    private MessageStatus messageStatus;

    @Getter
    @Setter
    @Column(name = "Currency")
    @Enumerated(value = EnumType.STRING)
    private Currency currency;

    @Getter
    @Setter
    @Column(name = "Amount")
    private Double amount;

    @Getter
    @Setter
    @Column(name = "dateTimeCreated")
    private LocalDateTime dateTimeCreated;
}
