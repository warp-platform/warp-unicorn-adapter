package com.warp.unicorn.dto.unique.login;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MemberBasedApplicationLoginReq {
    @JsonProperty("SysLogin")
    private String sysLogin;
    @JsonProperty("SysPass")
    private String sysPass;
}
