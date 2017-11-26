package com.netikras.studies.studentbuddy.api.client.android.pieces.comments.ui.presenter;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.pieces.base.MvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.comments.ui.impl.view.CommentsActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.comments.ui.view.CommentsMvpView;
import com.netikras.studies.studentbuddy.core.data.api.dto.PersonDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.CommentDto;

/**
 * Created by netikras on 17.11.25.
 */

public interface CommentsMvpPresenter<V extends CommentsMvpView> extends MvpPresenter<V> {
    void showAuthor(Context context, PersonDto personDto);

    void show(Context context, CommentDto comment);
}
