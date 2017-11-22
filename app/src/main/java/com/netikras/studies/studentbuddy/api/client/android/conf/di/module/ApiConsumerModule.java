package com.netikras.studies.studentbuddy.api.client.android.conf.di.module;

import com.netikras.studies.studentbuddy.api.client.android.conf.di.module.carrier.RestConfig;
import com.netikras.studies.studentbuddy.api.location.generated.FloorApiConsumer;
import com.netikras.studies.studentbuddy.api.location.generated.LocationApiConsumer;
import com.netikras.studies.studentbuddy.api.location.generated.SchoolApiConsumer;
import com.netikras.studies.studentbuddy.api.sys.generated.AdminApiConsumer;
import com.netikras.studies.studentbuddy.api.sys.generated.StatusApiConsumer;
import com.netikras.studies.studentbuddy.api.timetable.controller.generated.AssignmentApiConsumer;
import com.netikras.studies.studentbuddy.api.timetable.controller.generated.LecturesApiConsumer;
import com.netikras.studies.studentbuddy.api.timetable.controller.generated.TestsApiConsumer;
import com.netikras.studies.studentbuddy.api.user.generated.LecturerApiConsumer;
import com.netikras.studies.studentbuddy.api.user.generated.PersonApiConsumer;
import com.netikras.studies.studentbuddy.api.user.generated.StudentApiConsumer;
import com.netikras.studies.studentbuddy.api.user.generated.UserApiConsumer;
import com.netikras.studies.studentbuddy.api.user.mgmt.generated.AdminLecturerApiConsumer;
import com.netikras.studies.studentbuddy.api.user.mgmt.generated.AdminPersonApiConsumer;
import com.netikras.studies.studentbuddy.api.user.mgmt.generated.AdminStudentApiConsumer;
import com.netikras.studies.studentbuddy.api.user.mgmt.generated.AdminUserApiConsumer;
import com.netikras.tools.common.remote.RemoteEndpointServer;
import com.netikras.tools.common.remote.http.GenericRestConsumer;
import com.netikras.tools.common.remote.http.RequestListener;
import com.netikras.tools.common.remote.http.SessionContext;

import java.util.Map;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by netikras on 17.11.2.
 */

@Module
@Singleton
public class ApiConsumerModule {


    private <C extends GenericRestConsumer> C prepare(C consumer, RestConfig config) {

        consumer.setServiceProvider(config.getRestServiceProvider());

        if (config.getListeners() != null) {
            for (Map.Entry<String, RequestListener> listenerEntry : config.getListeners().entrySet()) {
                consumer.addListener(listenerEntry.getKey(), listenerEntry.getValue());
            }
        }

        if (config.getServers() != null) {
            for (Map.Entry<String, RemoteEndpointServer> serverEntry : config.getServers().entrySet()) {
                consumer.addServer(serverEntry.getKey(), serverEntry.getValue());
            }
        }

        consumer.setSessionContext(config.getSessionContext());

        return consumer;
    }


    @Provides
    public PersonApiConsumer personApiConsumer(RestConfig config) {
        return prepare(new PersonApiConsumer(), config);
    }

    @Provides
    public AdminPersonApiConsumer adminPersonApiConsumer(RestConfig config) {
        return prepare(new AdminPersonApiConsumer(), config);
    }

    @Provides
    public UserApiConsumer userApiConsumer(RestConfig config) {
        return prepare(new UserApiConsumer(), config);
    }

    @Provides
    public AdminUserApiConsumer adminUserApiConsumer(RestConfig config) {
        return prepare(new AdminUserApiConsumer(), config);
    }

    @Provides
    public SchoolApiConsumer schoolApiConsumer(RestConfig config) {
        return prepare(new SchoolApiConsumer(), config);
    }

    @Provides
    public LocationApiConsumer locationApiConsumer(RestConfig config) {
        return prepare(new LocationApiConsumer(), config);
    }

    @Provides
    public FloorApiConsumer floorApiConsumer(RestConfig config) {
        return prepare(new FloorApiConsumer(), config);
    }

    @Provides
    public AdminApiConsumer adminApiConsumer(RestConfig config) {
        return prepare(new AdminApiConsumer(), config);
    }

    @Provides
    public StatusApiConsumer statusApiConsumer(RestConfig config) {
        return prepare(new StatusApiConsumer(), config);
    }

    @Provides
    public AssignmentApiConsumer assignmentApiConsumer(RestConfig config) {
        return prepare(new AssignmentApiConsumer(), config);
    }


    @Provides
    public TestsApiConsumer testsApiConsumer(RestConfig config) {
        return prepare(new TestsApiConsumer(), config);
    }

    @Provides
    public LecturesApiConsumer lecturesApiConsumer(RestConfig config) {
        return prepare(new LecturesApiConsumer(), config);
    }

    @Provides
    public LecturerApiConsumer lecturerApiConsumer(RestConfig config) {
        return prepare(new LecturerApiConsumer(), config);
    }

    @Provides
    public StudentApiConsumer studentApiConsumer(RestConfig config) {
        return prepare(new StudentApiConsumer(), config);
    }

    @Provides
    public AdminLecturerApiConsumer adminLecturerApiConsumer(RestConfig config) {
        return prepare(new AdminLecturerApiConsumer(), config);
    }

    @Provides
    public AdminStudentApiConsumer adminStudentApiConsumer(RestConfig config) {
        return prepare(new AdminStudentApiConsumer(), config);
    }

}
