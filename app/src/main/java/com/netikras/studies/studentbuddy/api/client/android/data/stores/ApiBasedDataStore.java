package com.netikras.studies.studentbuddy.api.client.android.data.stores;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.service.ApiService;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.tools.common.exception.ErrorBody;
import com.netikras.tools.common.exception.ErrorsCollection;
import com.netikras.tools.common.exception.FriendlyUncheckedException;

import java.util.Collection;

import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

/**
 * Created by netikras on 17.10.31.
 */

public abstract class ApiBasedDataStore<I, E> extends CacheAwareStore<E> implements BaseDataStore<I, E> {

    private ErrorBody notImplementedError = new ErrorBody()
            .setMessage1("Cannot perform requested action")
            .setMessage2("Not implemented");
    private ErrorsCollection errorBodies = new ErrorsCollection();

    @Override
    public void orderData(ServiceRequest request) {
        ApiService.enqueueRequest(request);
    }

    protected void orderData(ServiceRequest request, Subscriber... subscribers) {
        if (subscribers != null) {
            if (isCacheEnabled()) {
//                orderOriginDataAndUpdateCache(request, subscribers);
                orderOriginData(request, subscribers);
            } else {
                orderOriginData(request, subscribers);
            }
        }
        orderData(request);
    }

    private void orderOriginDataAndUpdateCache(ServiceRequest request, Subscriber... subscribers) {
        Subscriber cacheSubscriber = new Subscriber(){
            @Override
            public void executeOnSuccess(Object response) {
                super.executeOnSuccess(response);

                try {
                    if (response != null) {
                        if (Collection.class.isAssignableFrom(response.getClass())) {
                            updateCache((Collection<E>) response);
                        } else {
                            updateCache((E) response);
                        }
                    }
                } catch (Throwable ignore) {}
                delegateResponseToAll(response, subscribers);
            }

            @Override
            public void executeOnError(ErrorsCollection errors) {
                super.executeOnError(errors);
                delegateErrorToAll(errors, subscribers);
            }
        };
        request.addSubscriber(cacheSubscriber);
    }

    private void orderOriginData(ServiceRequest request, Subscriber... subscribers) {
        for (Subscriber subscriber : subscribers) {
            if (subscriber.isDataFetchRequired()) {
                request.addSubscriber(subscriber);
            }
        }
    }

    private void delegateResponseToAll(Object response, Subscriber... subscribers) {
        if (!isNullOrEmpty(subscribers)) {
            for (Subscriber subscriber : subscribers) {
                if (subscriber != null) {
                    try {
                        subscriber.executeOnSuccess(response);
                    } catch (Throwable ignore) {}
                }
            }
        }
    }

    private void delegateErrorToAll(ErrorsCollection errors, Subscriber... subscribers) {
        if (!isNullOrEmpty(subscribers)) {
            for (Subscriber subscriber : subscribers) {
                if (subscriber != null) {
                    try {
                        subscriber.executeOnError(errors);
                    } catch (Throwable ignore) {}
                }
            }
        }
    }

    protected <T> void respondSuccess(T response, Subscriber<T>... subscribers) {
        if (!isNullOrEmpty(subscribers)) {
            for (Subscriber<T> subscriber : subscribers) {
                subscriber.onSuccess(response);
            }
        }
    }

    protected <T> void respondCacheHit(T response, Subscriber<T>... subscribers) {
        if (!isNullOrEmpty(subscribers)) {
            for (Subscriber<T> subscriber : subscribers) {
                subscriber.onCacheHit(response);
            }
        }
    }

    @Override
    public void processOrders(Context context) {
        ApiService.processQueue(context);
    }

    protected void notifyNotImplemented(Subscriber... subscribers) {

        if (isNullOrEmpty(errorBodies)) {
            errorBodies.add(notImplementedError);
        }

        if (!isNullOrEmpty(subscribers)) {
//            for (ServiceRequest.Subscriber subscriber : subscribers) {
//                try {
//                    subscriber.onError(errorBodies);
//                } catch (Exception e) {
//
//                }
//            }
            throw new FriendlyUncheckedException()
                    .addError(new ErrorBody().setMessage1("Not implemented"));
        }
    }


}
