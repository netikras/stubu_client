package com.netikras.studies.studentbuddy.api.client.android.pieces.person.data.impl;

import com.netikras.studies.studentbuddy.api.client.android.data.cache.CacheManager;
import com.netikras.studies.studentbuddy.api.client.android.data.cache.db.dao.PersonDao;
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

    private final PersonDao cache;

    @Inject
    public PersonDataStoreApiImpl(CacheManager cacheManager) {
        cache = new PersonDao(cacheManager);
    }

    private PersonDto updateCache(PersonDto item) {
        if (cache != null) {
            cache.put(item);
        }
        return item;
    }

    private void evict(PersonDto personDto) {
        if (personDto != null) {
            evict(personDto.getId());
        }
    }

    private void evict(String id) {
        if (cache != null) {
            cache.deleteById(id);
        }
    }


    @Override
    public void getById(String id, Subscriber<PersonDto>... subscribers) {
        if (cache != null) {
            PersonDto cached = cache.get(id);
            if (cached != null) {
                respondSuccess(cached, subscribers);
                return;
            }
        }
        orderData(new ServiceRequest<PersonDto>(){
            @Override
            public PersonDto request() {
                PersonDto dto = apiConsumer.retrievePersonDto(id);
                return updateCache(dto);
            }
        }, subscribers);
    }

    @Override
    public void create(PersonDto item, Subscriber<PersonDto>... subscribers) {
        orderData(new ServiceRequest<PersonDto>(){
            @Override
            public PersonDto request() {
                PersonDto dto = adminApiConsumer.createPersonDto(item);
                return updateCache(dto);
            }
        }, subscribers);
    }

    @Override
    public void update(PersonDto item, Subscriber<PersonDto>... subscribers) {
        orderData(new ServiceRequest<PersonDto>(){
            @Override
            public PersonDto request() {
                return updateCache(adminApiConsumer.updatePersonDto(item));
            }
        }, subscribers);
    }

    @Override
    public void purge(String id, Subscriber<Boolean>... subscribers) {
        orderData(new ServiceRequest<Boolean>(){
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
        orderData(new ServiceRequest<Boolean>(){
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
        orderData(new ServiceRequest<Collection<PersonDto>>(){
            @Override
            public Collection<PersonDto> request() {
                return apiConsumer.getPersonDtoAll();
            }
        }, subscribers);
    }

    @Override
    public void getByIdentifier(String identifier, Subscriber<PersonDto>... subscribers) {
        orderData(new ServiceRequest<PersonDto>(){
            @Override
            public PersonDto request() {
                return updateCache(apiConsumer.getPersonDtoByIdentifier(identifier));
            }
        }, subscribers);
    }

    @Override
    public void getByCode(String value, Subscriber<PersonDto>... subscribers) {
        orderData(new ServiceRequest<PersonDto>(){
            @Override
            public PersonDto request() {
                return updateCache(apiConsumer.getPersonDtoByCode(value));
            }
        }, subscribers);
    }

    @Override
    public void getByFirstName(String fname, Subscriber<Collection<PersonDto>>... subscribers) {
        orderData(new ServiceRequest<Collection<PersonDto>>(){
            @Override
            public Collection<PersonDto> request() {
                return apiConsumer.getPersonDtoAllByFirstName(fname);
            }
        }, subscribers);
    }

    @Override
    public void getByLastName(String lname, Subscriber<Collection<PersonDto>>... subscribers) {
        orderData(new ServiceRequest<Collection<PersonDto>>(){
            @Override
            public Collection<PersonDto> request() {
                return apiConsumer.getPersonDtoAllByLastName(lname);
            }
        }, subscribers);
    }

    @Override
    public void getByFirstAndLastName(String fname, String lname, Subscriber<Collection<PersonDto>>... subscribers) {
        orderData(new ServiceRequest<Collection<PersonDto>>(){
            @Override
            public Collection<PersonDto> request() {
                return apiConsumer.getPersonDtoAllByFirstAndLastName(fname, lname);
            }
        }, subscribers);
    }
}
