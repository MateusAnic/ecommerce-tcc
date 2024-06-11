package com.zprmts.tcc.ecommerce.controller;

import com.zprmts.tcc.ecommerce.controller.interfaces.PerfumeControllerInterface;
import com.zprmts.tcc.ecommerce.dto.perfume.PerfumeRequest;
import com.zprmts.tcc.ecommerce.dto.perfume.PerfumeResponse;
import com.zprmts.tcc.ecommerce.dto.perfume.PerfumeUpdate;
import com.zprmts.tcc.ecommerce.exception.RegraDeNegocioException;
import com.zprmts.tcc.ecommerce.service.Impl.PerfumeServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/perfume")
@Tag(name = "Perfume")
public class PerfumeController implements PerfumeControllerInterface {

    private final PerfumeServiceImpl perfumeService;
    @Override
    @PostMapping()
    public ResponseEntity<PerfumeResponse> create(@Valid @RequestBody PerfumeRequest perfumeRequest) {
        return new ResponseEntity<>(perfumeService.create(perfumeRequest), HttpStatus.OK);
    }

    @Override
    @PutMapping("/{idPerfume}")
    public ResponseEntity<PerfumeResponse> update(@PathVariable Long idPerfume,
                                                          @Valid @RequestBody PerfumeUpdate perfumeUpdate) throws RegraDeNegocioException {
        return new ResponseEntity<>(perfumeService.update(idPerfume, perfumeUpdate), HttpStatus.OK);
    }

    @Override
    @GetMapping("/listar")
    public ResponseEntity<Page<PerfumeResponse>> list(@PageableDefault(page = 0, size = 10) Pageable pageable,
                                                @RequestParam @Nullable Long idPerfume,
                                                @RequestParam @Nullable String name,
                                                @RequestParam @Nullable String description,
                                                @RequestParam @Nullable String categories) throws RegraDeNegocioException {
        return new ResponseEntity<>(perfumeService.list(pageable, idPerfume, name, description, categories), HttpStatus.OK);
    }

    @Override
    @GetMapping("/{idPerfume}")
    public ResponseEntity<PerfumeResponse> getById(@PathVariable Long idPerfume) throws RegraDeNegocioException {
        return new ResponseEntity<>(perfumeService.findById(idPerfume), HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{idPerfume}")
    public ResponseEntity<Void> delete(@PathVariable Long idPerfume) throws RegraDeNegocioException {
        perfumeService.delete(idPerfume);
        return ResponseEntity.noContent().build();
    }
}
