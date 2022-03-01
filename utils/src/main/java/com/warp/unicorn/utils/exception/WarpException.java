package com.warp.unicorn.utils.exception;

import lombok.Getter;

@Getter
public class WarpException extends RuntimeException {

    private static final long serialVersionUID = 8359430630502179416L;

    private final Integer code;
    private final String message;

    public WarpException(WarpErrors err) {
        super(err.getMessage());
        this.code = err.getCode();
        this.message = err.getMessage();
    }

}
