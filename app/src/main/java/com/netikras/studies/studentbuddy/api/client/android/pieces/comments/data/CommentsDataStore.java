package com.netikras.studies.studentbuddy.api.client.android.pieces.comments.data;

import com.netikras.studies.studentbuddy.api.client.android.data.stores.CrudDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.CommentDto;

import java.util.Collection;

/**
 * Created by netikras on 17.11.25.
 */

public interface CommentsDataStore extends CrudDataStore<String, CommentDto> {
    void getAllByPerson(String personId, ServiceRequest.Subscriber<Collection<CommentDto>>... subscribers);

    void getAllByTag(String tag, long pageno, long pagesize, ServiceRequest.Subscriber<Collection<CommentDto>>... subscribers);

    void getAllByType(String type, ServiceRequest.Subscriber<Collection<CommentDto>>... subscribers);

    void getAllByType(String type, String typeId, ServiceRequest.Subscriber<Collection<CommentDto>>... subscribers);

    void createByType(String type, String typeId, CommentDto comment, ServiceRequest.Subscriber<CommentDto>... subscribers);
}
