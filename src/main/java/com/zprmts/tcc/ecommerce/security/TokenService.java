package com.zprmts.tcc.ecommerce.security;

import com.zprmts.tcc.ecommerce.domain.CargoEntity;
import com.zprmts.tcc.ecommerce.domain.User;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenService {
    static final String HEADER_STRING = "Authorization";

    private static final String TOKEN_PREFIX = "Bearer";
    private static final String CARGOS_CLAIM = "cargos";


    @Value("${jwt.expiration}")
    private String expiration;

    @Value("${jwt.secret}")
    private String secret;
    
    public String generateToken(User usuarioEntity) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + Long.parseLong(expiration));

        List<String> cargos = usuarioEntity.getCargos().stream()
                .map(CargoEntity::getAuthority)
                .toList();

        return TOKEN_PREFIX + " " +
                Jwts.builder()
                        .setIssuer("ecommerce-tcc")
                        .claim(Claims.ID, usuarioEntity.getId().toString())
                        .claim(CARGOS_CLAIM, cargos)
                        .setIssuedAt(now)
                        .setExpiration(exp)
                        .signWith(SignatureAlgorithm.HS256, secret)
                        .compact();
    }

//    public UsernamePasswordAuthenticationToken isValid(String token) {
//        if (token != null) {
//            Claims body = Jwts.parser()
//                    .setSigningKey(secret)
//                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
//                    .getBody();
//            String user = body.get(Claims.ID, String.class);
//            if (user != null) {
//                List<String> cargos = body.get(CARGOS_CLAIM, List.class);
//                List<SimpleGrantedAuthority> authorities = cargos.stream()
//                        .map(SimpleGrantedAuthority::new)
//                        .toList();
//                return new UsernamePasswordAuthenticationToken(user, null, authorities);
//            }
//        }
//        return null;
//    }
public UsernamePasswordAuthenticationToken isValid(String token) {
    if (token == null) {
        return null;
    }

    token = token.replace("Bearer ", "");
    Claims chaves;
    try {
        chaves = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException |
             IllegalArgumentException e) {
        return null;
    }
    String idUsuario = chaves.get(Claims.ID, String.class);

    List<String> cargos = chaves.get(CARGOS_CLAIM, List.class);

    List<SimpleGrantedAuthority> cargosList = cargos.stream()
            .map(SimpleGrantedAuthority::new)
            .toList();

    return new UsernamePasswordAuthenticationToken(idUsuario,
            token, cargosList);
}
}
