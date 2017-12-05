package com.netikras.studies.studentbuddy.api.client.android.pieces.base.list;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

import com.netikras.studies.studentbuddy.api.client.android.R;
import com.netikras.studies.studentbuddy.core.data.api.dto.school.LectureDto;

import java.util.Arrays;
import java.util.List;

public class ListHandler<T> {

    private Context context;
    private BaseAdapter adapter;

    public ListRow getNewRow(View convertView) {
        return new ListRow<T>(convertView);
    }

    public ListRow extractExistingRow(View convertView) {
        return (ListRow) convertView.getTag();
    }

    public int getActivityLayout() {
        return R.layout.activity_simple_list;
    }

    public int getRowLayout() {
        return android.R.layout.simple_list_item_1;
    }

    public List<T> getListData() {
        return Arrays.asList();
    }

    public void onRowClick(T item) {

    }

    public void onDataSetChanged() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getListContext() {
        return context;
    }

    public String getToolbarText() {
        return "";
    }

    public void setAdapter(BaseAdapter adapter) {
        this.adapter = adapter;
    }
}