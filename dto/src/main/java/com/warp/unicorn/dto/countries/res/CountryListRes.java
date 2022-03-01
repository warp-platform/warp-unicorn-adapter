package com.warp.unicorn.dto.countries.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.warp.unicorn.dto.countries.support.CountryDTO;
import com.warp.unicorn.dto.list.res.ListRes;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
@Data
public class CountryListRes extends ListRes {
    private List<CountryDTO> list;
}
