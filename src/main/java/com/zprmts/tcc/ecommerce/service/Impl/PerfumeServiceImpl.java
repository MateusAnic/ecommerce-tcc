package com.zprmts.tcc.ecommerce.service.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.mail.iap.ByteArray;
import com.sun.mail.util.BASE64EncoderStream;
import com.zprmts.tcc.ecommerce.domain.Perfume;
import com.zprmts.tcc.ecommerce.dto.perfume.PerfumeRequest;
import com.zprmts.tcc.ecommerce.dto.perfume.PerfumeResponse;
import com.zprmts.tcc.ecommerce.dto.perfume.PerfumeUpdate;
import com.zprmts.tcc.ecommerce.enums.StatusPerfumeEnum;
import com.zprmts.tcc.ecommerce.exception.RegraDeNegocioException;
import com.zprmts.tcc.ecommerce.repository.PerfumeRepository;
import com.zprmts.tcc.ecommerce.repository.specification.PerfumeSpecifications;
import com.zprmts.tcc.ecommerce.service.PerfumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.zprmts.tcc.ecommerce.constants.ErrorMessage.PERFUME_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PerfumeServiceImpl implements PerfumeService {

    private final PerfumeRepository perfumeRepository;
    private final PerfumeSpecifications perfumeSpecifications;
    private final ObjectMapper objectMapper;

    @Override
    public PerfumeResponse create(PerfumeRequest perfumeRequest) {
        Perfume perfume = objectMapper.convertValue(perfumeRequest, Perfume.class);
        save(perfume);
        PerfumeResponse perfumeResponse = objectMapper.convertValue(perfume, PerfumeResponse.class);
        return perfumeResponse;
    }

    @Override
    public PerfumeResponse update(Long idPerfume, PerfumeUpdate perfumeUpdate) throws RegraDeNegocioException {
        Perfume perfume = getById(idPerfume);
        Perfume perfumeUpdateGetFoto = objectMapper.convertValue(perfumeUpdate, Perfume.class);
        if (!Objects.isNull(perfumeUpdateGetFoto.getFoto())) {
            perfume.setFoto(perfumeUpdateGetFoto.getFoto());
        }
        perfume.setName(perfumeUpdate.getName());
        perfume.setDescription(perfumeUpdate.getDescription());
        perfume.setPrice(perfumeUpdate.getPrice());
        perfume.setCategories(perfumeUpdate.getCategories());
        perfume = save(perfume);
        PerfumeResponse perfumeResponse = objectMapper.convertValue(perfume, PerfumeResponse.class);
        return perfumeResponse;
    }

    @Override
    public Perfume getById(Long perfumeId) throws RegraDeNegocioException {
        return perfumeRepository.findById(perfumeId)
                .orElseThrow(() -> new RegraDeNegocioException(PERFUME_NOT_FOUND));
    }

    @Override
    public PerfumeResponse findById(Long perfumeId) throws RegraDeNegocioException {
        return objectMapper.convertValue(getById(perfumeId), PerfumeResponse.class);
    }

    @Override
    public List<PerfumeResponse> findByIdIn (List<Long> idPerfumeList) {
        return perfumeRepository.findByIdIn(idPerfumeList)
                .stream().map(perfume -> objectMapper.convertValue(perfume, PerfumeResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<PerfumeResponse> list(Pageable pageable,
                                      @Nullable Long idPerfume,
                                      @Nullable String name,
                                      @Nullable String description,
                                      @Nullable String categories) {
        StatusPerfumeEnum statusPerfumeEnum = StatusPerfumeEnum.ATIVO;
        Specification spec = perfumeSpecifications.builderSpecification(idPerfume, name, description, categories, statusPerfumeEnum);
        Page<Perfume> perfumes = perfumeRepository.findAll(spec, pageable);

        return perfumes.map(perfume -> objectMapper.convertValue(perfume, PerfumeResponse.class));
    }

    @Override
    public Perfume save(Perfume perfume) {
        return perfumeRepository.save(perfume);
    }

    @Override
    public String delete(Long perfumeId) throws RegraDeNegocioException {
        Perfume perfume = perfumeRepository.findById(perfumeId)
                .orElseThrow(() -> new RegraDeNegocioException(PERFUME_NOT_FOUND));
        perfume.setAtivo(StatusPerfumeEnum.INATIVO);
        save(perfume);
        return "Perfume deleted successfully";
    }
}
