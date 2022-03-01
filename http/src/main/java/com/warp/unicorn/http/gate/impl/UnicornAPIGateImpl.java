package com.warp.unicorn.http.gate.impl;

import com.warp.unicorn.dto.unique.base.BaseReq;
import com.warp.unicorn.dto.unique.countries.Country;
import com.warp.unicorn.dto.unique.login.MemberBasedApplicationLoginReq;
import com.warp.unicorn.dto.unique.login.MemberBasedApplicationLoginRes;
import com.warp.unicorn.http.client.RestGate;
import com.warp.unicorn.http.gate.UnicornAPIGate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UnicornAPIGateImpl implements UnicornAPIGate {

    private final RestGate restGate;
    @Value("${application.unicorn.url}")
    private String url;

    @Override
    public String login(MemberBasedApplicationLoginReq req) {
        MemberBasedApplicationLoginRes res = restGate.call(MemberBasedApplicationLoginRes.class,
                url,
                "MemberBasedApplicationLogin",
                null,
                req,
                HttpMethod.POST,
                null,
                MediaType.APPLICATION_JSON);
        if (res != null)
            return res.getASecurityID();
        return null;
    }

    @Override
    public List<Country> countries(BaseReq req) {
        return restGate.call(new ParameterizedTypeReference<>() {},
                url,
                "LoadCountries",
                null,
                req,
                HttpMethod.POST,
                null,
                MediaType.APPLICATION_JSON);
    }
}
