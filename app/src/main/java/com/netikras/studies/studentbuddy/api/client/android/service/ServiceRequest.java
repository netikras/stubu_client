package com.netikras.studies.studentbuddy.api.client.android.service;

import android.support.annotation.NonNull;

import com.netikras.tools.common.exception.ErrorBody;
import com.netikras.tools.common.exception.ErrorsCollection;
import com.netikras.tools.common.io.IoUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

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

        private boolean fetchRequired = true;

        public boolean isDataFetchRequired() {
            return fetchRequired;
        }

        public void setFetchRequired(boolean required) {
            fetchRequired = required;
        }

        public void onCacheHit(T response) {
            onSuccess(response);
        }

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

    public static class Result<T> implements Future<T> {
        private T value;
        private boolean pending = true;
        private boolean timedOut = false;

        public Result(T value) {
            setValue(value);
        }

        public Result() {

        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
            setDone();
        }

        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            setDone();
            return false;
        }

        @Override
        public boolean isCancelled() {
            return false;
        }

        @Override
        public boolean isDone() {
            return !pending;
        }

        public void setDone() {
            pending = false;
        }

        @Override
        public T get() {
            return getValue();
        }

        @Override
        public T get(long timeout, @NonNull TimeUnit unit) {
            long tick = (timeout / 20) + 1;
            while (timeout > 0 && !isDone()) {
                timeout -= unit.convert(IoUtils.sleep(unit.toMillis(tick)), TimeUnit.MILLISECONDS);
            }

            timedOut = (timeout <= 0);
            return getValue();
        }

        public boolean isTimedOut() {
            return timedOut;
        }

        @Override
        public String toString() {
            return "Result{" +
                    "value=" + value +
                    ", pending=" + pending +
                    ", timedOut=" + timedOut +
                    '}';
        }
    }
}
