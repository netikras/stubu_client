package com.netikras.studies.studentbuddy.api.client.android.pieces.main.ui.impl.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.netikras.studies.studentbuddy.api.client.android.R;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.DepInjector;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.impl.view.LectureInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.login.ui.impl.view.LoginActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.main.ui.impl.fragments.GuestLectures;
import com.netikras.studies.studentbuddy.api.client.android.pieces.main.ui.impl.fragments.LecturerLectures;
import com.netikras.studies.studentbuddy.api.client.android.pieces.main.ui.impl.fragments.LecturesListFragment;
import com.netikras.studies.studentbuddy.api.client.android.pieces.main.ui.impl.fragments.StudentLectures;
import com.netikras.studies.studentbuddy.api.client.android.pieces.main.ui.impl.fragments.adapter.SectionsPageAdapter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.main.ui.presenter.MainMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.main.ui.view.MainMvpView;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Subscriber;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LectureDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

public class MainActivity extends BaseActivity implements MainMvpView {


    @Inject
    MainMvpPresenter<MainMvpView> presenter;

    SectionsPageAdapter adapter;

    @BindView(R.id.pager_main_act)
    ViewPager pager;
    @BindView(R.id.tabs_main_act)
    TabLayout tabLayout;

    private WatchedLecturesListHandler studentHandler;
    private WatchedLecturesListHandler guestHandler;
    private WatchedLecturesListHandler lecturerHandler;

    @Override
    protected List<Integer> excludeMenuItems() {
        return Arrays.asList(R.id.main_menu_create, R.id.main_menu_delete, R.id.main_menu_edit);
    }


    @Override
    protected void menuOnClickRefresh() {
        fetch();
    }

    private void fetch() {
        presenter.fetchLecturesForGuest(new Subscriber<Collection<LectureDto>>() {
            @Override
            public void onSuccess(Collection<LectureDto> response) {
                guestHandler.updateData((List<LectureDto>) response);
            }
        }, getCurrentUser().getPerson());

        presenter.fetchLecturesForStudent(new Subscriber<Collection<LectureDto>>() {
            @Override
            public void onSuccess(Collection<LectureDto> response) {
                studentHandler.updateData((List<LectureDto>) response);
            }
        }, getCurrentUser().getPerson());

        presenter.fetchLecturesForLecturer(new Subscriber<Collection<LectureDto>>() {
            @Override
            public void onSuccess(Collection<LectureDto> response) {
                lecturerHandler.updateData((List<LectureDto>) response);
            }
        }, getCurrentUser().getPerson());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUp();
    }

    @Override
    protected void setUp() {
        DepInjector.inject(this);
        onAttach(this);
        presenter.onAttach(this);
        addMenu();

        if (getCurrentUser() == null) {
            startView(LoginActivity.class, null);
            return;
        }

        executeTask();

        studentHandler = new WatchedLecturesListHandler(this);
        guestHandler = new WatchedLecturesListHandler(this);
        lecturerHandler = new WatchedLecturesListHandler(this);

        adapter = new SectionsPageAdapter(getSupportFragmentManager());

        setupPager(pager);

        tabLayout.setupWithViewPager(pager);

        fetch();

    }


    private void setupPager(ViewPager pager) {

        StudentLectures studentLectures = new StudentLectures();
        studentLectures.setContext(this);
        studentLectures.setListHandler(studentHandler);
        adapter.addTabFragment(studentLectures);

        GuestLectures guestLectures = new GuestLectures();
        guestLectures.setContext(this);
        guestLectures.setListHandler(guestHandler);
        adapter.addTabFragment(guestLectures);

        LecturerLectures lecturerLectures = new LecturerLectures();
        lecturerLectures.setContext(this);
        lecturerLectures.setListHandler(lecturerHandler);
        adapter.addTabFragment(lecturerLectures);

        pager.setAdapter(adapter);
    }


    static class WatchedLecturesListHandler extends LecturesListFragment.LecturesListHandler {

        private List<LectureDto> data = new ArrayList<>();
        private MainActivity activity;

        public WatchedLecturesListHandler(MainActivity activity) {
            this.activity = activity;
        }

        @Override
        public List<LectureDto> getListData() {
            return data;
        }

        public void updateData(List<LectureDto> newData) {
            data.clear();
            if (!isNullOrEmpty(newData)) {
                data.addAll(newData);
            }
            onDataSetChanged();
        }

        @Override
        public void onRowClick(LectureDto item) {
            activity.startView(LectureInfoActivity.class, new ViewTask<LectureInfoActivity>() {
                @Override
                public void execute() {
                    getActivity().show(item);
                }
            });
        }
    }

}
