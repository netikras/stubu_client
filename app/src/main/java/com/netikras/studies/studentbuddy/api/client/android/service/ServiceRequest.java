package com.netikras.studies.studentbuddy.api.client.android.service;

import android.support.annotation.NonNull;

import com.netikras.tools.common.exception.ErrorBody;
import com.netikras.tools.common.exception.ErrorsCollection;
import com.netikras.tools.common.io.IoUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

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

        private boolean fetchRequired = true;;
        private String id;

        public Subscriber() {
            id = UUID.randomUUID().toString();
            if (getMonitor() != null) {
                getMonitor().register(this);
            }
        }

        private String getId() {
            return id;
        }


        protected SubscribersMonitor getMonitor() {
            return null;
        }

        public boolean isDataFetchRequired() {
            return fetchRequired;
        }

        public void setFetchRequired(boolean required) {
            fetchRequired = required;
        }

        public void onCacheHit(T response) {

        }

        public void onSuccess(T response) {

        }

        public void onError(ErrorsCollection errors) {

        }

        public void onBeforeExec(T response, ErrorsCollection errors) {

        }

        public void onAfterExec(T response, ErrorsCollection errors) {
            if (getMonitor() != null) {
                getMonitor().release(this);
            }
        }

        public void executeOnSuccess(T response) {
            onBeforeExec(response, null);
            onSuccess(response);
            onAfterExec(response, null);
        }

        public void executeOnError(ErrorsCollection errors) {
            onBeforeExec(null, errors);
            onError(errors);
            onAfterExec(null, errors);
        }

        public void executeOnError(ErrorBody errorBody) {
            ErrorsCollection errorBodies = new ErrorsCollection();
            errorBodies.add(errorBody);
            executeOnError(errorBodies);
        }
    }

    public static class SubscribersMonitor {
        private Map<String, Subscriber> subscribers = Collections.synchronizedMap(new HashMap<>());

        public synchronized Subscriber[] register(Subscriber... subscribers) {
            if (!isNullOrEmpty(subscribers)) {
                for (Subscriber subscriber : subscribers) {
                    if (subscriber != null) {
                        this.subscribers.put(subscriber.getId(), subscriber);
                    }
                }
            }
            return subscribers;
        }

        public synchronized Subscriber[] release(Subscriber... subscribers) {
            if (!isNullOrEmpty(subscribers)) {
                for (Subscriber subscriber : subscribers) {
                    if (subscriber != null) {
                        this.subscribers.remove(subscriber.getId());
                    }
                }
            }
            if (getCount() == 0) {
                onAllFinished();
            }
            return subscribers;
        }

        public int getCount() {
            return subscribers.size();
        }

        public Map<String, Subscriber> getPending() {
            return subscribers;
        }

        public void onAllFinished() {

        }

        /**
         *
         * @param timeUnit
         * @param value
         * @return true if all subscribers were released before timeout; false - if timeout occurred.
         */
        public boolean waitForAllToFinish(com.netikras.studies.studentbuddy.api.misc.TimeUnit timeUnit, long value) {
            long ms = timeUnit.toMillis(value);
            long tick = ms / 100 + 1;

            while (ms > 0 && getCount() > 0) {
                IoUtils.sleep(tick);
                ms -= tick;
            }

            return ms <= 0;
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
