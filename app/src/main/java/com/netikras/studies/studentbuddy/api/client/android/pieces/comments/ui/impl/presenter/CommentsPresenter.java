package com.netikras.studies.studentbuddy.api.client.android.pieces.comments.ui.impl.presenter;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.comments.data.CommentsDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.comments.ui.presenter.CommentsMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.comments.ui.view.CommentsMvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.presenter.PersonMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.view.PersonMvpView;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.CommentDto;

import java.util.Collection;
import java.util.List;

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
    public void getById(Subscriber<CommentDto> subscriber, String id) {
        getDataStore().getById(id);
        getDataStore().processOrders(getContext());
    }


    @Override
    public void createComment(Subscriber<CommentDto> subscriber, CommentDto dto) {
        getDataStore().create(dto, subscriber);
        getDataStore().processOrders(getContext());
    }

    @Override
    public void getComments(Subscriber<List<CommentDto>> subscriber, String entityType, String entityId) {
        getDataStore().getAllByType(entityType, entityId, subscriber);
        getDataStore().processOrders(getContext());
    }
}
