package com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.impl.presenter;

import android.content.Context;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity.ViewTask;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.data.TestDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.impl.view.TestInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.presenter.LectureMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.presenter.TestMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.view.LectureMvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.view.TestMvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecturer.ui.impl.presenter.LecturerPresenter;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.DisciplineTestDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LectureDto;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.10.
 */

public class TestsPresenter<V extends TestMvpView> extends BasePresenter<V> implements TestMvpPresenter<V> {

    @Inject
    LectureMvpPresenter<LectureMvpView> lecturePresenter;

    @Inject
    public TestsPresenter(DataManager dataManager) {
        super(dataManager);
    }

    private TestDataStore getDataStore() {
        return getDataManager().getStore(TestDataStore.class);
    }

    @Override
    public void startView(Context fromContext) {
        startView(fromContext, TestInfoActivity.class);
    }


    @Override
    public void showLecture(LectureDto lecture) {
        lecturePresenter.showLecture(lecture, getContext());
    }

    @Override
    public void show(Context context, DisciplineTestDto item) {
        startView(context, TestInfoActivity.class, new ViewTask<TestInfoActivity>() {
            @Override
            public void execute() {
                getActivity().show(item);
            }
        });
    }
}
