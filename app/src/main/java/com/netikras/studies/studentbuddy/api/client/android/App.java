package com.netikras.studies.studentbuddy.api.client.android;

import android.app.Application;
import android.content.Intent;

import com.netikras.studies.studentbuddy.api.client.android.conf.di.DepInjector;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.component.ApplicationComponent;
import com.netikras.studies.studentbuddy.api.client.android.service.ScheduledUpdateService;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.RoleDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.RolePermissionDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.UserDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LectureGuestDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LecturerDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.StudentDto;

import java.util.ArrayList;
import java.util.List;

import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

/**
 * Created by netikras on 17.10.17.
 */

public class App extends Application {

    private static App current = null;

    private UserDto currentUser;
    private PersonRoles roles;
    private List<RoleDto> userRoles;

    private ApplicationComponent mApplicationComponent;

    public PersonRoles getRoles() {
        return roles;
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        current = this;
        mApplicationComponent = DepInjector.inject(this);
        roles = new PersonRoles();

        startUpdateService();
    }

    private void startUpdateService() {
        Intent intent = new Intent(this, ScheduledUpdateService.class);
        startService(intent);
    }

    public static App getCurrent() {
        return current;
    }

    public UserDto getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(UserDto currentUser) {
        this.currentUser = currentUser;
    }

    public void setUserRoles(List<RoleDto> userRoles) {
        this.userRoles = userRoles;
    }

    public List<RoleDto> getUserRoles() {
        return userRoles;
    }

    public boolean hasUserPermission(String resource, String action, String resourceId) {
        if (isNullOrEmpty(resource) || isNullOrEmpty(action)) {
            return false;
        }

        if (isNullOrEmpty(getUserRoles())) {
            return false;
        }

        resource = resource.toUpperCase();
        action = action.toUpperCase();

        for (RoleDto userRole : userRoles) {
            if (isNullOrEmpty(userRole.getPermissions())) {
                continue;
            }
            for (RolePermissionDto rolePermissionDto : userRole.getPermissions()) {
                if (resource.equals(rolePermissionDto.getResource())
                        && action.equals(rolePermissionDto.getAction())) {
                    if (rolePermissionDto.isStrict()) {
                        if (!isNullOrEmpty(rolePermissionDto.getEntityId())
                                && rolePermissionDto.getEntityId().equalsIgnoreCase(resourceId)) {
                            return true;
                        }
                    } else {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public static class PersonRoles {
        private List<StudentDto> roleStudents = new ArrayList<>();
        private List<LecturerDto> roleLecturers = new ArrayList<>();
        private List<LectureGuestDto> roleGuests = new ArrayList<>();

        public List<StudentDto> getRoleStudents() {
            return roleStudents;
        }

        public void setRoleStudents(List<StudentDto> roleStudents) {
            this.roleStudents = roleStudents;
        }

        public List<LecturerDto> getRoleLecturers() {
            return roleLecturers;
        }

        public void setRoleLecturers(List<LecturerDto> roleLecturers) {
            this.roleLecturers = roleLecturers;
        }

        public List<LectureGuestDto> getRoleGuests() {
            return roleGuests;
        }

        public void setRoleGuests(List<LectureGuestDto> roleGuests) {
            this.roleGuests = roleGuests;
        }
    }
}
