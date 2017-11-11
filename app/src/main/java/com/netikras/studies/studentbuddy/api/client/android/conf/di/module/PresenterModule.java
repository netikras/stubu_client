package com.netikras.studies.studentbuddy.api.client.android.conf.di.module;

import com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.ui.impl.presenter.CoursePresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.ui.presenter.CourseMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.ui.presenter.DisciplineMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.ui.impl.presenter.DisciplinePresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.ui.view.CourseMvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.discipline.ui.view.DisciplineMvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.impl.presenter.AssignmentPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.impl.presenter.LecturePresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.impl.presenter.TestsPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.presenter.AssignmentMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.presenter.LectureMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.presenter.TestMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.view.AssignmentMvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.view.LectureMvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.view.TestMvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecturer.ui.impl.presenter.LecturerPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecturer.ui.presenter.LecturerMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecturer.ui.view.LecturerMvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.impl.presenter.AddressPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.impl.presenter.BuildingPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.impl.presenter.BuildingSectionPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.impl.presenter.FloorPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.impl.presenter.LayoutPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.impl.presenter.RoomPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.presenter.AddressMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.presenter.BuildingMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.presenter.BuildingSectionMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.presenter.FloorMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.presenter.LayoutMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.view.AddressMvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.view.BuildingMvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.view.BuildingSectionMvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.view.FloorMvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.view.LayoutMvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.view.RoomMvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.login.ui.presenter.LoginMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.login.ui.impl.presenter.LoginPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.login.ui.view.LoginMvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.main.ui.impl.presenter.MainPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.main.ui.presenter.MainMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.main.ui.view.MainMvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.presenter.PersonMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.impl.presenter.PersonPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.presenter.UserMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.impl.presenter.UserPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.view.PersonMvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.person.ui.view.UserMvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.ui.impl.presenter.PersonnelMemberPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.ui.impl.presenter.SchoolDepartmentPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.ui.impl.presenter.SchoolPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.ui.presenter.PersonnelMemberMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.ui.presenter.SchoolDepartmentMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.ui.presenter.SchoolMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.ui.view.PersonnelMemberMvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.ui.view.SchoolDepartmentMvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.ui.view.SchoolMvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.settings.ui.presenter.SettingsMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.settings.ui.impl.presenter.SettingsPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.settings.ui.view.SettingsMvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.impl.presenter.StudentPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.impl.presenter.StudentsGroupPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.presenter.StudentsGroupMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.view.StudentMvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.student.ui.view.StudentsGroupMvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.sys.ui.impl.presenter.AdminPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.sys.ui.impl.presenter.PasswordReqPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.sys.ui.impl.presenter.RolePresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.sys.ui.impl.presenter.StatusPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.sys.ui.impl.presenter.SystemSettingPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.sys.ui.presenter.AdminMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.sys.ui.presenter.PasswordReqMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.sys.ui.presenter.RoleMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.sys.ui.presenter.StatusMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.sys.ui.presenter.SystemSettingMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.sys.ui.view.AdminMvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.sys.ui.view.PasswordReqMvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.sys.ui.view.RoleMvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.sys.ui.view.StatusMvpView;
import com.netikras.studies.studentbuddy.api.client.android.pieces.sys.ui.view.SystemSettingMvpView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by netikras on 17.11.1.
 */

@Module
public class PresenterModule {


    // Person
    //    @Singleton
    @Provides
    UserMvpPresenter<UserMvpView> userMvpPresenter(UserPresenter<UserMvpView> userPresenter) {
        return userPresenter;
    }

    //    @Singleton
    @Provides
    PersonMvpPresenter<PersonMvpView> personPresenter(PersonPresenter<PersonMvpView> personPresenter) {
        return personPresenter;
    }

    @Provides
    LoginMvpPresenter<LoginMvpView> loginPresenter(LoginPresenter<LoginMvpView> loginPresenter) {
        return loginPresenter;
    }

    @Provides
    SettingsMvpPresenter<SettingsMvpView> settingsPresenter(SettingsPresenter<SettingsMvpView> presenter) {
        return presenter;
    }


    // Discipline
    @Provides
    DisciplineMvpPresenter<DisciplineMvpView> disciplinePresenter(DisciplinePresenter<DisciplineMvpView> presenter) {
        return presenter;
    }

    @Provides
    CourseMvpPresenter<CourseMvpView> coursePresenter(CoursePresenter<CourseMvpView> presenter) {
        return presenter;
    }


    // Lecture
    @Provides
    LectureMvpPresenter<LectureMvpView> lecturePresenter(LecturePresenter<LectureMvpView> presenter) {
        return presenter;
    }

    @Provides
    AssignmentMvpPresenter<AssignmentMvpView> assignmentPresenter(AssignmentPresenter<AssignmentMvpView> presenter) {
        return presenter;
    }

    @Provides
    TestMvpPresenter<TestMvpView> testPresenter(TestsPresenter<TestMvpView> presenter) {
        return presenter;
    }


    // Lecturer
    @Provides
    LecturerMvpPresenter<LecturerMvpView> lecturerPresenter(LecturerPresenter<LecturerMvpView> presenter) {
        return presenter;
    }


    // Location
    @Provides
    AddressMvpPresenter<AddressMvpView> addressPresenter(AddressPresenter<AddressMvpView> presenter) {
        return presenter;
    }

    @Provides
    BuildingMvpPresenter<BuildingMvpView> buildingPresenter(BuildingPresenter<BuildingMvpView> presenter) {
        return presenter;
    }

    @Provides
    BuildingSectionMvpPresenter<BuildingSectionMvpView> buildingSectionPresenter(BuildingSectionPresenter<BuildingSectionMvpView> presenter) {
        return presenter;
    }

    @Provides
    FloorMvpPresenter<FloorMvpView> floorPresenter(FloorPresenter<FloorMvpView> presenter) {
        return presenter;
    }

    @Provides
    LayoutMvpPresenter<LayoutMvpView> layoutPresenter(LayoutPresenter<LayoutMvpView> presenter) {
        return presenter;
    }

    @Provides
    RoomPresenter<RoomMvpView> roomPresenter(RoomPresenter<RoomMvpView> presenter) {
        return presenter;
    }


    // Main
    @Provides
    MainMvpPresenter<MainMvpView> mainPresenter(MainPresenter<MainMvpView> presenter) {
        return presenter;
    }


    // School
    @Provides
    SchoolMvpPresenter<SchoolMvpView> schoolPresenter(SchoolPresenter<SchoolMvpView> presenter) {
        return presenter;
    }

    @Provides
    SchoolDepartmentMvpPresenter<SchoolDepartmentMvpView> departmentPresenter(SchoolDepartmentPresenter<SchoolDepartmentMvpView> presenter) {
        return presenter;
    }

    @Provides
    PersonnelMemberMvpPresenter<PersonnelMemberMvpView> personnelMemberPresenter(PersonnelMemberPresenter<PersonnelMemberMvpView> presenter) {
        return presenter;
    }


    // Student
    @Provides
    StudentPresenter<StudentMvpView> studentPresenter(StudentPresenter<StudentMvpView> presenter) {
        return presenter;
    }

    @Provides
    StudentsGroupMvpPresenter<StudentsGroupMvpView> studentsGroupPresenter(StudentsGroupPresenter<StudentsGroupMvpView> presenter) {
        return presenter;
    }


    // Sys
    @Provides
    AdminMvpPresenter<AdminMvpView> adminPresenter(AdminPresenter<AdminMvpView> presenter) {
        return presenter;
    }

    @Provides
    PasswordReqMvpPresenter<PasswordReqMvpView> pwreqPresenter(PasswordReqPresenter<PasswordReqMvpView> presenter) {
        return presenter;
    }

    @Provides
    RoleMvpPresenter<RoleMvpView> rolePresenter(RolePresenter<RoleMvpView> presenter) {
        return presenter;
    }

    @Provides
    StatusMvpPresenter<StatusMvpView> statusPresenter(StatusPresenter<StatusMvpView> presenter) {
        return presenter;
    }

    @Provides
    SystemSettingMvpPresenter<SystemSettingMvpView> systemSettingPresenter(SystemSettingPresenter<SystemSettingMvpView> presenter) {
        return presenter;
    }


}
