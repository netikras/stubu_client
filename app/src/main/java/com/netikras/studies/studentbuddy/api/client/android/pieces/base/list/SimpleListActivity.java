package com.netikras.studies.studentbuddy.api.client.android.pieces.base.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.netikras.studies.studentbuddy.api.client.android.R;
import com.netikras.studies.studentbuddy.api.client.android.conf.di.DepInjector;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseActivity;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.BaseViewFields;
import com.netikras.studies.studentbuddy.api.client.android.util.Exchange;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LectureDto;

import java.util.Arrays;
import java.util.Collection;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

/**
 * Created by netikras on 17.11.23.
 */

public class SimpleListActivity extends BaseActivity {


    ViewFields fields;

    @Inject
    Exchange exchange;

    private ListHandler lastHandler = null;

    @Override
    protected void setUp() {
        DepInjector.inject(this);
        onAttach(this);
        fields = initFields(new ViewFields());
//        presenter.onAttach(this);
        ButterKnife.bind(this, this);
    }


    public ViewFields getFields() {
        return fields;
    }

    @Override
    public void onCreate(Bundle saved) {
        super.onCreate(saved);
        setContentView(R.layout.activity_simple_list);
        setUp();

        ListHandler handler = getHandler();

        if (handler == null || ListHandler.class.equals(handler.getClass())) {
            // TODO throw error -- there's no suitable handler provided
            return;
        }

        handler.setContext(this);

//        setContentView(handler.getActivityLayout());


        getFields().setTitle(handler.getToolbarText());

        ArrayAdapter<LectureDto> adapter = new ArrayAdapter<LectureDto>(this, handler.getRowLayout(), handler.getListData()) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ListRow listRow;

                if (convertView == null) {
                    convertView = LayoutInflater.from(this.getContext()).inflate(handler.getRowLayout(), parent, false);
                    listRow = handler.getNewRow(convertView);
                } else {
                    listRow = handler.extractExistingRow(convertView);
                }
                
                listRow.assign(getItem(position));
                return convertView;
            }
        };
        getFields().getListView().setAdapter(adapter);

        getFields().getListView().setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                handler.onRowClick(getFields().getListView().getItemAtPosition(position));
            }
        });

    }

    private ListHandler getHandler() {
        if (getIntent() == null) {
            return lastHandler;
        }
        lastHandler = null;

        String handlerKey = getIntent().getExtras().getString("handler");
        if (!isNullOrEmpty(handlerKey)) {
            lastHandler = (ListHandler) exchange.get(handlerKey);
        }

        return lastHandler;
    }


    class ViewFields extends BaseViewFields {

        @BindView(R.id.list_simple_list)
        ListView listView;
        @BindView(R.id.toolbar_simple_list)
        Toolbar toolbar;

        public ViewFields() {
//            toolbar = findViewById(R.id.toolbar_simple_list);
//            listView = findViewById(R.id.list_simple_list);
//            System.out.println("List view: " + listView);
        }

        public String getTitle() {
            return toolbar.getTitle() == null ? "" : toolbar.getTitle().toString();
        }

        public void setTitle(String text) {
            toolbar.setTitle(text);
        }

        public ListView getListView() {
            return listView;
        }

        @Override
        protected Collection<TextView> getAllFields() {
            return Arrays.asList();
        }
    }

}
