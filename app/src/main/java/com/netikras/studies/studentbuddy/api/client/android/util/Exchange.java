package com.netikras.studies.studentbuddy.api.client.android.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by netikras on 17.11.19.
 */

public class Exchange {

    private Map<String, Object> data;


    public Exchange() {
        data = new HashMap<>();
    }

    public String put(Object o) {
        String key = UUID.randomUUID().toString();
        data.put(key, o);
        return key;
    }

    public Object get(String key) {
        return data.remove(key);
    }

    public Object peek(String key) {
        return data.get(key);
    }

}
