package com.netikras.studies.studentbuddy.api.client.android.pieces.comments.ui.impl.presenter;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.comments.data.CommentsDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.comments.ui.impl.view.CommentsActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.comments.ui.presenter.CommentsMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.comments.ui.view.CommentsMvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.impl.view.PersonInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.presenter.PersonMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.view.PersonMvpView;
import com.netikras.studies.studentbuddy.core.data.api.dto.PersonDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.CommentDto;

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

    @Override
    public void startView(Context fromContext) {
        startView(fromContext, CommentsActivity.class);
    }

    public void startView(Context fromContext, BaseActivity.ViewTask<CommentsActivity> task) {
        startView(fromContext, CommentsActivity.class, task);
    }

    @Override
    public void showAuthor(Context context, PersonDto personDto) {
        personPresenter.showPerson(context, personDto);
    }

    @Override
    public void show(Context context, CommentDto commentDto) {
        startView(context, new BaseActivity.ViewTask<CommentsActivity>() {
            @Override
            public void execute() {
                getActivity().showComment(commentDto);
            }
        });
    }

}
