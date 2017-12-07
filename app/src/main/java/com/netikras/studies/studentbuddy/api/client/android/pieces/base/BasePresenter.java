package com.netikras.studies.studentbuddy.api.client.android.pieces.base;

/**
 * Created by janisharali on 27/01/17.
 */

import android.content.Context;
import android.content.Intent;

import com.netikras.studies.studentbuddy.api.client.android.App;
import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity.ViewTask;
import com.netikras.studies.studentbuddy.api.client.android.util.Exchange;

import javax.inject.Inject;


/**
 * Base class that implements the Presenter interface and provides a base implementation for
 * onAttach() and onDetach(). It also handles keeping a reference to the mvpView that
 * can be accessed from the children classes by calling getMvpView().
 */
public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {

    private static final String TAG = "BasePresenter";

    private final DataManager mDataManager;

    private V mMvpView;

    @Inject
    Exchange exchange;

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
    public void startView(Context fromContext) {
        if (getMvpView() != null) {
            startView(fromContext, getMvpView().getClass());
        }
    }

    public void startView(Context fromContext, Class<? extends MvpView> viewClass) {
        Intent intent = new Intent(fromContext, viewClass);
        fromContext.startActivity(intent);
    }

    /**
     * @param fromContext current context - the one activity is to be called from
     * @param viewClass   activity class
     * @param task        something to do when view is started. <br/>
     *                    NOTE: before calling execute() view will call setActivity(). Utilize that
     */
    public <VIEW extends MvpView> void startView(Context fromContext, Class<VIEW> viewClass, ViewTask<VIEW> task) {
        Intent intent = new Intent(fromContext, viewClass);
        intent.putExtra("task", exchange.put(task));
        fromContext.startActivity(intent);
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.onAttach(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }

}
