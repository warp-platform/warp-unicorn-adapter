package com.warp.unicorn.countries.service;

import com.warp.unicorn.dto.countries.req.CountryListReq;
import com.warp.unicorn.dto.countries.res.CountryListRes;

public interface CountryService {
    CountryListRes list(CountryListReq req);
}
