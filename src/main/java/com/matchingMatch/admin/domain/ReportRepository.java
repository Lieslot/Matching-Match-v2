package com.matchingMatch.admin.domain;

import com.matchingMatch.admin.Report;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {

    Slice<Report> findAllByIdLessThanOrderByIdDesc(Long id, Pageable pageable);

}
