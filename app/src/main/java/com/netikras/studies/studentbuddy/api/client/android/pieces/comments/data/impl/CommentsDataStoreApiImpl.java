package com.netikras.studies.studentbuddy.api.client.android.pieces.comments.data.impl;

import com.netikras.studies.studentbuddy.api.client.android.data.cache.CacheManager;
import com.netikras.studies.studentbuddy.api.client.android.pieces.comments.data.cache.CommentDao;
import com.netikras.studies.studentbuddy.api.client.android.data.stores.ApiBasedDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.comments.data.CommentsDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.api.comments.generated.CommentsApiConsumer;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.CommentDto;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;
import static com.netikras.tools.common.security.IntegrityUtils.join;

/**
 * Created by netikras on 17.11.26.
 */

public class CommentsDataStoreApiImpl extends ApiBasedDataStore<String, CommentDto> implements CommentsDataStore {

    @Inject
    CommentsApiConsumer commentsApiConsumer;


    @Inject
    public CommentsDataStoreApiImpl(CacheManager cacheManager) {
        setCache(cacheManager.getDao(CommentDao.class));
    }

    @Override
    protected CommentDao getCache() {
        return super.getCache();
    }

    @Override
    public void getById(String id, Subscriber<CommentDto>... subscribers) {
        if (isCacheEnabled()) {
            CommentDto dto = getCached(id);
            if (dto != null) {
                fillFromCache(dto);
                respondCacheHit(dto, subscribers);
            }
        }
        orderData(new ServiceRequest<CommentDto>() {
            @Override
            public CommentDto request() {
                return updateCache(commentsApiConsumer.retrieveCommentDto(id));
            }
        }, subscribers);
    }

    @Override
    public void create(CommentDto item, Subscriber<CommentDto>... subscribers) {
        orderData(new ServiceRequest<CommentDto>() {
            @Override
            public CommentDto request() {
                return updateCache(commentsApiConsumer.createCommentDto(item));
            }
        }, subscribers);
    }

    @Override
    public void update(CommentDto item, Subscriber<CommentDto>... subscribers) {
        orderData(new ServiceRequest<CommentDto>() {
            @Override
            public CommentDto request() {
                return updateCache(commentsApiConsumer.updateCommentDto(item));
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
                evict(id);
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
    public void getAllByPerson(String personId, Subscriber<List<CommentDto>>... subscribers) {
        if (isCacheEnabled()) {
            List<CommentDto> dtos = getCache().getAllByAuthor(personId);
            if (!isNullOrEmpty(dtos)) {
                fillFromCache(dtos);
                respondCacheHit(dtos, subscribers);
            }
        }
        orderData(new ServiceRequest<Collection<CommentDto>>() {
            @Override
            public Collection<CommentDto> request() {
                return updateCache(commentsApiConsumer.getCommentDtoAllByPerson(personId));
            }
        }, subscribers);
    }

    @Override
    public void getAllByTag(String tag, long pageno, long pagesize, Subscriber<List<CommentDto>>... subscribers) {
        if (isCacheEnabled()) {
            List<CommentDto> dtos = getCache().getAllByTag(tag, pagesize, pageno);
            if (!isNullOrEmpty(dtos)) {
                fillFromCache(dtos);
                respondCacheHit(dtos, subscribers);
            }
        }
        orderData(new ServiceRequest<Collection<CommentDto>>() {
            @Override
            public Collection<CommentDto> request() {
                return updateCache(commentsApiConsumer.getCommentDtoByTagValue(tag, pageno, pagesize));
            }
        }, subscribers);
    }

    @Override
    public void getAllByType(String type, Subscriber<List<CommentDto>>... subscribers) {
        if (isCacheEnabled()) {
            List<CommentDto> dtos = getCache().getAllByEntity(type, null);
            if (!isNullOrEmpty(dtos)) {
                fillFromCache(dtos);
                respondCacheHit(dtos, subscribers);
            }
        }
        orderData(new ServiceRequest<Collection<CommentDto>>() {
            @Override
            public Collection<CommentDto> request() {
                return updateCache(commentsApiConsumer.getCommentDtoAllForType(type));
            }
        }, subscribers);
    }

    @Override
    public void getAllByType(String type, String typeId, Subscriber<List<CommentDto>>... subscribers) {
        if (isCacheEnabled()) {
            List<CommentDto> dtos = getCache().getAllByEntity(type, typeId);
            if (!isNullOrEmpty(dtos)) {
                fillFromCache(dtos);
                respondCacheHit(dtos, subscribers);
            }
        }
        orderData(new ServiceRequest<Collection<CommentDto>>() {
            @Override
            public Collection<CommentDto> request() {
                return updateCache(commentsApiConsumer.getCommentDtoForType(type, typeId));
            }
        }, subscribers);
    }

    @Override
    public void createByType(String type, String typeId, CommentDto comment, Subscriber<CommentDto>... subscribers) {
        orderData(new ServiceRequest<CommentDto>() {
            @Override
            public CommentDto request() {
                return updateCache(commentsApiConsumer.createCommentDtoNewForType(type, typeId, comment));
            }
        }, subscribers);
    }

    @Override
    public long getLastUpdateByType(String type, String typeId) {
        Date lastUpdate = getCache().getLastUpdateDate(type, typeId);

        return lastUpdate == null ? 0 : lastUpdate.getTime();
    }

    @Override
    public void getChangesAfterForEntities(List<String> entitiesIds, long after, Subscriber<List<CommentDto>>... subscribers) {
        orderData(new ServiceRequest<List<CommentDto>>() {
            @Override
            public List<CommentDto> request() {
                return updateCache(commentsApiConsumer.getCommentDtoEntitiesUpdatedAfter(join(",", true, null, entitiesIds), after));
            }
        }, subscribers);

    }
}
