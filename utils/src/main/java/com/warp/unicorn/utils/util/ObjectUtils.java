package com.warp.unicorn.utils.util;

public class ObjectUtils {
    public static <T> T nvl(T a, T b) {
        return a != null? a : b;
    }
}
