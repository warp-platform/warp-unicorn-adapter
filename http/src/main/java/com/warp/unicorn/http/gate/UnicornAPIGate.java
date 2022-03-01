package com.warp.unicorn.http.gate;

import com.warp.unicorn.dto.unique.base.BaseReq;
import com.warp.unicorn.dto.unique.countries.Country;
import com.warp.unicorn.dto.unique.login.MemberBasedApplicationLoginReq;

import java.util.List;

public interface UnicornAPIGate {
    String login(MemberBasedApplicationLoginReq req);
    List<Country> countries(BaseReq req);
}
