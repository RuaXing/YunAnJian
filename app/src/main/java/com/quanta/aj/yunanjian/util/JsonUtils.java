package com.quanta.aj.yunanjian.util;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 许德建 on 2014/8/11.
 */
public class JsonUtils {

    public static <T> List<T> parseList(String jsonStr, Class<T> clazz) {
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(jsonStr);
        Gson gson = new Gson();
        if (jsonElement.isJsonArray()) {
            JsonArray array = (JsonArray) jsonElement;
            List<T> list = new ArrayList<T>();
            for (int i = 0; i < array.size(); i++) {
                list.add(gson.fromJson(array.get(i), clazz));
            }
            return list;
        }
        return new ArrayList<T>();
    }

    public static <T> T parseObject(InputStream in, Class<T> clazz) {
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(new InputStreamReader(in));
        return parseObject(jsonElement, clazz);
    }

    public static <T> T parseObject(String jsonStr, Class<T> clazz) {
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(jsonStr);
        return parseObject(jsonElement, clazz);
    }

    private static <T> T parseObject(JsonElement jsonElement, Class<T> clazz) {
        Gson gson = new Gson();
        if (jsonElement.isJsonObject()) {
            return gson.fromJson(jsonElement, clazz);
        }
        if (String.class.isAssignableFrom(clazz)) {
            return ((T) jsonElement.getAsString());
        } else if (Boolean.class.isAssignableFrom(clazz) || boolean.class.isAssignableFrom(clazz)) {
            return ((T) (Boolean) jsonElement.getAsBoolean());
        } else if (Byte.class.isAssignableFrom(clazz) || byte.class.isAssignableFrom(clazz)) {
            return ((T) (Byte) jsonElement.getAsByte());
        } else if (Short.class.isAssignableFrom(clazz) || short.class.isAssignableFrom(clazz)) {
            return ((T) (Short) jsonElement.getAsShort());
        } else if (Integer.class.isAssignableFrom(clazz) || int.class.isAssignableFrom(clazz)) {
            return ((T) (Integer) jsonElement.getAsInt());
        } else if (Long.class.isAssignableFrom(clazz) || long.class.isAssignableFrom(clazz)) {
            return ((T) (Long) jsonElement.getAsLong());
        } else if (Float.class.isAssignableFrom(clazz) || float.class.isAssignableFrom(clazz)) {
            return ((T) (Float) jsonElement.getAsFloat());
        } else if (Double.class.isAssignableFrom(clazz) || double.class.isAssignableFrom(clazz)) {
            return ((T) (Double) jsonElement.getAsDouble());
        }
        return null;
    }

    public static String toString(Object object) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        return gson.toJson(object);
    }

    public static byte[] toBytes(Object object) {
        return toString(object).getBytes();
    }
}
