package com.warp.unicorn.dto.countries.req;

import com.warp.unicorn.dto.list.req.ListReq;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CountryListReq extends ListReq {
    private String name;
    private String iso;
}
