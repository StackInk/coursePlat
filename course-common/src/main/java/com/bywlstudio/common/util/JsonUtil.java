package com.bywlstudio.common.util;

import com.bywlstudio.common.exception.CourseException;
import com.google.gson.Gson;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: zl
 * @Date: Create in 2021/4/16 21:45
 * @Description:
 */
public class JsonUtil {

    static Gson gson = new Gson();

    public static String toJson(Object o) throws CourseException {
        if(Objects.isNull(o)) {
            throw new CourseException("JSON序列化参数为NULL",20005);
        }
        return gson.toJson(o);
    }

    public static <T> T getClass(String json, Class<T> clazz) throws CourseException {
        if(StringUtils.isEmpty(json)) {
            throw new CourseException("JSON反序列化参数为NULL",20005);
        }
        return gson.fromJson(json, clazz);
    }

    public static <T> T getClass(InputStream inputStream, Class<T> clazz) throws CourseException {
        if (Objects.isNull(inputStream)) {
            throw new CourseException("JSON反序列化参数为NULL",20005);
        }
        try {
            return gson.fromJson(new InputStreamReader(inputStream,"UTF-8"),clazz);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null ;
    }


}
