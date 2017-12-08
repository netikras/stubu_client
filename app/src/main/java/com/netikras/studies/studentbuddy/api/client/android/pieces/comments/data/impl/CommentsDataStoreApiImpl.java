package com.netikras.studies.studentbuddy.api.client.android.pieces.comments.data.impl;

import com.netikras.studies.studentbuddy.api.client.android.data.stores.ApiBasedDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.comments.data.CommentsDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.api.comments.generated.CommentsApiConsumer;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.CommentDto;

import java.util.Collection;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.26.
 */

public class CommentsDataStoreApiImpl extends ApiBasedDataStore<String, CommentDto> implements CommentsDataStore {

    @Inject
    CommentsApiConsumer commentsApiConsumer;

    @Override
    public void getById(String id, Subscriber<CommentDto>... subscribers) {
        orderData(new ServiceRequest<CommentDto>() {
            @Override
            public CommentDto request() {
                return commentsApiConsumer.retrieveCommentDto(id);
            }
        }, subscribers);
    }

    @Override
    public void create(CommentDto item, Subscriber<CommentDto>... subscribers) {
        orderData(new ServiceRequest<CommentDto>() {
            @Override
            public CommentDto request() {
                return commentsApiConsumer.createCommentDto(item);
            }
        }, subscribers);
    }

    @Override
    public void update(CommentDto item, Subscriber<CommentDto>... subscribers) {
        orderData(new ServiceRequest<CommentDto>() {
            @Override
            public CommentDto request() {
                return commentsApiConsumer.updateCommentDto(item);
            }
        }, subscribers);
    }

    /**
     * Not implemented. Fallback to deleteById()
     */
    @Override
    @Deprecated
    public void purge(String id, Subscriber<Boolean>... subscribers) {
        delete(id, subscribers);
    }

    @Override
    public void delete(String id, Subscriber<Boolean>... subscribers) {
        orderData(new ServiceRequest<Boolean>() {
            @Override
            public Boolean request() {
                commentsApiConsumer.deleteCommentDto(id);
                return Boolean.TRUE;
            }
        }, subscribers);
    }

    @Override
    @Deprecated
    public void getAll(Subscriber<Collection<CommentDto>>... subscribers) {
        notifyNotImplemented(subscribers);
    }

    @Override
    public void getAllByPerson(String personId, Subscriber<Collection<CommentDto>>... subscribers) {
        orderData(new ServiceRequest<Collection<CommentDto>>() {
            @Override
            public Collection<CommentDto> request() {
                return commentsApiConsumer.getCommentDtoAllByPerson(personId);
            }
        }, subscribers);
    }

    @Override
    public void getAllByTag(String tag, long pageno, long pagesize, Subscriber<Collection<CommentDto>>... subscribers) {
        orderData(new ServiceRequest<Collection<CommentDto>>() {
            @Override
            public Collection<CommentDto> request() {
                return commentsApiConsumer.getCommentDtoByTagValue(tag, pageno, pagesize);
            }
        }, subscribers);
    }

    @Override
    public void getAllByType(String type, Subscriber<Collection<CommentDto>>... subscribers) {
        orderData(new ServiceRequest<Collection<CommentDto>>() {
            @Override
            public Collection<CommentDto> request() {
                return commentsApiConsumer.getCommentDtoAllForType(type);
            }
        }, subscribers);
    }

    @Override
    public void getAllByType(String type, String typeId, Subscriber<Collection<CommentDto>>... subscribers) {
        orderData(new ServiceRequest<Collection<CommentDto>>() {
            @Override
            public Collection<CommentDto> request() {
                return commentsApiConsumer.getCommentDtoForType(type, typeId);
            }
        }, subscribers);
    }

    @Override
    public void createByType(String type, String typeId, CommentDto comment, Subscriber<CommentDto>... subscribers) {
        orderData(new ServiceRequest<CommentDto>() {
            @Override
            public CommentDto request() {
                return commentsApiConsumer.createCommentDtoNewForType(type, typeId, comment);
            }
        }, subscribers);
    }
}
