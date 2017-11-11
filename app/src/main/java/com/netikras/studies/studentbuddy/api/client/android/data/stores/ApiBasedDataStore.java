package com.netikras.studies.studentbuddy.api.client.android.data.stores;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.service.ApiService;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.AssignmentDto;
import com.netikras.tools.common.exception.ErrorBody;
import com.netikras.tools.common.exception.ErrorsCollection;
import com.netikras.tools.common.exception.FriendlyUncheckedException;

import java.util.Collection;

import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

/**
 * Created by netikras on 17.10.31.
 */

public abstract class ApiBasedDataStore<I, E> implements BaseDataStore<I, E> {

    private ErrorBody notImplementedError = new ErrorBody()
            .setMessage1("Cannot perform requested action")
            .setMessage2("Not implemented")
            ;
    private ErrorsCollection errorBodies = new ErrorsCollection();

    @Override
    public void orderData(ServiceRequest request) {
        ApiService.enqueueRequest(request);
    }

    protected void orderData(ServiceRequest request, ServiceRequest.Subscriber... subscribers) {
        if (subscribers != null) {
            for (ServiceRequest.Subscriber subscriber : subscribers) {
                request.addSubscriber(subscriber);
            }
        }
        orderData(request);
    }

    @Override
    public void processOrders(Context context) {
        ApiService.processQueue(context);
    }

    protected void notifyNotImplemented(ServiceRequest.Subscriber... subscribers) {

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
