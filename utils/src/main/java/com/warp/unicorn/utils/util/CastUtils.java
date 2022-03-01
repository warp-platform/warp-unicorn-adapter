package com.warp.unicorn.utils.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CastUtils {

    public static Long castToLong(Object val){
        if (val == null)
            return null;
        if (val instanceof Long)
            return (Long) val;
        if (val instanceof Integer)
            return ((Integer) val).longValue();
        if (val instanceof String)
            return Long.valueOf((String) val);
        return null;
    }

    public static String castToString(Object val){
        if (val == null)
            return null;
        if (val instanceof String)
            return (String) val;
        if (val instanceof Long)
            return Long.toString((Long) val);
        if (val instanceof Integer)
            return Integer.toString((Integer) val);
        return null;
    }

    public static Float castToFloat(Object val){
        if (val == null)
            return null;
        if (val instanceof Float)
            return (Float) val;
        if (val instanceof String) {
            String v = (String) val;
            return Float.parseFloat(v.replace(",", "."));
        }
        if (val instanceof Long)
            return ((Long) val).floatValue();
        if (val instanceof Integer)
            return ((Integer) val).floatValue();
        if (val instanceof Double)
            return ((Double) val).floatValue();
        return null;
    }

    public static Integer castToInteger(Object val){
        if (val == null)
            return 0;
        if (val instanceof Integer)
            return (Integer) val;
        if (val instanceof String)
            return Integer.parseInt((String) val);
        if (val instanceof Long)
            return ((Long) val).intValue();
        if (val instanceof Double)
            return ((Double) val).intValue();
        if (val instanceof Float)
            return ((Float) val).intValue();
        return 0;
    }

    public static Date castToDate(Object val){
        if (val == null)
            return null;
        if (val instanceof Date)
            return (Date) val;
        if (val instanceof String){
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
                return formatter.parse((String) val);
            } catch (ParseException e){
                return null;
            }
        }
        return null;
    }

}
