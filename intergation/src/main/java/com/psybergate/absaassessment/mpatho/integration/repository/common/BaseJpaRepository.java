package com.psybergate.absaassessment.mpatho.integration.repository.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseJpaRepository<T> extends JpaRepository<T, Long> {
}
