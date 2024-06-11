package com.zprmts.tcc.ecommerce.repository;

import com.zprmts.tcc.ecommerce.domain.Perfume;
import com.zprmts.tcc.ecommerce.enums.StatusPerfumeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PerfumeRepository extends JpaRepository<Perfume, Long>, JpaSpecificationExecutor<Perfume> {

    List<Perfume> findByIdIn(List<Long> perfumesIds);

    @Transactional
    @Modifying
    @Query(value = """
            DELETE FROM REVIEW WHERE ID_PERFUME = :id
            """, nativeQuery = true
    )
    Integer deleteReviewsPerfume(@Param("id") Long id);
}
