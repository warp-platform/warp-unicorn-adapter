package com.warp.unicorn.utils.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum WarpErrors {
    INTERNAL_ERROR(HttpStatus.FORBIDDEN,1000, "Service is unavailable now"),
    MISSING_REQUIRED_PARAMETERS(HttpStatus.BAD_REQUEST, 1001, "Required parameters are not present"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, 1002, "Wrong/No session"),
    DB_ERROR(HttpStatus.BAD_REQUEST, 1003, "Wrong/No session");

    private final HttpStatus status;
    private final Integer code;
    private final String message;

    public static WarpErrors getByCode(Integer code){
        for(WarpErrors i : WarpErrors.values()){
            if (code.equals(i.code))
                return i;
        }
        return INTERNAL_ERROR;
    }
}
