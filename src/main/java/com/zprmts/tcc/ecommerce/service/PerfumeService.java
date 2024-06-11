package com.zprmts.tcc.ecommerce.service;

import com.zprmts.tcc.ecommerce.domain.Perfume;
import com.zprmts.tcc.ecommerce.dto.perfume.PerfumeRequest;
import com.zprmts.tcc.ecommerce.dto.perfume.PerfumeResponse;
import com.zprmts.tcc.ecommerce.dto.perfume.PerfumeUpdate;
import com.zprmts.tcc.ecommerce.exception.RegraDeNegocioException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PerfumeService {

    PerfumeResponse create(PerfumeRequest perfumeRequest);

    PerfumeResponse update(Long idPerfume, PerfumeUpdate perfumeUpdate) throws RegraDeNegocioException;

    Perfume getById(Long perfumeId) throws RegraDeNegocioException;

    PerfumeResponse findById(Long perfumeId) throws RegraDeNegocioException;

    List<PerfumeResponse> findByIdIn (List<Long> idPerfumeList);

    Page<PerfumeResponse> list(Pageable pageable,
                               @Nullable Long idPerfume,
                               @Nullable String name,
                               @Nullable String description,
                               @Nullable String categories);

    Perfume save(Perfume perfume);

    String delete(Long perfumeId) throws RegraDeNegocioException;
}
