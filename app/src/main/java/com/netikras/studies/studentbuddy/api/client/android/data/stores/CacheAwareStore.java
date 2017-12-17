package com.netikras.studies.studentbuddy.api.client.android.data.stores;

import com.netikras.studies.studentbuddy.api.client.android.data.cache.GenericDao;

import java.util.Collection;

import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

/**
 * Created by netikras on 17.12.10.
 */

public class CacheAwareStore<E> {

    private GenericDao<E> cache = null;

    protected void setCache(GenericDao<E> c) {
        cache = c;
    }

    protected <C extends GenericDao<E>> C getCache() {
        return (C) cache;
    }

    protected boolean isCacheEnabled() {
        return getCache() != null;
    }


    protected E getCached(String id) {
        if (isCacheEnabled()) {
            return getCache().get(id);
        }
        return null;
    }

    protected <C extends Collection<E>> C updateCache(C items) {
        if (isCacheEnabled()) {
            getCache().putAllWithImmediates(items);
        }
        return items;
    }

    protected E updateCache(E item) {
        if (isCacheEnabled()) {
            getCache().putWithImmediates(item);
        }
        return item;
    }

    protected E fillFromCache(E item) {
        if (isCacheEnabled()) {
            getCache().fill(item);
        }
        return item;
    }

    protected <C extends Collection<E>> C fillFromCache(C items) {
        if (!isNullOrEmpty(items)) {
            items.forEach(this::fillFromCache);
        }
        return items;
    }

    protected void evict(String id) {
        if (isCacheEnabled()) {
            getCache().deleteById(id);
        }
    }

    protected <C extends Collection<E>> void evictAll(C items) {
        if (!isNullOrEmpty(items) && isCacheEnabled()) {
            for (E item : items) {
                if (item != null) {
                    evict(getCache().getId(item));
                }
            }
        }
    }

}
