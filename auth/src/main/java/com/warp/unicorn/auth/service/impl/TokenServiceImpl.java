package com.warp.unicorn.auth.service.impl;

import com.warp.unicorn.auth.dao.model.Token;
import com.warp.unicorn.auth.dao.repository.TokenRepository;
import com.warp.unicorn.auth.service.TokenService;
import com.warp.unicorn.dto.unique.login.MemberBasedApplicationLoginReq;
import com.warp.unicorn.http.gate.UnicornAPIGate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;
    private final UnicornAPIGate unicornAPIGate;

    @Value("${application.unicorn.login}")
    private String login;
    @Value("${application.unicorn.password}")
    private String password;

    @Override
    public String getToken() {
        Optional<Token> token = tokenRepository.find();
        if (token.isPresent())
            return token.get().getToken();
        String newToken = unicornAPIGate.login(new MemberBasedApplicationLoginReq(this.login, new String(Base64.getDecoder().decode(password))));
        tokenRepository.save(new Token(newToken));
        return newToken;
    }
}
