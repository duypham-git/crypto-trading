package com.me.crypto.trading.common.helper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.Instant;

public class JsonHelper {
    public static Gson DEFAULT_GSON;

    static {
        DEFAULT_GSON = new GsonBuilder()
                .registerTypeAdapter(Instant.class, InstantAdapter.INSTANCE)
                .create();
    }

    public static String toJsonString(Object obj) {
        return DEFAULT_GSON.toJson(obj);
    }
}
