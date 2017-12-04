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
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.impl.view.BuildingActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.ui.presenter.SchoolDepartmentMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.school.ui.view.SchoolDepartmentMvpView;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.BuildingDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.SchoolDepartmentDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.SchoolDto;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

/**
 * Created by netikras on 17.11.10.
 */

public class SchoolDepartmentActivity extends BaseActivity implements SchoolDepartmentMvpView {

    private ViewFields fields;

    @Inject
    SchoolDepartmentMvpPresenter<SchoolDepartmentMvpView> presenter;

    @Override
    protected List<Integer> excludeMenuItems() {
        return Arrays.asList(R.id.main_menu_create, R.id.main_menu_delete);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_department);
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


    public ViewFields getFields() {
        return fields;
    }

    public SchoolDepartmentDto collect() {
        SchoolDepartmentDto dto = new SchoolDepartmentDto();
        dto.setId(getFields().getId());
        dto.setTitle(getFields().getTitle());
        dto.setBuildings(getFields().getBuildings());
        dto.setSchool(getFields().getSchool());
        return dto;
    }

    public void show(SchoolDepartmentDto dto) {
        getFields().reset();

        if (dto == null) {
            return;
        }

        getFields().setId(dto.getId());
        getFields().setTitle(dto.getTitle());
        getFields().setBuildings(dto.getBuildings());
        getFields().setSchool(dto.getSchool());
    }

    @OnClick(R.id.btn_school_departments_buildings)
    public void showBuildingsList() {
        List<BuildingDto> buildings = getFields().getBuildings();
        if (isNullOrEmpty(buildings)) {
            onError(R.string.err_no_buildings);
            return;
        }

        showList(this, new ListHandler<BuildingDto>() {
            @Override
            public ListRow<BuildingDto> getNewRow(View convertView) {
                return new BuildingRow(convertView);
            }

            @Override
            public List<BuildingDto> getListData() {
                return buildings;
            }

            @Override
            public void onRowClick(BuildingDto item) {
                onError(getListContext(), "Building selected: " + item.getTitle());
                System.out.println("Building selected: " + item.getId());
                startView(BuildingActivity.class, new ViewTask<BuildingActivity>() {
                    @Override
                    public void execute() {
                        getActivity().show(item);
                    }
                });
            }

            @Override
            public String getToolbarText() {
                return getString(R.string.title_building);
            }

            class BuildingRow extends ListRow<BuildingDto> {

                TextView text;

                public BuildingRow(View rowView) {
                    super(null);
                    text = getDefaultListTextView(rowView);
                    rowView.setTag(this);
                }

                @Override
                public void assign(BuildingDto item) {
                    text.setText(item.getTitle());
                }
            }
        });
    }

    class ViewFields extends BaseViewFields {

        @BindView(R.id.txt_edit_school_department_id)
        EditText id;
        @BindView(R.id.txt_school_department_title)
        EditText title;
        @BindView(R.id.btn_school_departments_buildings)
        Button buildings;

        Button school;

        public String getId() {
            return getString( id);
        }

        public void setId(String id) {
            setString(this.id, id);
        }

        public String getTitle() {
            return getString( title);
        }

        public void setTitle(String title) {
            setString(this.title, title);
        }

        public String getBuildingsCount() {
            return getString( buildings);
        }

        public void setBuildingsCount(String buildings) {
            setString(this.buildings, buildings);
        }

        public List<BuildingDto> getBuildings() {
            return (List<BuildingDto>) getTag(buildings);
        }

        public void setBuildings(List<BuildingDto> buildings) {
            setTag(this.buildings, buildings);
            if (buildings != null) {
                setBuildingsCount("" + buildings.size());
            }
        }

        public String getSchoolTitle() {
            return getString(school);
        }

        public void setSchoolTitle(String school) {
            setString(this.school, school);
        }

        public SchoolDto getSchool() {
            return (SchoolDto) getTag(school);
        }

        public void setSchool(SchoolDto school) {
            setTag(this.school, school);
            if (school != null) {
                setSchoolTitle(school.getTitle());
            }
        }

        @Override
        protected Collection<TextView> getAllFields() {
            return Arrays.asList(id, title, buildings, school);
        }
    }
}
