package com.netikras.studies.studentbuddy.api.client.android.pieces.person.data.impl;

import com.netikras.studies.studentbuddy.api.client.android.data.cache.CacheManager;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.data.cahe.PersonDao;
import com.netikras.studies.studentbuddy.api.client.android.data.stores.ApiBasedDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.data.PersonDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.api.user.generated.PersonApiConsumer;
import com.netikras.studies.studentbuddy.api.user.mgmt.generated.AdminPersonApiConsumer;
import com.netikras.studies.studentbuddy.core.data.api.dto.PersonDto;

import java.util.Collection;

import javax.inject.Inject;

import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

/**
 * Created by netikras on 17.10.31.
 */

public class PersonDataStoreApiImpl extends ApiBasedDataStore<String, PersonDto> implements PersonDataStore {

    @Inject
    PersonApiConsumer apiConsumer;
    @Inject
    AdminPersonApiConsumer adminApiConsumer;

    @Inject
    public PersonDataStoreApiImpl(CacheManager cacheManager) {
        setCache(cacheManager.getDao(PersonDao.class));
    }

    @Override
    protected PersonDao getCache() {
        return super.getCache();
    }

    @Override
    public void getById(String id, Subscriber<PersonDto>... subscribers) {
        if (isCacheEnabled()) {
            PersonDto cached = getCached(id);
            if (cached != null) {
                respondCacheHit(cached, subscribers);
            }
        }
        orderData(new ServiceRequest<PersonDto>() {
            @Override
            public PersonDto request() {
                return updateCache(apiConsumer.retrievePersonDto(id));
            }
        }, subscribers);
    }

    @Override
    public void create(PersonDto item, Subscriber<PersonDto>... subscribers) {
        orderData(new ServiceRequest<PersonDto>() {
            @Override
            public PersonDto request() {
                return updateCache(adminApiConsumer.createPersonDto(item));
            }
        }, subscribers);
    }

    @Override
    public void update(PersonDto item, Subscriber<PersonDto>... subscribers) {
        orderData(new ServiceRequest<PersonDto>() {
            @Override
            public PersonDto request() {
                return updateCache(adminApiConsumer.updatePersonDto(item));
            }
        }, subscribers);
    }

    @Override
    public void purge(String id, Subscriber<Boolean>... subscribers) {
        orderData(new ServiceRequest<Boolean>() {
            @Override
            public Boolean request() {
                adminApiConsumer.deletePersonDto(id);
                evict(id);
                return Boolean.TRUE;
            }
        }, subscribers);
    }

    @Override
    public void delete(String id, Subscriber<Boolean>... subscribers) {
        orderData(new ServiceRequest<Boolean>() {
            @Override
            public Boolean request() {
                adminApiConsumer.purgePersonDto(id);
                evict(id);
                return Boolean.TRUE;
            }
        }, subscribers);
    }

    @Override
    public void getAll(Subscriber<Collection<PersonDto>>... subscribers) {
        orderData(new ServiceRequest<Collection<PersonDto>>() {
            @Override
            public Collection<PersonDto> request() {
                return updateCache(apiConsumer.getPersonDtoAll());
            }
        }, subscribers);
    }

    @Override
    public void getByIdentifier(String identifier, Subscriber<PersonDto>... subscribers) {

        if (isCacheEnabled()) {
            PersonDto dto = getCache().getByIdentifier(identifier);
            if (dto != null) {
                fillFromCache(dto);
                respondCacheHit(dto, subscribers);
            }
        }

        orderData(new ServiceRequest<PersonDto>() {
            @Override
            public PersonDto request() {
                return updateCache(apiConsumer.getPersonDtoByIdentifier(identifier));
            }
        }, subscribers);
    }

    @Override
    public void getByCode(String value, Subscriber<PersonDto>... subscribers) {

        if (isCacheEnabled()) {
            PersonDto dto = getCache().getByCode(value);
            if (dto != null) {
                fillFromCache(dto);
                respondCacheHit(dto, subscribers);
            }
        }

        orderData(new ServiceRequest<PersonDto>() {
            @Override
            public PersonDto request() {
                return updateCache(apiConsumer.getPersonDtoByCode(value));
            }
        }, subscribers);
    }

    @Override
    public void getByFirstName(String fname, Subscriber<Collection<PersonDto>>... subscribers) {
        if (isCacheEnabled()) {
            Collection<PersonDto> dtos = getCache().getAllByFirstName(fname);
            if (!isNullOrEmpty(dtos)) {
                fillFromCache(dtos);
                respondCacheHit(dtos, subscribers);
            }
        }

        orderData(new ServiceRequest<Collection<PersonDto>>() {
            @Override
            public Collection<PersonDto> request() {
                return updateCache(apiConsumer.getPersonDtoAllByFirstName(fname));
            }
        }, subscribers);
    }

    @Override
    public void getByLastName(String lname, Subscriber<Collection<PersonDto>>... subscribers) {
        if (isCacheEnabled()) {
            Collection<PersonDto> dtos = getCache().getAllByLastName(lname);
            if (!isNullOrEmpty(dtos)) {
                fillFromCache(dtos);
                respondCacheHit(dtos, subscribers);
            }
        }

        orderData(new ServiceRequest<Collection<PersonDto>>() {
            @Override
            public Collection<PersonDto> request() {
                return updateCache(apiConsumer.getPersonDtoAllByLastName(lname));
            }
        }, subscribers);
    }

    @Override
    public void getByFirstAndLastName(String fname, String lname, Subscriber<Collection<PersonDto>>... subscribers) {

        if (isCacheEnabled()) {
            Collection<PersonDto> dtos = getCache().getAllByFirsAndLasttName(fname, lname);
            if (!isNullOrEmpty(dtos)) {
                fillFromCache(dtos);
                respondCacheHit(dtos, subscribers);
            }
        }

        orderData(new ServiceRequest<Collection<PersonDto>>() {
            @Override
            public Collection<PersonDto> request() {
                return updateCache(apiConsumer.getPersonDtoAllByFirstAndLastName(fname, lname));
            }
        }, subscribers);
    }
}
