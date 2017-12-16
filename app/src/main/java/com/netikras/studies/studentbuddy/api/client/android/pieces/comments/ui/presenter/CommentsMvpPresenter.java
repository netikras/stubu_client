package com.netikras.studies.studentbuddy.api.client.android.pieces.comments.ui.presenter;

import com.netikras.studies.studentbuddy.api.client.android.pieces.base.MvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.comments.ui.view.CommentsMvpView;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.CommentDto;

import java.util.List;

/**
 * Created by netikras on 17.11.25.
 */

public interface CommentsMvpPresenter<V extends CommentsMvpView> extends MvpPresenter<V> {
    void getById(Subscriber<CommentDto> subscriber, String id);

    void createComment(Subscriber<CommentDto> subscriber, CommentDto dto);

    void getComments(Subscriber<List<CommentDto>> subscriber, String entityType, String entityId);

    void deleteComment(Subscriber<Boolean> subscriber, String id);
}
