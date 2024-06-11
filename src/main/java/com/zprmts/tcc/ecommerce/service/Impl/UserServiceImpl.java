package com.zprmts.tcc.ecommerce.service.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zprmts.tcc.ecommerce.domain.CargoEntity;
import com.zprmts.tcc.ecommerce.domain.Perfume;
import com.zprmts.tcc.ecommerce.domain.User;
import com.zprmts.tcc.ecommerce.dto.perfume.PerfumeResponse;
import com.zprmts.tcc.ecommerce.dto.user.*;
import com.zprmts.tcc.ecommerce.exception.RegraDeNegocioException;
import com.zprmts.tcc.ecommerce.repository.PerfumeRepository;
import com.zprmts.tcc.ecommerce.repository.UserRepository;
import com.zprmts.tcc.ecommerce.service.PerfumeService;
import com.zprmts.tcc.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.zprmts.tcc.ecommerce.constants.ErrorMessage.EMAIL_NOT_FOUND;
import static com.zprmts.tcc.ecommerce.constants.ErrorMessage.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    @Override
    public User getUserById(Long userId) throws RegraDeNegocioException {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RegraDeNegocioException(USER_NOT_FOUND));
    }

    @Override
    public User getUserInfo(String email) throws RegraDeNegocioException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RegraDeNegocioException(EMAIL_NOT_FOUND));
    }

    @Override
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        Page<User> users = userRepository.findAllByOrderByIdAsc(pageable);

        return users.map(user -> objectMapper.convertValue(user, UserResponse.class));
    }

    public void delete(Long id) throws RegraDeNegocioException {
        User user = getUserById(id);
        user.setAtivo("N");
        userRepository.save(user);
    }

    @Override
    @Transactional
    public UserResponse update(@Nullable Long id, UpdateUserRequest updateUserRequest) throws RegraDeNegocioException {
        User user;
        if (id != null) {
            user = getUserById(id);
        } else {
            user = getUserById(getUserLogado().getId());
        }

        user.setFirstName(updateUserRequest.getFirstName());
        user.setLastName(updateUserRequest.getLastName());
        user.setCity(updateUserRequest.getCity());
        user.setAddress(updateUserRequest.getAddress());
        user.setPhoneNumber(updateUserRequest.getPhoneNumber());
        user.setPostIndex(updateUserRequest.getPostIndex());
        user.setAtivo(updateUserRequest.getAtivo());
        userRepository.save(user);

        UserResponse userResponse = objectMapper.convertValue(user, UserResponse.class);
        Set<CargoResponseDTO> cargoResponseDTOSet = new HashSet<>();
        for (CargoEntity cargo : user.getCargos()) {
            cargoResponseDTOSet.add(objectMapper.convertValue(cargo, CargoResponseDTO.class));
        }
        userResponse.setCargos(cargoResponseDTOSet);
        return userResponse;
    }

    @Override
    @Transactional
    public UserResponse updateMyUser(UpdateUserRequest updateUserRequest) throws RegraDeNegocioException {
        User user;
        user = getUserById(getUserLogado().getId());

        user.setFirstName(updateUserRequest.getFirstName());
        user.setLastName(updateUserRequest.getLastName());
        user.setCity(updateUserRequest.getCity());
        user.setAddress(updateUserRequest.getAddress());
        user.setPhoneNumber(updateUserRequest.getPhoneNumber());
        user.setPostIndex(updateUserRequest.getPostIndex());
        user.setAtivo(updateUserRequest.getAtivo());
        userRepository.save(user);

        UserResponse userResponse = objectMapper.convertValue(user, UserResponse.class);
        Set<CargoResponseDTO> cargoResponseDTOSet = new HashSet<>();
        for (CargoEntity cargo : user.getCargos()) {
            cargoResponseDTOSet.add(objectMapper.convertValue(cargo, CargoResponseDTO.class));
        }
        userResponse.setCargos(cargoResponseDTOSet);
        return userResponse;
    }

    public LoginResponseDTO getUsuarioLogado() throws RegraDeNegocioException {
        Long idUsuarioLogado = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return mappingLoginResponse(userRepository
                .findById(idUsuarioLogado)
                .orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado."))
        );
    }

    public User getUserLogado() throws RegraDeNegocioException {
        Long idUsuarioLogado = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        User user = getUserById(idUsuarioLogado);
        return user;
    }

    public UserResponse getUserResponseLogado() throws RegraDeNegocioException {
        User user = getUserLogado();
        UserResponse userResponse = objectMapper.convertValue(user, UserResponse.class);
        Set<CargoResponseDTO> cargoResponseDTOSet = new HashSet<>();
        for (CargoEntity cargo : user.getCargos()) {
            cargoResponseDTOSet.add(objectMapper.convertValue(cargo, CargoResponseDTO.class));
        }
        userResponse.setCargos(cargoResponseDTOSet);
        return userResponse;
    }


    public LoginResponseDTO mappingLoginResponse(User usuarioEntity) {
        LoginResponseDTO loginResponseDTO = objectMapper.convertValue(usuarioEntity, LoginResponseDTO.class);
        Set<CargoResponseDTO> cargoResponseDTOS = usuarioEntity.getCargos()
                .stream()
                .map(cargoEntity -> objectMapper.convertValue(cargoEntity, CargoResponseDTO.class))
                .collect(Collectors.toSet());
        loginResponseDTO.setRoles(cargoResponseDTOS);

        return loginResponseDTO;
    }
}
