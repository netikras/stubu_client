package com.netikras.studies.studentbuddy.api.client.android.pieces.school.ui.impl.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import java.util.concurrent.TimeUnit;

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

        prepare(collect());
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

    private Result<SchoolDto> fetch(SchoolDto dto) {
        Result<SchoolDto> result = new Result<>();
        showLoading();
        presenter.getById(new ErrorsAwareSubscriber<SchoolDto>() {
            @Override
            public void onSuccess(SchoolDto response) {
                result.setValue(response);
            }
        }, dto.getId());

        return result;
    }

    private void prepare(SchoolDto entity) {
        if (entity == null || isNullOrEmpty(entity.getId())) {
            return;
        }
        if (isPartial()) {
            Result<SchoolDto> result = fetch(entity);
            SchoolDto dto = result.get(5, TimeUnit.SECONDS);
            if (!result.isTimedOut()) {
                show(dto);
            }
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

    public class ViewFields extends BaseViewFields {
        @BindView(R.id.txt_edit_school_id)
        EditText id;
        @BindView(R.id.txt_edit_school_title)
        EditText title;
        @BindView(R.id.btn_school_departments)
        Button departments;


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
    }

}
