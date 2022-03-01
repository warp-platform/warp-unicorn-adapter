package com.warp.unicorn.adapter.config.mvc.interceptors;

import com.warp.unicorn.utils.exception.WarpErrors;
import com.warp.unicorn.utils.exception.WarpException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.warp.unicorn.utils.constant.Constants.Header.*;

@Component
@RequiredArgsConstructor
public class RequestInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        String requestId = request.getHeader(REQUEST_ID);
        String channel = request.getHeader(CHANNEL);
        if (requestId != null && channel != null) {
            ThreadContext.put(REQUEST_ID, requestId);
            ThreadContext.put(CHANNEL, channel);
        } else {
            throw new WarpException(WarpErrors.MISSING_REQUIRED_PARAMETERS);
        }
        String locale = request.getHeader(LOCALE);
        if (!StringUtils.hasText(locale)) {
            locale = "ru";
        }
        ThreadContext.put(LOCALE, locale);
        return true;
    }
}
