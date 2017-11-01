package com.netikras.studies.studentbuddy.api.client.android.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.support.annotation.Nullable;

import com.netikras.tools.common.exception.ErrorsCollection;
import com.netikras.tools.common.exception.FriendlyExceptionBase;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;


public class ApiService extends IntentService {


    private static Queue<ServiceRequest> requestsQueue = new ConcurrentLinkedQueue<>();

    public ApiService(String name) {
        super(name);
    }


    public static void sendRequest(Context context, ServiceRequest request) {
        enqueueRequest(request);
        processQueue(context);
    }

    public static void enqueueRequest(ServiceRequest request) {
        requestsQueue.add(request);
    }

    public static void processQueue(Context fromContext) {
        Intent intent = new Intent(fromContext, ApiService.class);
        fromContext.startService(intent);
    }


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);

    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        processRequests();
    }


    private void processRequests() {
        while (requestsQueue.isEmpty()) {
            ServiceRequest request = requestsQueue.poll();
            if (request != null) {
                try {
                    Object result = request.request();
                    request.respondWithSuccess(result);
                } catch (Exception e) {
                    ErrorsCollection errors = null;
                    if (FriendlyExceptionBase.class.isAssignableFrom(e.getClass())) {
                        errors = ((FriendlyExceptionBase)e).getErrors();
                    } else {
                        errors = new ErrorsCollection();
                        errors.add(FriendlyExceptionBase.digestToErrorBody(e));
                    }
                    request.respondWithErrors(errors);
                }
            }
        }
    }


}
