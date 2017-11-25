package com.netikras.studies.studentbuddy.api.client.android.pieces.comments.data;

import com.netikras.studies.studentbuddy.api.client.android.data.stores.CrudDataStore;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.CommentDto;

/**
 * Created by netikras on 17.11.25.
 */

public interface CommentsDataStore extends CrudDataStore<String, CommentDto> {
}
