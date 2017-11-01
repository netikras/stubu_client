package com.netikras.studies.studentbuddy.api.client.android.data.stores;

import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;

import java.util.Collection;

/**
 * Created by netikras on 17.10.31.
 */

public interface CrudDataStore<I, E> extends BaseDataStore<I, E> {

    void getById(I id, Subscriber<E>... subscribers);

    void create(E item, Subscriber<E>... subscribers);

    void update(E item, Subscriber<E>... subscribers);

    void purge(I id, Subscriber<Boolean>... subscribers);

    void delete(I id, Subscriber... subscribers);

    void getAll(Subscriber<Collection<E>>... subscribers);

}
