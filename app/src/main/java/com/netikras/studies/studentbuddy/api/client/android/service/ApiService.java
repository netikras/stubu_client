package com.netikras.studies.studentbuddy.api.client.android.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.netikras.studies.studentbuddy.api.client.android.conf.di.DepInjector;
import com.netikras.tools.common.exception.ErrorsCollection;
import com.netikras.tools.common.exception.FriendlyExceptionBase;
import com.netikras.tools.common.io.IoUtils;
import com.netikras.tools.common.security.IntegrityUtils;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;


public class ApiService extends IntentService {


    private static Thread serviceThread;

//    private static Queue<ServiceRequest> requestsQueue = new ConcurrentLinkedQueue<>();
    private static Queue<ServiceRequest> requestsQueue = new LinkedBlockingQueue<>();

    public ApiService() {
        super("ApiService");
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
//        fromContext.startService(intent);
        startThread();
    }


    private static synchronized void startThread() {
        if (serviceThread == null) {
            serviceThread = new Thread() {
                @Override
                public void run() {
                    while (true) {
                        if (BlockingQueue.class.isAssignableFrom(requestsQueue.getClass())) {

                            try {
                                processRequests((ServiceRequest) ((BlockingQueue) requestsQueue).poll(10, TimeUnit.SECONDS));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } else {

                            // This is bad. It's very bad. But at least it won't crash the app immediately or drain device's battery in 10 minutes
                            while (requestsQueue.isEmpty()) {
                                IoUtils.sleep(500);
                            }
                            processRequests(requestsQueue.poll());
                        }
                    }
                }
            };
            serviceThread.start();
        }
    }


    @Override
    public void onCreate() {
        super.onCreate();
//        DepInjector.inject(this)
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);

    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        processRequests();
    }


    private static void processRequests(ServiceRequest... requests) {

        if (!isNullOrEmpty(requests)) {
            for (ServiceRequest request : requests) {
                processRequest(request);
            }
        }

        while (!requestsQueue.isEmpty()) {
            ServiceRequest request = requestsQueue.poll();
            processRequest(request);
        }
    }

    private static void processRequest(ServiceRequest request) {
        if (request != null) {
            try {
                Object result = request.request();
                request.respondWithSuccess(result);
            } catch (Exception e) {
                ErrorsCollection errors = null;
                if (FriendlyExceptionBase.class.isAssignableFrom(e.getClass())) {
                    errors = ((FriendlyExceptionBase)e).getErrors();
                } else {
                    e.printStackTrace();
                    errors = new ErrorsCollection();
                    errors.add(FriendlyExceptionBase.digestToErrorBody(e));
                }
                request.respondWithErrors(errors);
            }
        }
    }


}
