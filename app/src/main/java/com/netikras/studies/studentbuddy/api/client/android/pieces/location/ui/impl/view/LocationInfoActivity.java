package com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.impl.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.netikras.studies.studentbuddy.api.client.android.R;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.DepInjector;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseViewFields;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.presenter.LocationMvpPresenter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.location.ui.view.LocationMvpView;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest;
import com.netikras.studies.studentbuddy.api.client.android.service.ServiceRequest.Result;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.AddressDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.BuildingDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.BuildingFloorDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.BuildingSectionDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.FloorLayoutDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.location.LectureRoomDto;
import com.netikras.studies.studentbuddy.core.data.api.dto.meta.UserDto;
import com.netikras.tools.common.exception.ErrorBody;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

public class LocationInfoActivity extends BaseActivity implements LocationMvpView {


    @Inject
    LocationMvpPresenter<LocationMvpView> presenter;

    private ViewFields fields;

    private Result<Boolean> triedToFetch = new Result<>(Boolean.FALSE);

    private static LectureRoomDto lastEntry = null;

    @Override
    protected List<Integer> excludeMenuItems() {
        return Arrays.asList(R.id.main_menu_create, R.id.main_menu_delete);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_info);
        setUp();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        lastEntry = collect();
    }

    @Override
    protected void setUp() {
        DepInjector.inject(this);
        onAttach(this);
        presenter.onAttach(this);
        fields = initFields(new ViewFields());
        addMenu();
        if (lastEntry != null) {
            showClean(null, null, null, null, lastEntry);
        }
        executeTask();
    }

    @Override
    public ViewFields getFields() {
        return fields;
    }

    @Override
    public void show(AddressDto addressDto, BuildingDto buildingDto, BuildingSectionDto sectionDto, BuildingFloorDto floorDto, LectureRoomDto roomDto) {
        getFields().reset();

        if (roomDto != null) {
            if (floorDto == null) {
                floorDto = roomDto.getFloor();
            }
        }

        if (floorDto != null) {
            if (sectionDto == null) {
                sectionDto = floorDto.getBuildingSection();
            }
            if (buildingDto == null) {
                buildingDto = floorDto.getBuilding();
            }
        }

        if (buildingDto != null) {
            if (addressDto == null) {
                addressDto = buildingDto.getAddress();
            }
        }

        if (sectionDto != null) {
            if (buildingDto == null) {
                buildingDto = sectionDto.getBuilding();
            }
            if (addressDto == null) {
                addressDto = sectionDto.getAddress();
            }
        }


        if (roomDto != null) {
            getFields().setRoom(roomDto);
        }
        if (floorDto != null) {
            getFields().setFloor(floorDto);
        }
        if (sectionDto != null) {
            getFields().setSection(sectionDto);
        }
        if (buildingDto != null) {
            getFields().setBuilding(buildingDto);
        }
        if (addressDto != null) {
            getFields().setAddress(addressDto);
        }

        if (lastEntry != null) {
            lastEntry = null;
        } else {
            prepare(roomDto);
        }
    }

    @Override
    public void showClean(AddressDto addressDto, BuildingDto buildingDto, BuildingSectionDto sectionDto, BuildingFloorDto floorDto, LectureRoomDto roomDto) {
        getFields().reset();
        show(addressDto, buildingDto, sectionDto, floorDto, roomDto);
    }

    private LectureRoomDto collect() {
        LectureRoomDto dto = getFields().getRoom();
        if (dto == null) {
            dto = new LectureRoomDto();
        }

        if (dto.getFloor() == null) {
            dto.setFloor(getFields().getFloor());
        }

        if (dto.getFloor() != null) {
            BuildingFloorDto floor = dto.getFloor();
            if (floor.getBuildingSection() == null) {
                floor.setBuildingSection(getFields().getSection());
            }
            if (floor.getBuilding() == null) {
                getFields().getBuilding();
            }

            if (floor.getBuilding() != null) {
                BuildingDto building = floor.getBuilding();
                if (building.getAddress() == null) {
                    building.setAddress(getFields().getAddress());
                }
            }

            if (floor.getBuildingSection() != null) {
                BuildingSectionDto section = floor.getBuildingSection();
                if (section.getAddress() == null) {
                    section.setAddress(getFields().getAddress());
                }
            }
        }

        if (lastEntry != null) {
            lastEntry = null;
        }

        return dto;
    }

    @Override
    protected boolean isPartial() {
        return getFields().getRoom() == null
                || getFields().getFloor() == null
                || getFields().getFloor().getLayouts() == null
                || getFields().getSection() == null
                || getFields().getBuilding() == null
                || getFields().getAddress() == null
                ;
    }

    private void prepare(LectureRoomDto entity) {
        if (triedToFetch.getValue()) {
            triedToFetch.setValue(Boolean.FALSE);
            return;
        }
        if (entity == null || isNullOrEmpty(entity.getId())) {
            return;
        }
        if (isPartial()) {
            showLoading();
            triedToFetch.setValue(Boolean.TRUE);
            presenter.getRoom(new ErrorsAwareSubscriber<LectureRoomDto>() {
                @Override
                public void onSuccess(LectureRoomDto response) {
                    runOnUiThread(() -> show(null, null, null, null, response));
//                    showUser(response);
                }
            }, entity.getId());

        }
    }


    @OnClick(R.id.btn_location_address)
    public void showAddress() {
        onError("Not implemented yet");
    }

    @OnClick(R.id.btn_location_building)
    public void showBuilding() {
        onError("Not implemented yet");
    }

    @OnClick(R.id.btn_location_building_section)
    public void showSection() {
        onError("Not implemented yet");
    }

    @OnClick(R.id.btn_location_floor)
    public void showFloor() {
//        presenter.showFloor(this, getFields().getFloor());


        if (getFields().getFloor() != null) {
            showLoading();
            presenter.getFloor(new ErrorsAwareSubscriber<BuildingFloorDto>() {

                @Override
                public void onCacheHit(BuildingFloorDto response) {
                    if (response != null && !isNullOrEmpty(response.getLayouts())) {
                        setFetchRequired(false);
                        onSuccess(response);
                    }
                }

                @Override
                public void onSuccess(BuildingFloorDto response) {
                    startView(LayoutActivity.class, new ViewTask<LayoutActivity>() {
                        @Override
                        public void execute() {
                            if (response != null && !isNullOrEmpty(response.getLayouts())) {
                                getActivity().show(response.getLayouts().get(0));
                            } else {
                                executeOnError(new ErrorBody().setMessage1("Floor layout not found"));
                            }
                        }
                    });
                }
            }, getFields().getFloorId());
        } else {
            onError("Aukštas nenurodytas");
        }

//        presenter.getRoom(null, getFields().getRoomId());
    }

    @OnClick(R.id.btn_location_room)
    public void showRoom() {

        onError("Not implemented yet");
    }

    public class ViewFields extends BaseViewFields {

        @BindView(R.id.txt_edit_location_buildingid)
        EditText buildingId;
        @BindView(R.id.txt_edit_location_building_sectionid)
        EditText sectionId;
        @BindView(R.id.txt_edit_location_floorid)
        EditText floorId;
        @BindView(R.id.txt_edit_location_roomid)
        EditText roomId;
        @BindView(R.id.txt_edit_location_name)
        EditText name;

        @BindView(R.id.btn_location_address)
        Button address;
        @BindView(R.id.btn_location_building)
        Button building;
        @BindView(R.id.btn_location_building_section)
        Button section;
        @BindView(R.id.btn_location_floor)
        Button floor;
        @BindView(R.id.btn_location_room)
        Button room;

        @BindView(R.id.list_location_comments)
        ListView comments;


        @BindView(R.id.txt_lbl_location_buildingid)
        TextView lblBuildingId;
        @BindView(R.id.txt_lbl_location_building_sectionid)
        TextView lblSectionId;
        @BindView(R.id.txt_lbl_location_floorid)
        TextView lblFloorId;
        @BindView(R.id.txt_lbl_location_roomid)
        TextView lblRoomId;

        public String getBuildingId() {
            return getString(buildingId);
        }

        public void setBuildingId(String buildingId) {
            setString(this.buildingId, buildingId);
        }

        public String getSectionId() {
            return getString(sectionId);
        }

        public void setSectionId(String sectionId) {
            setString(this.sectionId, sectionId);
        }

        public String getFloorId() {
            return getString(floorId);
        }

        public void setFloorId(String floorId) {
            setString(this.floorId, floorId);
        }

        public String getRoomId() {
            return getString(roomId);
        }

        public void setRoomId(String roomId) {
            setString(this.roomId, roomId);
        }

        public String getName() {
            return getString(name);
        }

        public void setName(String name) {
            setString(this.name, name);
        }

        public String getAddressText() {
            return getString(address);
        }

        public void setAddressText(String address) {
            setString(this.address, address);
        }

        public String getBuildingName() {
            return getString(building);
        }

        public void setBuildingName(String building) {
            setString(this.building, building);
        }

        public String getSectionName() {
            return getString(section);
        }

        public void setSectionName(String section) {
            setString(this.section, section);
        }

        public String getFloorName() {
            return getString(floor);
        }

        public void setFloorName(String floor) {
            setString(this.floor, floor);
        }

        public String getRoomName() {
            return getString(room);
        }

        public void setRoomName(String room) {
            setString(this.room, room);
        }

        public AddressDto getAddress() {
            return (AddressDto) getTag(address);
        }

        public void setAddress(AddressDto address) {
            setTag(this.address, address);
            if (address != null) {
                StringBuilder builder = new StringBuilder();
                if (!isNullOrEmpty(address.getStreet())) {
                    builder.append(address.getStreet()).append(" g. ");
                }
                if (!isNullOrEmpty(address.getBuildingNo())) {
                    builder.append(address.getBuildingNo()).append(" ");
                }
                if (!isNullOrEmpty(address.getCity())) {
                    builder.append(address.getCity()).append(" ");
                }

                setAddressText(builder.toString());
            }
        }

        public BuildingDto getBuilding() {
            return (BuildingDto) getTag(building);
        }

        public void setBuilding(BuildingDto building) {
            setTag(this.building, building);
            if (building != null) {
                setBuildingId(building.getId());
                setBuildingName(building.getTitle());
            }
        }

        public BuildingSectionDto getSection() {
            return (BuildingSectionDto) getTag(section);
        }

        public void setSection(BuildingSectionDto section) {
            setTag(this.section, section);
            if (section != null) {
                setSectionId(section.getId());
                setSectionName(section.getTitle());
            }
        }

        public BuildingFloorDto getFloor() {
            return (BuildingFloorDto) getTag(floor);
        }

        public void setFloor(BuildingFloorDto floor) {
            setTag(this.floor, floor);
            if (floor != null) {
                setFloorId(floor.getId());
                StringBuilder floorTitle = new StringBuilder();
                if (!isNullOrEmpty(floor.getNumber())) {
                    floorTitle.append(floor.getNumber()).append(" ");
                }
                if (!isNullOrEmpty(floor.getTitle())) {
                    floorTitle.append("(").append(floor.getTitle()).append(")");
                }
                setFloorName(floorTitle.toString());
            }
        }

        public LectureRoomDto getRoom() {
            return (LectureRoomDto) getTag(room);
        }

        public void setRoom(LectureRoomDto room) {
            setTag(this.room, room);
            if (room != null) {
                setRoomId(room.getId());
                setRoomName(room.getNumber() + ", " + room.getTitle());
            }
        }

        public ListView getComments() {
            return comments;
        }

        public void setComments(ListView comments) {
            this.comments = comments;
        }

        @Override
        protected Collection<TextView> getAllFields() {
            return Arrays.asList(name, buildingId, sectionId, floorId, roomId, address, room, floor, building, section);
        }

        @Override
        public void enableEdit(boolean enable) {
            super.enableEdit(enable);

            if (enable) {
                setVisible(lblBuildingId, true);
                setVisible(lblSectionId, true);
                setVisible(lblFloorId, true);
                setVisible(lblRoomId, true);

                setVisible(buildingId, true);
                setVisible(sectionId, true);
                setVisible(floorId, true);
                setVisible(roomId, true);
            } else {
                setVisible(lblBuildingId, false);
                setVisible(lblSectionId, false);
                setVisible(lblFloorId, false);
                setVisible(lblRoomId, false);

                setVisible(buildingId, null);
                setVisible(sectionId, null);
                setVisible(floorId, null);
                setVisible(roomId, null);
            }
        }
    }
}
