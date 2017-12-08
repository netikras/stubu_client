package com.netikras.studies.studentbuddy.api.client.android.data.cache;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.data.cache.db.dao.GenericDao;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by netikras on 17.12.8.
 */

public class CacheManager {

    private Map<Class<? extends GenericDao>, Object> caches = new HashMap<>();

    private Context appContext;

    public void setAppContext(Context appContext) {
        this.appContext = appContext;
    }

    public Context getAppContext() {
        return appContext;
    }

    public <D extends GenericDao> void registerDao(D dao) {
        if (dao != null) {
            caches.put(dao.getClass(), dao);
        }
    }

    public <D extends GenericDao> D getDao(Class<D> type) {
        return (D) caches.get(type);
    }

}
