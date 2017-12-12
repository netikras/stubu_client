package com.netikras.studies.studentbuddy.api.client.android.pieces.main.ui.impl.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.netikras.studies.studentbuddy.api.client.android.pieces.base.list.CustomListAdapter;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.list.ListHandler;
import com.netikras.studies.studentbuddy.api.client.android.pieces.base.list.ListRow;
import com.netikras.studies.studentbuddy.api.client.android.util.CommonUtils;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LectureDto;

import java.util.List;

import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

/**
 * Created by netikras on 17.12.5.
 */

public abstract class LecturesListFragment extends Fragment {

    protected LecturesListHandler handler = null;
    protected Context context;

    public abstract ListView getListView();

    public abstract String getName();

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public Context getContext() {
        if (context != null) {
            return context;
        }
        return super.getContext();
    }

    public void setListHandler(LecturesListHandler listHandler) {
        this.handler = listHandler;
    }

    @Override
    public void onCreate(Bundle saved) {
        super.onCreate(saved);
    }

    @Override
    public void onStart() {
        super.onStart();
        CustomListAdapter listAdapter = new CustomListAdapter();
        listAdapter.bind(getContext(), getListView(), handler);
    }



    public abstract static class LecturesListHandler extends ListHandler<LectureDto> {
        @Override
        public ListRow getNewRow(View convertView) {
            return new LecturesListRow(convertView);
        }

        @Override
        public abstract List<LectureDto> getListData();

        @Override
        public abstract void onRowClick(LectureDto item);

        class LecturesListRow extends ListRow<LectureDto> {

            TextView text;

            public LecturesListRow(View rowView) {
                super(null);
                text = getDefaultListTextView(rowView);
                rowView.setTag(this);
            }

            @Override
            public void assign(LectureDto item) {
                if (item == null) {
                    return;
                }

                StringBuilder title = new StringBuilder();
                title.append("(").append(CommonUtils.datetimeToTime(item.getStartsOn())).append(") ");
                title.append(item.getDiscipline().getTitle()).append("");
                if (!isNullOrEmpty(item.getTests())) {
                    title.append(", [").append("T:").append(item.getTests().size()).append("]");
                }
                if (!isNullOrEmpty(item.getAssignments())) {
                    title.append(", [").append("A:").append(item.getAssignments().size()).append("]");
                }

                text.setText(title.toString());
            }
        }
    }

}
