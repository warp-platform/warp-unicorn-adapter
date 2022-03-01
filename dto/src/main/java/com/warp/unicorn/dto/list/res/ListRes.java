package com.warp.unicorn.dto.list.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ListRes {
    private Integer totalRowCount;
    private Integer rowCount;
    private Integer nextPageToken;
    private Integer totalPageCount;
}
