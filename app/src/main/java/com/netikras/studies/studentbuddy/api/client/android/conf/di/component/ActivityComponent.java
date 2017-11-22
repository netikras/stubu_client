package com.netikras.studies.studentbuddy.api.client.android.conf.di.component;

import android.app.Activity;

import com.netikras.studies.studentbuddy.api.client.android.conf.di.module.ActivityModule;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.module.ApiConsumerModule;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.module.ApiHttpModule;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.module.ApplicationModule;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.module.DataModule;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.module.PresenterModule;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.ui.impl.view.CourseActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.ui.impl.view.DisciplineInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.impl.view.AssignmentActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.impl.view.LectureInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.impl.view.TestInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecturer.ui.impl.view.LecturerInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.impl.view.AddressActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.impl.view.BuildingActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.impl.view.BuildingSectionActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.impl.view.FloorActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.impl.view.LayoutActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.impl.view.RoomActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.login.ui.impl.view.LoginActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.login.ui.presenter.LoginMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.login.ui.view.LoginMvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.main.ui.impl.view.MainActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.impl.view.PersonInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.impl.view.UserInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.ui.impl.view.PersonnelMemberActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.ui.impl.view.SchoolActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.ui.impl.view.SchoolDepartmentActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.settings.ui.impl.view.SettingsActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.impl.view.GuestActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.impl.view.StudentInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.impl.view.StudentsGroupInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.sys.ui.impl.view.AdminActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.sys.ui.impl.view.PasswordReqActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.sys.ui.impl.view.RoleActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.sys.ui.impl.view.StatusActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.sys.ui.impl.view.SystemSettingsActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by netikras on 17.10.30.
 */

@Singleton
@Component(
        modules = {
                ActivityModule.class,
                ApplicationModule.class,
                PresenterModule.class,
                DataModule.class,
                ApiConsumerModule.class,
                ApiHttpModule.class
        }
)
public interface ActivityComponent {

    void inject(Activity activity);

    void inject(BaseActivity activity);

    void inject(DisciplineInfoActivity activity);

    void inject(LoginActivity activity);

    void inject(UserInfoActivity activity);

    void inject(PersonInfoActivity activity);

    void inject(MainActivity activity);

    void inject(SettingsActivity activity);

    void inject(CourseActivity activity);

    void inject(AssignmentActivity activity);

    void inject(LectureInfoActivity activity);

    void inject(TestInfoActivity activity);

    void inject(LecturerInfoActivity activity);

    void inject(AddressActivity activity);

    void inject(BuildingActivity activity);

    void inject(BuildingSectionActivity activity);

    void inject(FloorActivity activity);

    void inject(LayoutActivity activity);

    void inject(RoomActivity activity);

    void inject(PersonnelMemberActivity activity);

    void inject(SchoolDepartmentActivity activity);

    void inject(SchoolActivity activity);

    void inject(GuestActivity activity);

    void inject(StudentInfoActivity activity);

    void inject(StudentsGroupInfoActivity activity);

    void inject(AdminActivity activity);

    void inject(PasswordReqActivity activity);

    void inject(RoleActivity activity);

    void inject(StatusActivity activity);

    void inject(SystemSettingsActivity activity);


    LoginMvpPresenter<LoginMvpView> loginMvpViewLoginMvpPresenter();
}
