package com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.data.impl;

import android.util.Log;

import com.netikras.studies.studentbuddy.api.client.android.data.cache.CacheManager;
import com.netikras.studies.studentbuddy.api.client.android.data.cache.db.dao.AssignmentDao;
import com.netikras.studies.studentbuddy.api.client.android.data.cache.db.dao.LectureDao;
import com.netikras.studies.studentbuddy.api.client.android.data.cache.db.dao.LecturerDao;
import com.netikras.studies.studentbuddy.api.client.android.data.cache.db.dao.StudentDao;
import com.netikras.studies.studentbuddy.api.client.android.data.cache.db.dao.TestDao;
import com.netikras.studies.studentbuddy.api.client.android.data.stores.ApiBasedDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.data.LectureDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.api.misc.TimeUnit;
import com.netikras.studies.studentbuddy.api.timetable.controller.generated.LecturesApiConsumer;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LectureDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.StudentDto;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import static com.netikras.studies.studentbuddy.api.misc.TimeUnit.MILLISECONDS;
import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

/**
 * Created by netikras on 17.11.11.
 */

public class LectureDataStoreApiImpl extends ApiBasedDataStore<String, LectureDto> implements LectureDataStore {


    @Inject
    LecturesApiConsumer lecturesApiConsumer;

    private StudentDao studentCache;
    private LecturerDao lecturerCache;
    private AssignmentDao assignmentCache;
    private TestDao testCache;

    @Inject
    public LectureDataStoreApiImpl(CacheManager cacheManager) {
        setCache(cacheManager.getDao(LectureDao.class));
        studentCache = cacheManager.getDao(StudentDao.class);
        lecturerCache = cacheManager.getDao(LecturerDao.class);
        assignmentCache = cacheManager.getDao(AssignmentDao.class);
        testCache = cacheManager.getDao(TestDao.class);
    }

    @Override
    protected LectureDao getCache() {
        return super.getCache();
    }

    @Override
    protected LectureDto fillFromCache(LectureDto item) {
        super.fillFromCache(item);

        if (item.getLecturer() != null && lecturerCache != null) {
            if (item.getLecturer().getPerson() == null || isNullOrEmpty(item.getLecturer().getPerson().getFirstName())) {
                lecturerCache.fill(item.getLecturer());
            }
        }

        if (isNullOrEmpty(item.getAssignments()) && assignmentCache != null) {
            item.setAssignments(assignmentCache.getAllByLectureId(item.getId()));
            assignmentCache.fillAll(item.getAssignments());
        }

        if (isNullOrEmpty(item.getTests()) && testCache != null) {
            item.setTests(testCache.getAllByLectureId(item.getId()));
            testCache.fillAll(item.getTests());
        }

        return item;
    }

    @Override
    public void getById(String id, Subscriber<LectureDto>... subscribers) {

        if (isCacheEnabled()) {
            LectureDto cached = getCached(id);
            if (cached != null) {
                fillFromCache(cached);
                respondCacheHit(cached, subscribers);
            }
        }

        orderData(new ServiceRequest<LectureDto>() {
            @Override
            public LectureDto request() {
                return updateCache(lecturesApiConsumer.retrieveLectureDto(id));
            }
        }, subscribers);
    }

    @Override
    public void create(LectureDto item, Subscriber<LectureDto>... subscribers) {
        orderData(new ServiceRequest<LectureDto>() {
            @Override
            public LectureDto request() {
                return updateCache(lecturesApiConsumer.createLectureDto(item));
            }
        }, subscribers);
    }

    @Override
    public void update(LectureDto item, Subscriber<LectureDto>... subscribers) {
        orderData(new ServiceRequest<LectureDto>() {
            @Override
            public LectureDto request() {
                return updateCache(lecturesApiConsumer.updateLectureDto(item));
            }
        }, subscribers);
    }

    @Override
    public void purge(String id, Subscriber<Boolean>... subscribers) {
        orderData(new ServiceRequest<Boolean>() {
            @Override
            public Boolean request() {
                lecturesApiConsumer.purgeLectureDto(id);
                evict(id);
                return Boolean.TRUE;
            }
        }, subscribers);
    }

    @Override
    public void delete(String id, Subscriber... subscribers) {
        orderData(new ServiceRequest<Boolean>() {
            @Override
            public Boolean request() {
                lecturesApiConsumer.deleteLectureDto(id);
                evict(id);
                return Boolean.TRUE;
            }
        }, subscribers);
    }

    @Override
    @Deprecated
    public void getAll(Subscriber<Collection<LectureDto>>... subscribers) {
        notifyNotImplemented(subscribers);
    }

    @Override
    public void getAllByGroup(String id, Long after, Long before, Subscriber<Collection<LectureDto>>... subscribers) {

        if (isCacheEnabled()) {
            List<LectureDto> cached = getCache().getAllByGroupStartingBetween(id, ""+after, ""+before);
            if (!isNullOrEmpty(cached)) {
                fillFromCache(cached);
                respondCacheHit(cached, subscribers);
            }
        }

        orderData(new ServiceRequest<Collection<LectureDto>>() {
            @Override
            public Collection<LectureDto> request() {
                return updateCache(lecturesApiConsumer.getLectureDtoAllByGroupIdStartingBetween(id, after, before));
            }
        }, subscribers);
    }

    @Override
    public void getAllByLecturer(String id, Long after, Long before, Subscriber<Collection<LectureDto>>... subscribers) {
        if (isCacheEnabled()) {
            List<LectureDto> cached = getCache().getAllByLecturerStartingBetween(id, ""+after, ""+before);
            if (!isNullOrEmpty(cached)) {
                fillFromCache(cached);
                respondCacheHit(cached, subscribers);
            }
        }

        orderData(new ServiceRequest<Collection<LectureDto>>() {
            @Override
            public Collection<LectureDto> request() {
                return updateCache(lecturesApiConsumer.getLectureDtoAllByLecturerIdStartingBetween(id, after, before));
            }
        }, subscribers);
    }

    @Override
    public void getAllByRoom(String id, Long after, Long before, Subscriber<Collection<LectureDto>>... subscribers) {
        if (isCacheEnabled()) {
            List<LectureDto> cached = getCache().getAllByRoomStartingBetween(id, ""+after, ""+before);
            if (!isNullOrEmpty(cached)) {
                fillFromCache(cached);
                respondCacheHit(cached, subscribers);
            }
        }
        orderData(new ServiceRequest<Collection<LectureDto>>() {
            @Override
            public Collection<LectureDto> request() {
                return updateCache(lecturesApiConsumer.getLectureDtoAllByRoomIdStartingBetween(id, after, before));
            }
        }, subscribers);
    }

    @Override
    public void getAllByStudent(String id, Long after, Long before, Subscriber<Collection<LectureDto>>... subscribers) {

        if (isCacheEnabled() && studentCache != null) {
            StudentDto studentDto = studentCache.get(id);
            if (studentDto != null && studentDto.getGroup() != null && !isNullOrEmpty(studentDto.getGroup().getId())) {
                List<LectureDto> cached = getCache().getAllByGroupStartingBetween(studentDto.getGroup().getId(), "" + after, "" + before);
                if (!isNullOrEmpty(cached)) {
                    fillFromCache(cached);
                    respondCacheHit(cached, subscribers);
                }
            }
        }

        orderData(new ServiceRequest<Collection<LectureDto>>() {
            @Override
            public Collection<LectureDto> request() {
                return updateCache(lecturesApiConsumer.getLectureDtoAllByStudentIdStartingBetween(id, after, before));
            }
        }, subscribers);
    }

    @Override
    public void getAllByGuest(String id, Long after, Long before, Subscriber<Collection<LectureDto>>... subscribers) {

//        if (isCacheEnabled()) {
//            List<LectureDto> cached = cache.getAllWhere("guest_id = ? and starts_on > ? and starts_on < ?", id, "" + after, "" + before);
//            if (!isNullOrEmpty(cached)) {
//                respondSuccess(cached, subscribers);
//            }
//        }

        orderData(new ServiceRequest<Collection<LectureDto>>() {
            @Override
            public Collection<LectureDto> request() {
                return updateCache(lecturesApiConsumer.getLectureDtoAllByGuestIdStartingBetween(id, after, before));
            }
        }, subscribers);
    }


    @Override
    public void getAllByGroup(String id, TimeUnit unit, Long amount, Subscriber<Collection<LectureDto>>... subscribers) {

        if (isCacheEnabled()) {
            List<LectureDto> cached = getCache().getAllByGroupStartingBetween(id, "" + now(), "" + now() + unit.convert(amount, MILLISECONDS));
            if (!isNullOrEmpty(cached)) {
                fillFromCache(cached);
                respondCacheHit(cached, subscribers);
            }
        }

        orderData(new ServiceRequest<Collection<LectureDto>>() {
            @Override
            public Collection<LectureDto> request() {
                return updateCache(lecturesApiConsumer.getLectureDtoAllByGroupIdStartingIn(id, unit.getText(), amount));
            }
        }, subscribers);
    }

    @Override
    public void getAllByLecturer(String id, TimeUnit unit, Long amount, Subscriber<Collection<LectureDto>>... subscribers) {
        if (isCacheEnabled()) {
            List<LectureDto> cached = getCache().getAllByLecturerStartingBetween(id, "" + now(), "" + now() + unit.convert(amount, MILLISECONDS));
            if (!isNullOrEmpty(cached)) {
                fillFromCache(cached);

                for (LectureDto dto : cached) {
                    Log.d("Cached", "" + dto);
                    dto.setId("_"+dto.getId());
                }
                Log.d("Subscribers count", "" + subscribers.length);
                respondCacheHit(cached, subscribers);
            }
        }

        orderData(new ServiceRequest<Collection<LectureDto>>() {
            @Override
            public Collection<LectureDto> request() {
                return updateCache(lecturesApiConsumer.getLectureDtoAllByLecturerIdStartingIn(id, unit.getText(), amount));
            }
        }, subscribers);
    }

    @Override
    public void getAllByRoom(String id, TimeUnit unit, Long amount, Subscriber<Collection<LectureDto>>... subscribers) {
        if (isCacheEnabled()) {
            List<LectureDto> cached = getCache().getAllByRoomStartingBetween(id, "" + now(), "" + now() + unit.convert(amount, MILLISECONDS));
            if (!isNullOrEmpty(cached)) {
                fillFromCache(cached);
                respondCacheHit(cached, subscribers);
            }
        }

        orderData(new ServiceRequest<Collection<LectureDto>>() {
            @Override
            public Collection<LectureDto> request() {
                return updateCache(lecturesApiConsumer.getLectureDtoAllByRoomIdStartingIn(id, unit.getText(), amount));
            }
        }, subscribers);
    }

    @Override
    public void getAllByStudent(String id, TimeUnit unit, Long amount, Subscriber<Collection<LectureDto>>... subscribers) {
        if (isCacheEnabled()) {
            StudentDto studentDto = studentCache.get(id);
            if (studentDto != null && studentDto.getGroup() != null && !isNullOrEmpty(studentDto.getGroup().getId())) {
                List<LectureDto> cached = getCache().getAllByGroupStartingBetween( studentDto.getGroup().getId(), "" + now(), "" + now() + unit.convert(amount, MILLISECONDS));
                if (!isNullOrEmpty(cached)) {
                    fillFromCache(cached);
                    respondCacheHit(cached, subscribers);
                }
            }
        }

        orderData(new ServiceRequest<Collection<LectureDto>>() {
            @Override
            public Collection<LectureDto> request() {
                return updateCache(lecturesApiConsumer.getLectureDtoAllByStudentIdStartingIn(id, unit.getText(), amount));
            }
        }, subscribers);
    }

    @Override
    public void getAllByGuest(String id, TimeUnit unit, long amount, Subscriber<Collection<LectureDto>>... subscribers) {
//        if (isCacheEnabled()) {
//            List<LectureDto> cached = cache.getAllWhere("guest_id = ? and starts_on > ? and starts_on < ?", id, "" + now(), "" + now() + unit.convert(amount, TimeUnit.MILLISECONDS));
//            if (!isNullOrEmpty(cached)) {
//                respondSuccess(cached, subscribers);
//            }
//        }

        orderData(new ServiceRequest<Collection<LectureDto>>() {
            @Override
            public Collection<LectureDto> request() {
                return updateCache(lecturesApiConsumer.getLectureDtoAllByGuestIdStartingIn(id, unit.getText(), amount));
            }
        }, subscribers);
    }

    private long now() {
        return System.currentTimeMillis();
    }

}
