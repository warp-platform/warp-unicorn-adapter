package com.warp.unicorn.dto.list.req;

import lombok.Data;

@Data
public class ListReq {
    private Integer rowCount;
    private Integer pageToken;
}
