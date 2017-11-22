package com.netikras.studies.studentbuddy.api.client.android.conf.di;

import android.app.Activity;
import android.app.Application;

import com.netikras.studies.studentbuddy.api.client.android.conf.di.component.ActivityComponent;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.component.ApplicationComponent;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.component.DaggerActivityComponent;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.component.DaggerApplicationComponent;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.component.DaggerPresenterComponent;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.component.PresenterComponent;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.module.ActivityModule;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.module.ApplicationModule;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.module.DataModule;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.module.PresenterModule;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.MvpPresenter;
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

/**
 * Created by netikras on 17.10.30.
 */

public class DepInjector {

    private static ApplicationComponent applicationComponent;
    private static ActivityComponent activityComponent;
    private static PresenterComponent presenterComponent;


    public static ApplicationComponent getApplicationComponent() {
        if (applicationComponent == null) {
            throw new IllegalStateException("Application is not injected!");
        }
        return applicationComponent;
    }

    public static ActivityComponent getActivityComponent() {
        if (activityComponent == null) {
            throw new IllegalStateException("Activity is not injected");
        }
        return activityComponent;
    }

    public static PresenterComponent getPresenterComponent() {
        if (presenterComponent == null) {
            throw new IllegalStateException("Presenter is not injected");
        }
        return presenterComponent;
    }





    private static ApplicationComponent getApplicationComponent(Application application) {
        if (applicationComponent == null) {
            applicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(application))
                    .build();
        }
        applicationComponent.inject(application);
        return applicationComponent;
    }

    private static ActivityComponent getActivityComponent(Activity activity) {
        if (activityComponent == null) {
            activityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(activity))
//                    .applicationComponent(getApplicationComponent())
                    .applicationModule(new ApplicationModule(getApplicationComponent().application()))
                    .presenterModule(new PresenterModule())
                    .dataModule(new DataModule())
                    .build();
        }
        return activityComponent;
    }

    private static PresenterComponent getPresenterComponent(MvpPresenter presenter) {
        if (presenterComponent == null) {
            presenterComponent = DaggerPresenterComponent.builder()
                    .build();
        }
        return presenterComponent;
    }





    public static ApplicationComponent inject(Application app) {
        getApplicationComponent(app).inject(app);
        return getApplicationComponent();
    }

    public static ActivityComponent inject(Activity activity) {
        getActivityComponent(activity).inject(activity);
        return getActivityComponent();
    }

    public static ActivityComponent inject(BaseActivity activity) {
        getActivityComponent(activity).inject(activity);
        return getActivityComponent();
    }

    public static ActivityComponent inject(MainActivity activity) {
        getActivityComponent(activity).inject(activity);
        return getActivityComponent();
    }

    public static ActivityComponent inject(DisciplineInfoActivity activity) {
        getActivityComponent(activity).inject(activity);
        return getActivityComponent();
    }

    public static ActivityComponent inject(UserInfoActivity activity) {
        getActivityComponent(activity).inject(activity);
        return getActivityComponent();
    }

    public static ActivityComponent inject(PersonInfoActivity activity) {
        getActivityComponent(activity).inject(activity);
        return getActivityComponent();
    }

    public static ActivityComponent inject(LoginActivity activity) {
        getActivityComponent(activity).inject(activity);
        return getActivityComponent();
    }

    public static ActivityComponent inject(SettingsActivity activity) {
        getActivityComponent(activity).inject(activity);
        return getActivityComponent();
    }
    public static ActivityComponent inject(CourseActivity activity) {
        getActivityComponent(activity).inject(activity);
        return getActivityComponent();
    }
    public static ActivityComponent inject(AssignmentActivity activity) {
        getActivityComponent(activity).inject(activity);
        return getActivityComponent();
    }
    public static ActivityComponent inject(LectureInfoActivity activity) {
        getActivityComponent(activity).inject(activity);
        return getActivityComponent();
    }
    public static ActivityComponent inject(TestInfoActivity activity) {
        getActivityComponent(activity).inject(activity);
        return getActivityComponent();
    }
    public static ActivityComponent inject(LecturerInfoActivity activity) {
        getActivityComponent(activity).inject(activity);
        return getActivityComponent();
    }
    public static ActivityComponent inject(AddressActivity activity) {
        getActivityComponent(activity).inject(activity);
        return getActivityComponent();
    }
    public static ActivityComponent inject(BuildingActivity activity) {
        getActivityComponent(activity).inject(activity);
        return getActivityComponent();
    }
    public static ActivityComponent inject(BuildingSectionActivity activity) {
        getActivityComponent(activity).inject(activity);
        return getActivityComponent();
    }
    public static ActivityComponent inject(FloorActivity activity) {
        getActivityComponent(activity).inject(activity);
        return getActivityComponent();
    }
    public static ActivityComponent inject(LayoutActivity activity) {
        getActivityComponent(activity).inject(activity);
        return getActivityComponent();
    }
    public static ActivityComponent inject(RoomActivity activity) {
        getActivityComponent(activity).inject(activity);
        return getActivityComponent();
    }
    public static ActivityComponent inject(PersonnelMemberActivity activity) {
        getActivityComponent(activity).inject(activity);
        return getActivityComponent();
    }
    public static ActivityComponent inject(SchoolDepartmentActivity activity) {
        getActivityComponent(activity).inject(activity);
        return getActivityComponent();
    }
    public static ActivityComponent inject(SchoolActivity activity) {
        getActivityComponent(activity).inject(activity);
        return getActivityComponent();
    }
    public static ActivityComponent inject(GuestActivity activity) {
        getActivityComponent(activity).inject(activity);
        return getActivityComponent();
    }
    public static ActivityComponent inject(StudentInfoActivity activity) {
        getActivityComponent(activity).inject(activity);
        return getActivityComponent();
    }
    public static ActivityComponent inject(StudentsGroupInfoActivity activity) {
        getActivityComponent(activity).inject(activity);
        return getActivityComponent();
    }
    public static ActivityComponent inject(AdminActivity activity) {
        getActivityComponent(activity).inject(activity);
        return getActivityComponent();
    }
    public static ActivityComponent inject(PasswordReqActivity activity) {
        getActivityComponent(activity).inject(activity);
        return getActivityComponent();
    }
    public static ActivityComponent inject(RoleActivity activity) {
        getActivityComponent(activity).inject(activity);
        return getActivityComponent();
    }
    public static ActivityComponent inject(StatusActivity activity) {
        getActivityComponent(activity).inject(activity);
        return getActivityComponent();
    }
    public static ActivityComponent inject(SystemSettingsActivity activity) {
        getActivityComponent(activity).inject(activity);
        return getActivityComponent();
    }





    public static PresenterComponent inject(MainPresenter presenter) {
        getPresenterComponent(presenter).inject(presenter);
        return getPresenterComponent();
    }

    public static PresenterComponent inject(LoginPresenter presenter) {
        getPresenterComponent(presenter).inject(presenter);
        return getPresenterComponent();
    }

    public static PresenterComponent inject(UserPresenter presenter) {
        getPresenterComponent(presenter).inject(presenter);
        return getPresenterComponent();
    }

    public static PresenterComponent inject(PersonPresenter presenter) {
        getPresenterComponent(presenter).inject(presenter);
        return getPresenterComponent();
    }

    public static PresenterComponent inject(SettingsPresenter presenter) {
        getPresenterComponent(presenter).inject(presenter);
        return getPresenterComponent();
    }

    public static PresenterComponent inject(DisciplinePresenter presenter) {
        getPresenterComponent(presenter).inject(presenter);
        return getPresenterComponent();
    }
    public static PresenterComponent inject(CoursePresenter presenter) {
        getPresenterComponent(presenter).inject(presenter);
        return getPresenterComponent();
    }
    public static PresenterComponent inject(AssignmentPresenter presenter) {
        getPresenterComponent(presenter).inject(presenter);
        return getPresenterComponent();
    }
    public static PresenterComponent inject(LecturePresenter presenter) {
        getPresenterComponent(presenter).inject(presenter);
        return getPresenterComponent();
    }
    public static PresenterComponent inject(TestsPresenter presenter) {
        getPresenterComponent(presenter).inject(presenter);
        return getPresenterComponent();
    }
    public static PresenterComponent inject(LecturerPresenter presenter) {
        getPresenterComponent(presenter).inject(presenter);
        return getPresenterComponent();
    }
    public static PresenterComponent inject(AddressPresenter presenter) {
        getPresenterComponent(presenter).inject(presenter);
        return getPresenterComponent();
    }
    public static PresenterComponent inject(BuildingPresenter presenter) {
        getPresenterComponent(presenter).inject(presenter);
        return getPresenterComponent();
    }
    public static PresenterComponent inject(BuildingSectionPresenter presenter) {
        getPresenterComponent(presenter).inject(presenter);
        return getPresenterComponent();
    }
    public static PresenterComponent inject(FloorPresenter presenter) {
        getPresenterComponent(presenter).inject(presenter);
        return getPresenterComponent();
    }
    public static PresenterComponent inject(LayoutPresenter presenter) {
        getPresenterComponent(presenter).inject(presenter);
        return getPresenterComponent();
    }
    public static PresenterComponent inject(RoomPresenter presenter) {
        getPresenterComponent(presenter).inject(presenter);
        return getPresenterComponent();
    }
    public static PresenterComponent inject(PersonnelMemberPresenter presenter) {
        getPresenterComponent(presenter).inject(presenter);
        return getPresenterComponent();
    }
    public static PresenterComponent inject(SchoolDepartmentPresenter presenter) {
        getPresenterComponent(presenter).inject(presenter);
        return getPresenterComponent();
    }
    public static PresenterComponent inject(SchoolPresenter presenter) {
        getPresenterComponent(presenter).inject(presenter);
        return getPresenterComponent();
    }
    public static PresenterComponent inject(GuestPresenter presenter) {
        getPresenterComponent(presenter).inject(presenter);
        return getPresenterComponent();
    }
    public static PresenterComponent inject(StudentPresenter presenter) {
        getPresenterComponent(presenter).inject(presenter);
        return getPresenterComponent();
    }
    public static PresenterComponent inject(StudentsGroupPresenter presenter) {
        getPresenterComponent(presenter).inject(presenter);
        return getPresenterComponent();
    }
    public static PresenterComponent inject(AdminPresenter presenter) {
        getPresenterComponent(presenter).inject(presenter);
        return getPresenterComponent();
    }
    public static PresenterComponent inject(PasswordReqPresenter presenter) {
        getPresenterComponent(presenter).inject(presenter);
        return getPresenterComponent();
    }
    public static PresenterComponent inject(RolePresenter presenter) {
        getPresenterComponent(presenter).inject(presenter);
        return getPresenterComponent();
    }
    public static PresenterComponent inject(StatusPresenter presenter) {
        getPresenterComponent(presenter).inject(presenter);
        return getPresenterComponent();
    }
    public static PresenterComponent inject(SystemSettingPresenter presenter) {
        getPresenterComponent(presenter).inject(presenter);
        return getPresenterComponent();
    }



}
