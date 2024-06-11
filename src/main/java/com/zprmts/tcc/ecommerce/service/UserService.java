package com.zprmts.tcc.ecommerce.service;

import com.zprmts.tcc.ecommerce.domain.User;
import com.zprmts.tcc.ecommerce.dto.user.UpdateUserRequest;
import com.zprmts.tcc.ecommerce.dto.user.UserResponse;
import com.zprmts.tcc.ecommerce.exception.RegraDeNegocioException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;

public interface UserService {

    User getUserById(Long userId) throws RegraDeNegocioException;

    User getUserInfo(String email) throws RegraDeNegocioException;

    Page<UserResponse> getAllUsers(Pageable pageable);

    UserResponse update(@Nullable Long id, UpdateUserRequest updateUserRequest) throws RegraDeNegocioException;

    UserResponse updateMyUser(UpdateUserRequest updateUserRequest) throws RegraDeNegocioException;
}
