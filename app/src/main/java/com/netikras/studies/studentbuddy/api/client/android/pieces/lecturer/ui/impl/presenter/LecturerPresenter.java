package com.netikras.studies.studentbuddy.api.client.android.pieces.lecturer.ui.impl.presenter;

import android.content.Context;
import android.content.Intent;

import com.netikras.studies.studentbuddy.api.client.android.data.DataManager;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity.ViewTask;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BasePresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.ui.presenter.DisciplineMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.ui.view.DisciplineMvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecturer.data.LecturerDataStore;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecturer.ui.impl.view.LecturerInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecturer.ui.presenter.LecturerMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecturer.ui.view.LecturerMvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.presenter.PersonMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.view.PersonMvpView;
import com.netikras.studies.studentbuddy.core.data.api.dto.PersonDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.DisciplineDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LecturerDto;

import javax.inject.Inject;

/**
 * Created by netikras on 17.11.10.
 */

public class LecturerPresenter<V extends LecturerMvpView> extends BasePresenter<V> implements LecturerMvpPresenter<V> {

    @Inject
    DisciplineMvpPresenter<DisciplineMvpView> disciplinePresenter;

    @Inject
    PersonMvpPresenter<PersonMvpView> personPresenter;

    @Inject
    public LecturerPresenter(DataManager dataManager) {
        super(dataManager);
    }

    private LecturerDataStore getDataStore() {
        return getDataManager().getStore(LecturerDataStore.class);
    }

    @Override
    public void startView(Context fromContext) {
        Intent intent = new Intent(fromContext, LecturerInfoActivity.class);
        fromContext.startActivity(intent);
    }

    @Override
    public void show(Context context, LecturerDto lecturer) {
        startView(context, LecturerInfoActivity.class, new ViewTask<LecturerInfoActivity>() {
            @Override
            public void execute() {
                getActivity().show(lecturer);
            }
        });
    }

    @Override
    public void showPerson(Context context, PersonDto person) {
        personPresenter.showPerson(context, person);
    }

    @Override
    public void showDiscipline(Context listContext, DisciplineDto item) {
        disciplinePresenter.show(listContext, item);
    }
}
