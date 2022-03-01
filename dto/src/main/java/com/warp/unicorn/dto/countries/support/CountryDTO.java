package com.warp.unicorn.dto.countries.support;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.warp.unicorn.dto.unique.countries.Country;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class CountryDTO {
    private Long id;
    private String name;
    private String iso;

    public static CountryDTO wrap(Country c){
        CountryDTO dto = new CountryDTO();
        dto.setId(c.getId());
        dto.setName(c.getName());
        dto.setIso(c.getIso());
        return dto;
    }
}
