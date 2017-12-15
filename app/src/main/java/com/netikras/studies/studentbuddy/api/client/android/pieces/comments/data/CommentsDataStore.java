package com.netikras.studies.studentbuddy.api.client.android.pieces.comments.data;

import com.netikras.studies.studentbuddy.api.client.android.data.stores.CrudDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.CommentDto;

import java.util.List;

/**
 * Created by netikras on 17.11.25.
 */

public interface CommentsDataStore extends CrudDataStore<String, CommentDto> {
    void getAllByPerson(String personId, Subscriber<List<CommentDto>>... subscribers);

    void getAllByTag(String tag, long pageno, long pagesize, Subscriber<List<CommentDto>>... subscribers);

    void getAllByType(String type, Subscriber<List<CommentDto>>... subscribers);

    void getAllByType(String type, String typeId, Subscriber<List<CommentDto>>... subscribers);

    void createByType(String type, String typeId, CommentDto comment, Subscriber<CommentDto>... subscribers);

    long getLastUpdateByType(String type, String typeId);

    void getChangesAfterForEntities(List<String> entitiesIds, long after, Subscriber<List<CommentDto>>... subscribers);
}
