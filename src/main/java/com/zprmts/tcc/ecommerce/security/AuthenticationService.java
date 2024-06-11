package com.zprmts.tcc.ecommerce.security;

import com.zprmts.tcc.ecommerce.domain.User;
import com.zprmts.tcc.ecommerce.exception.RegraDeNegocioException;
import com.zprmts.tcc.ecommerce.service.Impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {
    private final UserServiceImpl userService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User usuario = null;
        try {
            usuario = userService.getUserInfo(username);
        } catch (RegraDeNegocioException e) {
            throw new RuntimeException(e);
        }
        return usuario;
    }
}
