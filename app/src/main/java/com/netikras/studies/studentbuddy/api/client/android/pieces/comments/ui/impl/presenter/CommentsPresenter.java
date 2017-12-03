package com.netikras.studies.studentbuddy.api.client.android.pieces.comments.ui.impl.presenter;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.comments.data.CommentsDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.comments.ui.presenter.CommentsMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.comments.ui.view.CommentsMvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.presenter.PersonMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.view.PersonMvpView;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.25.
 */

public class CommentsPresenter<V extends CommentsMvpView> extends BasePresenter<V> implements CommentsMvpPresenter<V> {

    @Inject
    PersonMvpPresenter<PersonMvpView> personPresenter;

    @Inject
    public CommentsPresenter(DataManager dataManager) {
        super(dataManager);
    }

    private CommentsDataStore getDataStore() {
        return getDataManager().getStore(CommentsDataStore.class);
    }

}
