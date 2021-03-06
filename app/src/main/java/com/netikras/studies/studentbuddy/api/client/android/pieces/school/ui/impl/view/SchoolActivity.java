package com.netikras.studies.studentbuddy.api.client.android.pieces.school.ui.impl.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.netikras.studies.studentbuddy.api.client.android.R;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.DepInjector;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseViewFields;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.list.ListHandler;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.list.ListRow;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.ui.presenter.SchoolMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.ui.view.SchoolMvpView;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Result;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.SchoolDepartmentDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.SchoolDto;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.netikras.tools.common.security.IntegrityUtils.coalesce;
import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

/**
 * Created by netikras on 17.11.10.
 */

public class SchoolActivity extends BaseActivity implements SchoolMvpView {

    private ViewFields fields;

    @Inject
    SchoolMvpPresenter<SchoolMvpView> presenter;

    private static SchoolDto lastEntry = null;

    @Override
    protected List<Integer> excludeMenuItems() {
        return Arrays.asList(R.id.main_menu_create, R.id.main_menu_delete);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school);
        setUp();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        lastEntry = collect();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public ViewFields getFields() {
        return fields;
    }

    @Override
    protected void setUp() {
        DepInjector.inject(this);
        onAttach(this);
        presenter.onAttach(this);
        fields = initFields(new ViewFields());
        addMenu();
        if (lastEntry != null) {
            show(lastEntry);
        }
        executeTask();
    }

    public SchoolDto collect() {
        SchoolDto dto = new SchoolDto();
        dto.setId(getFields().getId());
        dto.setTitle(getFields().getTitle());
        dto.setDepartments(getFields().getDepartments());
        return dto;
    }

    public void show(SchoolDto schoolDto) {
        getFields().reset();
        if (schoolDto == null) {
            return;
        }

        getFields().setId(schoolDto.getId());
        getFields().setTitle(schoolDto.getTitle());
        getFields().setDepartments(schoolDto.getDepartments());

        if (lastEntry != null) {
            lastEntry = null;
        } else {
            prepare(collect());
        }
    }

    @Override
    protected boolean isPartial() {
        SchoolDto dto = collect();
        if (dto == null) {
            return true;
        }
        Object item = coalesce(dto.getDepartments());
        return item == null;
    }

    private void prepare(SchoolDto entity) {
        if (hasTriedToFetch()) {
            setTriedToFetch(false);
            return;
        }
        if (entity == null || isNullOrEmpty(entity.getId())) {
            return;
        }
        if (isPartial()) {
            showLoading();
            setTriedToFetch(true);
            presenter.getById(new ErrorsAwareSubscriber<SchoolDto>() {
                @Override
                public void onCacheHit(SchoolDto response) {
                    if (response != null && !isNullOrEmpty(response.getTitle())) {
                        setFetchRequired(false);
                        executeOnSuccess(response);
                    }
                }

                @Override
                public void onSuccess(SchoolDto response) {
                    runOnUiThread(() -> show(response));
                }
            }, entity.getId());
        }
    }

    @OnClick(R.id.btn_school_departments)
    public void showDepartmentsList() {
        List<SchoolDepartmentDto> departments = getFields().getDepartments();
        if (isNullOrEmpty(departments)) {
            onError(R.string.err_no_departments);
            return;
        }

        showList(this, new ListHandler<SchoolDepartmentDto>() {
            @Override
            public ListRow<SchoolDepartmentDto> getNewRow(View convertView) {
                return new DepartmentRow(convertView);
            }

            @Override
            public List<SchoolDepartmentDto> getListData() {
                return departments;
            }

            @Override
            public void onRowClick(SchoolDepartmentDto item) {
                onError(getListContext(), "Department selected: " + item.getTitle());
                System.out.println("Department selected: " + item.getId());
                startView(SchoolDepartmentActivity.class, new ViewTask<SchoolDepartmentActivity>() {
                    @Override
                    public void execute() {
                        getActivity().show(item);
                    }
                });
            }

            @Override
            public String getToolbarText() {
                return getString(R.string.title_departments);
            }

            class DepartmentRow extends ListRow<SchoolDepartmentDto> {

                TextView text;

                public DepartmentRow(View rowView) {
                    super(null);
                    text = getDefaultListTextView(rowView);
                    rowView.setTag(this);
                }

                @Override
                public void assign(SchoolDepartmentDto item) {
                    text.setText(item.getTitle());
                }
            }
        });
    }

    @Override
    protected void menuOnClickRefresh() {
        showLoading();
        presenter.getById(new ErrorsAwareSubscriber<SchoolDto>(){
            @Override
            public void onSuccess(SchoolDto response) {
                if (response != null) {
                    runOnUiThread(() -> show(response));
                }
            }
        }, getFields().getId());
    }

    @Override
    protected void menuOnClickEdit() {
        getFields().enableEdit(true);
    }

    @Override
    protected void menuOnClickSave() {
        SchoolDto dto = collect();
        showLoading();
        presenter.update(new ErrorsAwareSubscriber<SchoolDto>() {
            @Override
            public void onSuccess(SchoolDto response) {
                if (response != null) {
                    runOnUiThread(() -> show(response));
                }
            }
        }, dto);
    }

    public class ViewFields extends BaseViewFields {
        @BindView(R.id.txt_edit_school_id)
        EditText id;
        @BindView(R.id.txt_edit_school_title)
        EditText title;
        @BindView(R.id.btn_school_departments)
        Button departments;

        @BindView(R.id.txt_lbl_school_id)
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

        public String getDepartmentsCount() {
            return getString(departments);
        }

        public void setDepartmentsCount(String count) {
            setString(this.departments, count);
        }


        public List<SchoolDepartmentDto> getDepartments() {
            return (List<SchoolDepartmentDto>) getTag(departments);
        }

        public void setDepartments(List<SchoolDepartmentDto> departments) {
            setTag(this.departments, departments);
            if (departments != null) {
                setDepartmentsCount("" + departments.size());
            }
        }

        @Override
        protected Collection<TextView> getAllFields() {
            return Arrays.asList(id, title, departments);
        }

        @Override
        protected Map<TextView, Integer> getEditableFields() {
            Map<TextView, Integer> types = super.getEditableFields();

            types.put(title, InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

            return types;
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
