package com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.impl.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.TextView;

import com.netikras.studies.studentbuddy.api.client.android.R;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.DepInjector;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseViewFields;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.presenter.AssignmentMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.lecture.ui.view.AssignmentMvpView;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.AssignmentDto;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

import static com.netikras.studies.studentbuddy.api.client.android.util.CommonUtils.datetimeToDate;
import static com.netikras.studies.studentbuddy.api.client.android.util.CommonUtils.datetimeToTime;

/**
 * Created by netikras on 17.11.10.
 */

public class AssignmentActivity extends BaseActivity implements AssignmentMvpView {

    private ViewFields fields;

    @Inject
    AssignmentMvpPresenter<AssignmentMvpView> presenter;

    @Override
    protected List<Integer> excludeMenuItems() {
        return Arrays.asList();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);
        setUp();
    }

    @Override
    protected void setUp() {
        DepInjector.inject(this);
        onAttach(this);
        presenter.onAttach(this);
        fields = initFields(new ViewFields());
        addMenu();
        executeTask();
    }

    @Override
    public void show(AssignmentDto dto) {
        if (dto == null) {
            fields.clean();
            return;
        }

        fields.setDueDate(dto.getDue());
        fields.setAnnouncedDatetime(dto.getCreatedOn());
        fields.setId(dto.getId());
        fields.setDescription(dto.getDescription());

    }

    class ViewFields extends BaseViewFields {

        @BindView(R.id.txt_edit_assignment_id)
        EditText id;
        @BindView(R.id.txt_edit_assignment_title)
        EditText title;
        @BindView(R.id.txt_edit_assignment_description)
        EditText description;
        @BindView(R.id.txt_edit_assignment_announced_date)
        EditText announcedDate;
        @BindView(R.id.txt_edit_assignment_announced_time)
        EditText announcedTime;
        @BindView(R.id.txt_edit_assignment_due_date)
        EditText dueDate;
        @BindView(R.id.txt_edit_assignment_due_time)
        EditText dueTime;

        @BindView(R.id.txt_lbl_assignment_id)
        TextView lblId;

        public String getId() {
            return getString(id);
        }

        public void setId(String id) {
            setString(this.id, id);
        }

        public String getTitle() {
            return getString(title);
        }

        public void setTitle(String title) {
            setString(this.title, title);
        }

        public String getDescription() {
            return getString(description);
        }

        public void setDescription(String description) {
            setString(this.description, description);
        }

        public String getAnnouncedDate() {
            return getString(announcedDate);
        }

        public void setAnnouncedDate(String announcedDate) {
            setString(this.announcedDate, announcedDate);
        }

        public String getAnnouncedTime() {
            return getString(announcedTime);
        }

        public void setAnnouncedTime(String announcedTime) {
            setString(this.announcedTime, announcedTime);
        }

        public String getDueDate() {
            return getString(dueDate);
        }

        public void setDueDate(String dueDate) {
            setString(this.dueDate, dueDate);
        }

        public String getDueTime() {
            return getString(dueTime);
        }

        public void setDueTime(String dueTime) {
            setString(this.dueTime, dueTime);
        }


        public Date getAnnouncedDatetime() {
            return getDatetime(getAnnouncedDate(), getAnnouncedTime());
        }

        public void setAnnouncedDatetime(Date announcedDate) {
            setAnnouncedDate(datetimeToDate(announcedDate));
            setAnnouncedTime(datetimeToTime(announcedDate));
        }

        public Date getDueDatetime() {
            return getDatetime(getDueDate(), getDueTime());
        }

        public void setDueDate(Date dueDate) {
            setDueDate(datetimeToDate(dueDate));
            setDueDate(datetimeToTime(dueDate));
        }

        @Override
        protected Collection<TextView> getAllFields() {
            return Arrays.asList(id, title, description, announcedDate, announcedTime, dueDate, dueTime, announcedDate, announcedTime);
        }

        @Override
        protected Collection<TextView> getEditableFields() {
            return Arrays.asList(title, description, announcedDate, announcedTime, dueDate, dueTime);
        }

        @Override
        public void enableEdit(boolean enable) {
            super.enableEdit(enable);

            if (enable) {
                setVisible(id, true);
                setVisible(lblId, true);
            } else {
                setVisible(id, null);
                setVisible(lblId, false);
            }
        }
    }
}
