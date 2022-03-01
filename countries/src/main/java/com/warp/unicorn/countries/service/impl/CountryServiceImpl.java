package com.warp.unicorn.countries.service.impl;

import com.warp.unicorn.auth.service.TokenService;
import com.warp.unicorn.countries.service.CountryService;
import com.warp.unicorn.dto.countries.req.CountryListReq;
import com.warp.unicorn.dto.countries.res.CountryListRes;
import com.warp.unicorn.dto.countries.support.CountryDTO;
import com.warp.unicorn.dto.unique.base.BaseReq;
import com.warp.unicorn.dto.unique.countries.Country;
import com.warp.unicorn.http.gate.UnicornAPIGate;
import com.warp.unicorn.utils.util.ListUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final UnicornAPIGate unicornAPIGate;
    private final TokenService tokenService;

    @Override
    public CountryListRes list(CountryListReq req) {
        List<Country> countries = unicornAPIGate.countries(new BaseReq(tokenService.getToken()));
        CountryListRes res = new CountryListRes();
        if (CollectionUtils.isEmpty(countries))
            return res;

        if (StringUtils.hasText(req.getName()))
            countries = countries.stream().filter(i -> i.getName().toUpperCase().contains(req.getName().toUpperCase())).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(countries))
            return res;

        if (StringUtils.hasText(req.getIso()))
            countries = countries.stream().filter(i -> i.getIso().toUpperCase().equalsIgnoreCase(req.getIso())).collect(Collectors.toList());

        res.setTotalRowCount(countries.size());
        int rowCount = req.getRowCount() != null && countries.size() > req.getRowCount() ? req.getRowCount() : countries.size();
        res.setRowCount(rowCount);
        Pair<List<Country>, Integer> shortenList = ListUtils.pagination(countries, rowCount, req.getPageToken());
        res.setList(shortenList.getFirst().stream().map(CountryDTO::wrap).collect(Collectors.toList()));
        res.setTotalPageCount((int) Math.ceil(res.getTotalRowCount()/res.getRowCount().floatValue()));
        res.setNextPageToken(shortenList.getSecond() > 0? shortenList.getSecond() : null);
        return res;
    }
}
