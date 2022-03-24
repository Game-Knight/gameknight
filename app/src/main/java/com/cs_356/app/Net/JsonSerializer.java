package com.cs_356.app.Net;

import com.google.gson.Gson;

public class JsonSerializer {

    public static String serialize(Object object) {
        return (new Gson()).toJson(object);
    }

    public static <T> T deserialize(String json, Class<T> returnType) {
        return (new Gson()).fromJson(json, returnType);
    }
}
