package com.zprmts.tcc.ecommerce.service.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zprmts.tcc.ecommerce.domain.CargoEntity;
import com.zprmts.tcc.ecommerce.domain.User;
import com.zprmts.tcc.ecommerce.dto.PasswordResetRequest;
import com.zprmts.tcc.ecommerce.dto.RegistrationRequest;
import com.zprmts.tcc.ecommerce.dto.RegistrationRequestAdmin;
import com.zprmts.tcc.ecommerce.enums.CargoEnum;
import com.zprmts.tcc.ecommerce.exception.RegraDeNegocioException;
import com.zprmts.tcc.ecommerce.repository.UserRepository;
import com.zprmts.tcc.ecommerce.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Set;

import static com.zprmts.tcc.ecommerce.constants.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserServiceImpl userService;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public String registerUserAdmin(RegistrationRequestAdmin registrationRequestAdmin) throws RegraDeNegocioException {
        if (registrationRequestAdmin.getPassword() != null && !registrationRequestAdmin.getPassword().equals(registrationRequestAdmin.getPassword2())) {
            throw new RegraDeNegocioException(PASSWORDS_DO_NOT_MATCH);
        }

        if (userRepository.findByEmail(registrationRequestAdmin.getEmail()).isPresent()) {
            throw new RegraDeNegocioException(EMAIL_IN_USE);
        }
        User user = objectMapper.convertValue(registrationRequestAdmin, User.class);
        user.setAtivo("S");
        Set<CargoEnum> cargoEnumSet = registrationRequestAdmin.getCargos();
        user.setPassword(passwordEncoder.encode(registrationRequestAdmin.getPassword()));
        for (CargoEnum cargoEnum : cargoEnumSet) {
            CargoEntity cargoEntity = new CargoEntity();
            cargoEntity.setNome(cargoEnum.name());
            cargoEntity.setIdCargo(cargoEnum.getValor());
            user.getCargos().add(cargoEntity);
        }
        userRepository.save(user);

        return "User successfully registered.";
    }

    @Override
    @Transactional
    public String registerUser(RegistrationRequest registrationRequest) throws RegraDeNegocioException {
        if (registrationRequest.getPassword() != null && !registrationRequest.getPassword().equals(registrationRequest.getPassword2())) {
            throw new RegraDeNegocioException(PASSWORDS_DO_NOT_MATCH);
        }

        if (userRepository.findByEmail(registrationRequest.getEmail()).isPresent()) {
            throw new RegraDeNegocioException(EMAIL_IN_USE);
        }

        User user = objectMapper.convertValue(registrationRequest, User.class);
        user.setAtivo("S");
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));

        CargoEntity cargoEntity = new CargoEntity();
        cargoEntity.setNome(CargoEnum.ROLE_USUARIO.name());
        cargoEntity.setIdCargo(CargoEnum.ROLE_USUARIO.getValor());
        user.getCargos().add(cargoEntity);
        userRepository.save(user);

        return "User successfully registered.";
    }

    @Override
    @Transactional
    public String passwordReset(PasswordResetRequest passwordResetRequest) throws RegraDeNegocioException {
        if (StringUtils.isEmpty(passwordResetRequest.getPassword2())) {
            throw new RegraDeNegocioException(EMPTY_PASSWORD_CONFIRMATION);
        }
        if (passwordResetRequest.getPassword() != null && !passwordResetRequest.getPassword().equals(passwordResetRequest.getPassword2())) {
            throw new RegraDeNegocioException(PASSWORDS_DO_NOT_MATCH);
        }
        User user = userService.getUserLogado();
        user.setPassword(passwordEncoder.encode(passwordResetRequest.getPassword()));
        userRepository.save(user);
        return "Password successfully changed!";
    }

    private void sendEmail(User user, String subject, String template, String urlAttribute, String urlPath) {
//        Map<String, Object> attributes = new HashMap<>();
//        attributes.put("firstName", user.getFirstName());
//        attributes.put(urlAttribute, "http://" + hostname + urlPath);
//        mailSender.sendMessageHtml(user.getEmail(), subject, template, attributes);
    }
}
