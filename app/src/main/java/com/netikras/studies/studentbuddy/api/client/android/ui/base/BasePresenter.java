/*
 * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://mindorks.com/license/apache-v2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.netikras.studies.studentbuddy.api.client.android.ui.base;

/**
 * Created by janisharali on 27/01/17.
 */

import android.content.Context;
import android.content.Intent;

import com.netikras.studies.studentbuddy.api.client.android.App;
import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.tools.common.exception.ErrorBody;
import com.netikras.tools.common.exception.ErrorsCollection;

import javax.inject.Inject;


/**
 * Base class that implements the Presenter interface and provides a base implementation for
 * onAttach() and onDetach(). It also handles keeping a reference to the mvpView that
 * can be accessed from the children classes by calling getMvpView().
 */
public abstract class BasePresenter<V extends MvpView> implements MvpPresenter<V> {

    private static final String TAG = "BasePresenter";

    private final DataManager mDataManager;

    private V mMvpView;


    @Inject
    public BasePresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
    }


    @Override
    public void onAttach(V mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void onDetach() {
        mMvpView = null;
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    public V getMvpView() {
        return mMvpView;
    }

    public Context getContext() {
        return getMvpView() != null ? (Context) getMvpView() : App.getCurrent();
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public DataManager getDataManager() {
        return mDataManager;
    }

    @Override
    public void handleApiError(ErrorBody error) {

//        if (error == null || error.getErrorBody() == null) {
//            getMvpView().onError(R.string.api_default_error);
//            return;
//        }
//
//        if (error.getErrorCode() == AppConstants.API_STATUS_CODE_LOCAL_ERROR
//                && error.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)) {
//            getMvpView().onError(R.string.connection_error);
//            return;
//        }
//
//        if (error.getErrorCode() == AppConstants.API_STATUS_CODE_LOCAL_ERROR
//                && error.getErrorDetail().equals(ANConstants.REQUEST_CANCELLED_ERROR)) {
//            getMvpView().onError(R.string.api_retry_error);
//            return;
//        }
//
//        final GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
//        final Gson gson = builder.create();
//
//        try {
//            ApiError apiError = gson.fromJson(error.getErrorBody(), ApiError.class);
//
//            if (apiError == null || apiError.getMessage() == null) {
//                getMvpView().onError(R.string.api_default_error);
//                return;
//            }
//
//            switch (error.getErrorCode()) {
//                case HttpsURLConnection.HTTP_UNAUTHORIZED:
//                case HttpsURLConnection.HTTP_FORBIDDEN:
//                    setUserAsLoggedOut();
//                    getMvpView().openActivityOnTokenExpire();
//                case HttpsURLConnection.HTTP_INTERNAL_ERROR:
//                case HttpsURLConnection.HTTP_NOT_FOUND:
//                default:
//                    getMvpView().onError(apiError.getMessage());
//            }
//        } catch (JsonSyntaxException | NullPointerException e) {
//            Log.e(TAG, "handleApiError", e);
//            getMvpView().onError(R.string.api_default_error);
//        }
    }

    @Override
    public void setUserAsLoggedOut() {
//        getDataManager().setAccessToken(null);
    }

    @Override
    public void startView(Context fromContext) {
        if (getMvpView() != null) {
            startView(fromContext, getMvpView().getClass());
        }
    }

    public void startView(Context fromContext, Class<? extends MvpView> viewClass) {
        Intent intent = new Intent(fromContext, viewClass);
        fromContext.startActivity(intent);
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.onAttach(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }

    protected class ErrorsAwareSubscriber<T> extends Subscriber<T> {
        @Override
        public void onError(ErrorsCollection errors) {
            for (ErrorBody error : errors) {
                getMvpView().onError(error.getMessage1());
            }
        }
    }

}
