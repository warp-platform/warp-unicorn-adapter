package com.warp.unicorn.countries.controller;

import com.warp.unicorn.countries.service.CountryService;
import com.warp.unicorn.dto.countries.req.CountryListReq;
import com.warp.unicorn.dto.countries.res.CountryListRes;
import com.warp.unicorn.utils.builder.ResponseBuilder;
import com.warp.unicorn.utils.constant.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${spring.application.name}/countries")
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;

    @GetMapping(value = "list", produces = Constants.APPLICATION_JSON_VALUE)
    public ResponseEntity<CountryListRes> list(CountryListReq req){
        return ResponseBuilder.ok(countryService.list(req));
    }

}
