package com.zprmts.tcc.ecommerce.controller.interfaces;

import com.zprmts.tcc.ecommerce.dto.perfume.PerfumeRequest;
import com.zprmts.tcc.ecommerce.dto.perfume.PerfumeResponse;
import com.zprmts.tcc.ecommerce.dto.perfume.PerfumeUpdate;
import com.zprmts.tcc.ecommerce.exception.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

public interface PerfumeControllerInterface {

    @Operation(summary = "Cadastrar um Perfume", description = "Cadastrar um Perfume")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Perfume criado com sucesso!"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    ResponseEntity<PerfumeResponse> create(@Valid @RequestBody PerfumeRequest perfumeRequest);

    @Operation(summary = "Atualizar um Perfume", description = "Atualiza um Perfume")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Perfume atualizado com sucesso!"),
                    @ApiResponse(responseCode = "404", description = "Perfume não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idPerfume}")
    ResponseEntity<PerfumeResponse> update(@Valid @PathVariable("idPerfume") Long idPerfume,
                                           @Valid @RequestBody PerfumeUpdate perfumeUpdate) throws RegraDeNegocioException;

    @Operation(summary = "Buscar um Perfume por Id", description = "Busca um Perfume por ID no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Perfume encontrado com sucesso!"),
                    @ApiResponse(responseCode = "404", description = "Perfume não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idPerfume}")
    public ResponseEntity<PerfumeResponse> getById(@PathVariable Long idPerfume) throws RegraDeNegocioException;


    @Operation(summary = "Deletar um perfume", description = "Deleta um perfume no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Perfume deletado com sucesso!"),
                    @ApiResponse(responseCode = "404", description = "Perfume não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idPerfume}")
    public ResponseEntity<Void> delete(@PathVariable Long idPerfume) throws RegraDeNegocioException;

    @Operation(summary = "Listar Perfumes", description = "Lista perfumes com base em filtros, informados ou não.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Perfumes listados com sucesso!"),
                    @ApiResponse(responseCode = "404", description = "Perfume não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/listar")
    public ResponseEntity<Page<PerfumeResponse>> list(@PageableDefault(page = 0, size = 10, sort = "idPerfume", direction = Sort.Direction.ASC) Pageable pageable,
                                                      @RequestParam @Nullable Long idPerfume,
                                                      @RequestParam @Nullable String name,
                                                      @RequestParam @Nullable String description,
                                                      @RequestParam @Nullable String categories) throws RegraDeNegocioException;
}
