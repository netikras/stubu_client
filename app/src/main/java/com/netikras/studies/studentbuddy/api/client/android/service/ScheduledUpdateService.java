package com.netikras.studies.studentbuddy.api.client.android.service;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.netikras.studies.studentbuddy.api.client.android.R;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.DepInjector;
import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.data.cache.CacheManager;
import com.netikras.studies.studentbuddy.api.client.android.data.prefs.PreferencesHelper;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity.ViewTask;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.MvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.comments.data.CommentsDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.data.LectureDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.data.cahe.LectureDao;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.impl.view.AssignmentActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.impl.view.LectureInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.impl.view.TestInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecturer.data.LecturerDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.login.ui.impl.view.LoginActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.main.ui.impl.view.MainActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.data.UserDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.data.GuestDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.data.StudentDataStore;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.SubscribersMonitor;
import com.netikras.studies.studentbuddy.api.client.android.util.CommonUtils;
import com.netikras.studies.studentbuddy.api.client.android.util.Exchange;
import com.netikras.studies.studentbuddy.api.misc.TimeUnit;
import com.netikras.studies.studentbuddy.core.data.api.dto.PersonDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.CommentDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.UserDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.AssignmentDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.DisciplineTestDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LectureDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LectureGuestDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LecturerDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.StudentDto;
import com.netikras.tools.common.exception.ErrorBody;
import com.netikras.tools.common.exception.ErrorsCollection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Inject;

import static com.netikras.tools.common.remote.http.HttpStatus.UNAUTHORIZED;
import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

/**
 * Created by netikras on 17.12.14.
 */

public class ScheduledUpdateService extends IntentService {

    private static final String TAG = "UpdateService";

    LectureDao lectureCache;

    @Inject
    DataManager dataManager;
    @Inject
    PreferencesHelper preferencesHelper;
    @Inject
    Exchange exchange;
    @Inject
    CacheManager cacheManager;

    private static final int SVC_ID = R.id.UPDATES_SERVICE_ID;


    public ScheduledUpdateService() {
        super("ScheduledUpdateService");
        Log.i(TAG, "Constructing updater");
    }


    private LectureDataStore getLectureDataStore() {
        return dataManager.getStore(LectureDataStore.class);
    }

    private CommentsDataStore getCommentsDataStore() {
        return dataManager.getStore(CommentsDataStore.class);
    }

    private UserDataStore getUserDataStore() {
        return dataManager.getStore(UserDataStore.class);
    }

    private StudentDataStore getStudentDataStore() {
        return dataManager.getStore(StudentDataStore.class);
    }

    private LecturerDataStore getLecturerDataStore() {
        return dataManager.getStore(LecturerDataStore.class);
    }

    private GuestDataStore getGuestDataStore() {
        return dataManager.getStore(GuestDataStore.class);
    }


    protected <VIEW extends MvpView> void startView(Class<VIEW> viewClass, ViewTask<VIEW> task) {
        Intent intent = new Intent(this, viewClass);
        intent.putExtra("task", exchange.put(task));
        startActivity(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setUp();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(TAG, "handling intent...");

        boolean autostarted = intent.getBooleanExtra("autostart", false);
        String lectureId = intent.getStringExtra("LECTURE");

        if (autostarted) {
            if (!preferencesHelper.isAutostartEnabled()) {
                stopSelf();
                return;
            }
        }

        if (!isNullOrEmpty(lectureId)) {
            Log.d(TAG, "onHandleIntent: Lecture ID: " + lectureId);
            notifyLectureStartsSoon(lectureId);
            stopSelf();
            return;
        }

        update();
        reschedule(TimeUnit.MINUTES.toMillis(preferencesHelper.getUpdatePeriod()), getApplicationContext());
    }

    protected void setUp() {
        DepInjector.inject(this);
        lectureCache = cacheManager.getDao(LectureDao.class);
//        onAttach(this);
//        presenter.onAttach(this);
//        fields = initFields(new StudentInfoActivity.ViewFields());
    }


    private long getHours() {
        return preferencesHelper.getFetchLecturesAheadHours();
    }


    private void update() {
        Log.d(TAG, "going for update() @ " + new Date());

        final Map<String, String> entitiesComments = new ConcurrentHashMap<>();
        final Map<String, LectureDto> lectures = new ConcurrentHashMap<>();

        boolean lectureNotificationsEnabled = preferencesHelper.isLectureNotificationsEnabled();
        boolean commentsNotificationsEnabled = preferencesHelper.isCommentNotificationsEnabled();

        final SubscribersMonitor monitor = new SubscribersMonitor() {
            @Override
            public void onAllFinished() {
                Log.d(TAG, "FINISHED UPDATING. Comments: " + entitiesComments);
                super.onAllFinished();
                if (commentsNotificationsEnabled && !entitiesComments.isEmpty()) {
                    for (Map.Entry<String, String> entry : entitiesComments.entrySet()) {
                        if (isNullOrEmpty(entry.getValue())) {
                            continue;
                        }

                        switch (entry.getValue()) {
                            case "LECTURE":
                                notifyCommentsForLecture(entry.getKey());
                                break;
                            case "ASSIGNMENT":
                                notifyCommentsForAssignment(entry.getKey());
                                break;
                            case "TEST":
                                notifyCommentsForTest(entry.getKey());
                                break;
                            default:
                                break;
                        }
                    }
                }

                if (lectureNotificationsEnabled && !isNullOrEmpty(lectures)) {

                    long notifyLecturesBefore = TimeUnit.MINUTES.toMillis(preferencesHelper.getNotifyBeforePeriod());

                    for (Map.Entry<String, LectureDto> entry : lectures.entrySet()) {
                        LectureDto dto = entry.getValue();

                        long notifyTime = dto.getStartsOn().getTime() - notifyLecturesBefore;
                        if (notifyTime <= System.currentTimeMillis() + 10000 // basically all old lectures are matched by this.
                                && dto.getStartsOn().getTime() + 10000 > System.currentTimeMillis()) { // narrows results down to only lectures that have not started yet
                            Log.d(TAG, "onAllFinished: Notifying lecture is to start soon");
                            notifyLectureStartsSoon(dto.getId());
                        } else {
                            Log.d(TAG, "onAllFinished: Scheduling lecture id:" + dto.getId() + " start notification @" + notifyTime + " [" + new Date(notifyTime) + "]");
                            Intent intent = new Intent(ScheduledUpdateService.this, ScheduledUpdateService.this.getClass());
                            intent.putExtra("LECTURE", dto.getId());

                            reschedule(notifyTime, dto.getId().hashCode(), intent, getApplicationContext());
                        }
                    }
                }
            }
        };

        getUserDataStore().getCurrentUser(new Subscriber<UserDto>() {
            @Override
            protected SubscribersMonitor getMonitor() {
                return monitor;
            }

            @Override
            public void onError(ErrorsCollection errors) {
                super.onError(errors);
                Log.d(TAG, "" + errors);
                if (isNullOrEmpty(errors)) {
                    return;
                }

                for (ErrorBody error : errors) {
                    if (error == null) {
                        continue;
                    }
                    if (UNAUTHORIZED.getCode() == error.getStatus()) {
                        getUserDataStore().refresh();
                        showNotification(error.getMessage1(), error.getMessage2(), LoginActivity.class, "");
                        continue;
                    }
                    showNotification(error.getMessage1(), error.getMessage2(), MainActivity.class, "");
                }

            }

            @Override
            public void onSuccess(UserDto response) {
                preferencesHelper.setCurrentUser(response);

                if (response == null || isNullOrEmpty(response.getId())) {
                    Log.d(TAG, "onSuccess: apparently app did not receive any user info from the server. App might be cut off the network");
                    // https://stackoverflow.com/questions/45522784/why-i-am-getting-sokettimeoutexception-in-sleep-mode-of-device
                    return;
                }
                if ("guest".equalsIgnoreCase(response.getName())) {
                    executeOnError(new ErrorBody().setMessage1("Unauthorized").setMessage2("Not logged in (guest)").setStatus(UNAUTHORIZED.getCode()));
                    return;
                }
                getUserDataStore().getById(response.getId(), new Subscriber<UserDto>() {

                    protected Subscriber<List<CommentDto>> getCommentsSubscriber() {
                        return new Subscriber<List<CommentDto>>() {
                            @Override
                            protected SubscribersMonitor getMonitor() {
                                return monitor;
                            }

                            @Override
                            public synchronized void onSuccess(List<CommentDto> response) { // cache is already updated
                                if (isNullOrEmpty(response)) {
                                    return;
                                }
                                for (CommentDto dto : response) {
                                    entitiesComments.put(dto.getEntityId(), dto.getEntityType());
                                }
                            }
                        };
                    }

                    @Override
                    protected SubscribersMonitor getMonitor() {
                        return monitor;
                    }

                    @Override
                    public void onSuccess(UserDto response) {
                        if (response == null || isNullOrEmpty(response.getId())) {
                            return;
                        }
                        if (response.getPerson() == null || isNullOrEmpty(response.getPerson().getId())) {
                            return;
                        }

                        preferencesHelper.setCurrentUser(response);
                        PersonDto person = response.getPerson();
                        final long lastLecturesCommentsUpdate = getCommentsDataStore().getLastUpdateByType("LECTURE", null);
                        final long lastTestsCommentsUpdate = getCommentsDataStore().getLastUpdateByType("TEST", null);
                        final long lastAssignmentsCommentsUpdate = getCommentsDataStore().getLastUpdateByType("ASSIGNMENT", null);


                        final Subscriber<Collection<LectureDto>> lecturesSubscriber = new Subscriber<Collection<LectureDto>>() {
                            @Override
                            protected SubscribersMonitor getMonitor() {
                                return monitor;
                            }

                            @Override
                            public void onSuccess(Collection<LectureDto> response) { // already updated in cache
                                if (isNullOrEmpty(response)) {
                                    return;
                                }
                                List<String> lecturesIds = new ArrayList<>();
                                List<String> assignmentsIds = new ArrayList<>();
                                List<String> testsIds = new ArrayList<>();
                                for (LectureDto dto : response) {
                                    lectures.put(dto.getId(), dto);
                                    lecturesIds.add(dto.getId());
                                    if (!isNullOrEmpty(dto.getTests())) {
                                        for (DisciplineTestDto disciplineTestDto : dto.getTests()) {
                                            testsIds.add(disciplineTestDto.getId());
                                        }
                                    }
                                    if (!isNullOrEmpty(dto.getAssignments())) {
                                        for (AssignmentDto assignmentDto : dto.getAssignments()) {
                                            assignmentsIds.add(assignmentDto.getId());
                                        }
                                    }
                                }
                                getCommentsDataStore().getChangesAfterForEntities(lecturesIds, lastLecturesCommentsUpdate, getCommentsSubscriber());

                                if (!isNullOrEmpty(testsIds)) {
                                    getCommentsDataStore().getChangesAfterForEntities(testsIds, lastTestsCommentsUpdate, getCommentsSubscriber());
                                }
                                if (!isNullOrEmpty(assignmentsIds)) {
                                    getCommentsDataStore().getChangesAfterForEntities(assignmentsIds, lastAssignmentsCommentsUpdate, getCommentsSubscriber());
                                }
                            }
                        };

                        getStudentDataStore().getAllByPerson(person.getId(), new Subscriber<Collection<StudentDto>>() {
                            @Override
                            protected SubscribersMonitor getMonitor() {
                                return monitor;
                            }

                            @Override
                            public void onSuccess(Collection<StudentDto> response) {
                                if (isNullOrEmpty(response)) {
                                    return;
                                }
                                for (StudentDto studentDto : response) {
                                    getLectureDataStore().getAllByStudent(studentDto.getId(), TimeUnit.HOURS, getHours(), lecturesSubscriber);
                                }
                            }
                        });

                        getLecturerDataStore().getAllByPerson(person.getId(), new Subscriber<Collection<LecturerDto>>() {
                            @Override
                            protected SubscribersMonitor getMonitor() {
                                return monitor;
                            }

                            @Override
                            public void onSuccess(Collection<LecturerDto> response) {
                                if (isNullOrEmpty(response)) {
                                    return;
                                }
                                for (LecturerDto lecturerDto : response) {
                                    getLectureDataStore().getAllByLecturer(lecturerDto.getId(), TimeUnit.HOURS, getHours(), lecturesSubscriber);
                                }
                            }
                        });

                        getGuestDataStore().getAllByPerson(person.getId(), new Subscriber<Collection<LectureGuestDto>>() {
                            @Override
                            protected SubscribersMonitor getMonitor() {
                                return monitor;
                            }

                            @Override
                            public void onSuccess(Collection<LectureGuestDto> response) {
                                if (isNullOrEmpty(response)) {
                                    return;
                                }
                                for (LectureGuestDto guestDto : response) {
                                    getLectureDataStore().getAllByGuest(guestDto.getId(), TimeUnit.HOURS, getHours(), lecturesSubscriber);
                                }
                            }
                        });
                    }
                });
            }
        });

    }


    static void reschedule(long runAfterMs, Context context) {

        long nextRun = System.currentTimeMillis() + runAfterMs;

        Log.d(TAG, "Rescheduling. Next run: " + new Date(nextRun));

        Intent intent = new Intent(context, ScheduledUpdateService.class);
        reschedule(nextRun, SVC_ID, intent, context);
    }

    static void reschedule(long runAt, int wakeupId, Intent intent, Context context) {

        wakeupId = Math.abs(wakeupId);

        PendingIntent pendingIntent = PendingIntent.getService(context, wakeupId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        Log.d(TAG, "reschedule: wakeUpId:" + wakeupId + ", wakeUpTime: " + new Date(runAt));
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, runAt, pendingIntent);
    }

    private void notifyLectureStartsSoon(String id) {
        getLectureDataStore().getById(id, new Subscriber<LectureDto>() {
            @Override
            public void onCacheHit(LectureDto response) {
                setFetchRequired(false);
                executeOnSuccess(response);
            }

            @Override
            public void onSuccess(LectureDto lecture) {

                StringBuilder bodyBuilder = new StringBuilder();
                bodyBuilder.append("(").append(CommonUtils.datetimeToTime(lecture.getStartsOn())).append(") ");
                if (lecture.getDiscipline() != null) {
                    bodyBuilder.append(lecture.getDiscipline().getTitle());
                }
                if (!isNullOrEmpty(lecture.getAssignments())) {
                    bodyBuilder.append(" ").append(String.format(getString(R.string.template_assignments_count), lecture.getAssignments().size()));
                }
                if (!isNullOrEmpty(lecture.getTests())) {
                    bodyBuilder.append(" ").append(String.format(getString(R.string.template_tests_count), lecture.getTests().size()));
                }

                showNotification(getString(R.string.notif_lecture_will_start_shortly), bodyBuilder.toString(), LectureInfoActivity.class, id);
            }
        });
    }

    private void notifyCommentsForLecture(String id) {
        showNotification(getString(R.string.notif_lecture_has_new_comments), getString(R.string.notif_click_to_see_more), LectureInfoActivity.class, id);
    }

    private void notifyCommentsForAssignment(String id) {
        showNotification(getString(R.string.notif_assignment_has_new_comments), getString(R.string.notif_click_to_see_more), AssignmentActivity.class, id);
    }

    private void notifyCommentsForTest(String id) {
        showNotification(getString(R.string.notif_test_has_new_comments), getString(R.string.notif_click_to_see_more), TestInfoActivity.class, id);
    }

    private void showNotification(String title, String text, Class view, String entityId) {
        PendingIntent pendingIntent = null;
        int requestId = entityId.hashCode();
        if (view != null) {
            Intent intent = new Intent(ScheduledUpdateService.this, view);
            intent.putExtra("cached_id", entityId);
            pendingIntent = PendingIntent.getActivity(ScheduledUpdateService.this, requestId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        }
        Log.d(TAG, "Popping notification for entity " + view + " w/ ID: " + entityId);
        showNotification(ScheduledUpdateService.this, title, text, requestId, pendingIntent);
    }

    public void showNotification(Context context, String title, String text, int id, PendingIntent intent) {
//        NotificationCompat.Builder notification = new NotificationCompat.Builder(context, "")
        NotificationCompat.Builder notification = null;

        if (Build.VERSION.SDK_INT < 26) {
            notification = new NotificationCompat.Builder(context);
        } else {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel("default", "Channel name", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Channel description");
            notificationManager.createNotificationChannel(channel);
            notification = new NotificationCompat.Builder(context, channel.getId());
        }

        notification
                .setSmallIcon(R.drawable.icon)
                .setContentTitle(title)
                .setContentText(text)
                .setContentIntent(intent);
        showNotification(notification, id, context);
    }


    public void showNotification(NotificationCompat.Builder notificationBuilder, int id, Context context) {
        if (!preferencesHelper.isNotificationsEnabled()) {
            Log.d(TAG, "showNotification: Notifications are disabled by the user.");
            return;
        }
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify("com.netikras.studies.studentbuddy.api.client.android", id, notificationBuilder.build());
    }


}
