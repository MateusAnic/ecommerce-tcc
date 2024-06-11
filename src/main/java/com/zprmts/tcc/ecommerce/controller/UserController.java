package com.zprmts.tcc.ecommerce.controller;

import com.zprmts.tcc.ecommerce.dto.perfume.PerfumeResponse;
import com.zprmts.tcc.ecommerce.dto.user.UpdateUserRequest;
import com.zprmts.tcc.ecommerce.dto.user.UserResponse;
import com.zprmts.tcc.ecommerce.exception.RegraDeNegocioException;
import com.zprmts.tcc.ecommerce.service.Impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Tag(name = "User")
public class UserController {

    private final UserServiceImpl usuarioService;

    @GetMapping("/usuario-logado")
    public ResponseEntity<UserResponse> getUserInfo() throws Exception {
        return new ResponseEntity<>(usuarioService.getUserResponseLogado(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<UserResponse> updateMyUser(@Valid @RequestBody UpdateUserRequest request) throws Exception {
        return new ResponseEntity<>(usuarioService.updateMyUser(request), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateAnotherUser(@NonNull @PathVariable Long id, @Valid @RequestBody UpdateUserRequest request) throws Exception {
        return new ResponseEntity<>(usuarioService.update(id, request), HttpStatus.OK);
    }

    @GetMapping("/listar")
    public ResponseEntity<Page<UserResponse>> list(@PageableDefault(page = 0, size = 10) Pageable pageable) throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.getAllUsers(pageable), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@NonNull @PathVariable Long id) throws Exception {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
