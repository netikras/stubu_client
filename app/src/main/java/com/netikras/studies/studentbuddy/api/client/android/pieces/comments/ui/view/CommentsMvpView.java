package com.netikras.studies.studentbuddy.api.client.android.pieces.comments.ui.view;

import com.netikras.studies.studentbuddy.api.client.android.pieces.base.MvpView;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.CommentDto;

/**
 * Created by netikras on 17.11.25.
 */

public interface CommentsMvpView extends MvpView {
    void showComment(CommentDto commentDto);
}
