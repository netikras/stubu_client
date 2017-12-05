package com.netikras.studies.studentbuddy.api.client.android.service;

import com.netikras.tools.common.exception.ErrorBody;
import com.netikras.tools.common.exception.ErrorsCollection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by netikras on 17.10.30.
 */

public abstract class ServiceRequest<T> {

    private List<Subscriber> subscribers = new ArrayList<>();


    public abstract T request();

    public void addSubscriber(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    public void respondWithSuccess(T response) {
        subscribers.forEach(s -> s.executeOnSuccess(response));
    }

    public void respondWithErrors(ErrorsCollection errors) {
        subscribers.forEach(s -> s.executeOnError(errors));
    }

    public static class Subscriber<T> {
        public void onSuccess(T response) {

        }

        public void onError(ErrorsCollection errors) {

        }

        public void executeOnSuccess(T response) {
            onSuccess(response);
        }

        public void executeOnError(ErrorsCollection errors) {
            onError(errors);
        }

        public void executeOnError(ErrorBody errorBody) {
            ErrorsCollection errorBodies = new ErrorsCollection();
            errorBodies.add(errorBody);
            executeOnError(errorBodies);
        }
    }

    public static class Result<T> {
        private T value;

        public Result (T value) {
            setValue(value);
        }

        public Result () {

        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }
    }
}
