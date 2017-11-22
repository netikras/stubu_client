package com.netikras.studies.studentbuddy.api.client.android.conf.di.component;

import com.netikras.studies.studentbuddy.api.client.android.conf.di.PerPresenter;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.module.ApiConsumerModule;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.module.ApiHttpModule;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.module.ApplicationModule;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.module.DataModule;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.module.PresenterModule;
import com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.ui.impl.presenter.CoursePresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.ui.impl.presenter.DisciplinePresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.ui.impl.view.CourseActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.ui.impl.view.DisciplineInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.impl.presenter.AssignmentPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.impl.presenter.LecturePresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.impl.presenter.TestsPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.impl.view.AssignmentActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.impl.view.LectureInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.impl.view.TestInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecturer.ui.impl.presenter.LecturerPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecturer.ui.impl.view.LecturerInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.impl.presenter.AddressPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.impl.presenter.BuildingPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.impl.presenter.BuildingSectionPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.impl.presenter.FloorPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.impl.presenter.LayoutPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.impl.presenter.RoomPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.impl.view.AddressActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.impl.view.BuildingActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.impl.view.BuildingSectionActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.impl.view.FloorActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.impl.view.LayoutActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.impl.view.RoomActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.login.ui.impl.presenter.LoginPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.login.ui.impl.view.LoginActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.main.ui.impl.presenter.MainPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.main.ui.impl.view.MainActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.impl.presenter.PersonPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.impl.presenter.UserPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.impl.view.PersonInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.impl.view.UserInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.ui.impl.presenter.PersonnelMemberPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.ui.impl.presenter.SchoolDepartmentPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.ui.impl.presenter.SchoolPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.ui.impl.view.PersonnelMemberActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.ui.impl.view.SchoolActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.ui.impl.view.SchoolDepartmentActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.settings.ui.impl.presenter.SettingsPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.settings.ui.impl.view.SettingsActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.impl.presenter.GuestPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.impl.presenter.StudentPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.impl.presenter.StudentsGroupPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.impl.view.GuestActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.impl.view.StudentInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.impl.view.StudentsGroupInfoActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.sys.ui.impl.presenter.AdminPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.sys.ui.impl.presenter.PasswordReqPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.sys.ui.impl.presenter.RolePresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.sys.ui.impl.presenter.StatusPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.sys.ui.impl.presenter.SystemSettingPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.sys.ui.impl.view.AdminActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.sys.ui.impl.view.PasswordReqActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.sys.ui.impl.view.RoleActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.sys.ui.impl.view.StatusActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.sys.ui.impl.view.SystemSettingsActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by netikras on 17.11.1.
 */

@Singleton
@Component(
//        dependencies = {ApplicationComponent.class},
        modules = {ApplicationModule.class, PresenterModule.class, DataModule.class, ApiHttpModule.class, ApiConsumerModule.class}
)
public interface PresenterComponent {

    void inject(LoginPresenter<LoginActivity> presenter);

    void inject(MainPresenter<MainActivity> presenter);

    void inject(UserPresenter<UserInfoActivity> presenter);

    void inject(PersonPresenter<PersonInfoActivity> presenter);

    void inject(SettingsPresenter<SettingsActivity> presenter);

    void inject(DisciplinePresenter<DisciplineInfoActivity> presenter);

    void inject(CoursePresenter<CourseActivity> presenter);

    void inject(AssignmentPresenter<AssignmentActivity> presenter);

    void inject(LecturePresenter<LectureInfoActivity> presenter);

    void inject(TestsPresenter<TestInfoActivity> presenter);

    void inject(LecturerPresenter<LecturerInfoActivity> presenter);

    void inject(AddressPresenter<AddressActivity> presenter);

    void inject(BuildingPresenter<BuildingActivity> presenter);

    void inject(BuildingSectionPresenter<BuildingSectionActivity> presenter);

    void inject(FloorPresenter<FloorActivity> presenter);

    void inject(LayoutPresenter<LayoutActivity> presenter);

    void inject(RoomPresenter<RoomActivity> presenter);

    void inject(PersonnelMemberPresenter<PersonnelMemberActivity> presenter);

    void inject(SchoolDepartmentPresenter<SchoolDepartmentActivity> presenter);

    void inject(SchoolPresenter<SchoolActivity> presenter);

    void inject(GuestPresenter<GuestActivity> presenter);

    void inject(StudentPresenter<StudentInfoActivity> presenter);

    void inject(StudentsGroupPresenter<StudentsGroupInfoActivity> presenter);

    void inject(AdminPresenter<AdminActivity> presenter);

    void inject(PasswordReqPresenter<PasswordReqActivity> presenter);

    void inject(RolePresenter<RoleActivity> presenter);

    void inject(StatusPresenter<StatusActivity> presenter);

    void inject(SystemSettingPresenter<SystemSettingsActivity> presenter);

}
