package com.javaweb.Utils;

import java.util.Map;

public class MapUtil {

    @SuppressWarnings("unchecked")
    public static <T> T getObject(Map<String, Object> params, String key, Class<T> tClass) {
        Object value = params.get(key);
        if (value == null || value.toString().trim().isEmpty()) {
            return null;
        }
        try {
            if (tClass == Long.class) {
                return (T) Long.valueOf(value.toString());
            }
            if (tClass == Integer.class) {
                return (T) Integer.valueOf(value.toString());
            }
            if (tClass == String.class) {
                return (T) value.toString();
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}
