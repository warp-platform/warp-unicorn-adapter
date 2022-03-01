package com.warp.unicorn.dto.unique.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BaseReq {
    @JsonProperty("aSecurityIdent")
    private String aSecurityIdent;
}
