package com.zprmts.tcc.ecommerce.service;

import com.zprmts.tcc.ecommerce.domain.User;
import com.zprmts.tcc.ecommerce.dto.PasswordResetRequest;
import com.zprmts.tcc.ecommerce.dto.RegistrationRequest;
import com.zprmts.tcc.ecommerce.dto.RegistrationRequestAdmin;
import com.zprmts.tcc.ecommerce.exception.RegraDeNegocioException;
//import com.gmail.merikbest2015.ecommerce.security.oauth2.OAuth2UserInfo;

import java.util.Map;

public interface AuthenticationService {

    String registerUser(RegistrationRequest registrationRequest) throws RegraDeNegocioException;
    String registerUserAdmin(RegistrationRequestAdmin registrationRequestAdmin) throws RegraDeNegocioException;
    String passwordReset(PasswordResetRequest passwordResetRequest) throws RegraDeNegocioException;
}
