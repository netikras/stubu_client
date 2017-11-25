package com.netikras.studies.studentbuddy.api.client.android.pieces.base.list;

import android.content.Context;
import android.view.View;

import com.netikras.studies.studentbuddy.api.client.android.R;

import java.util.Arrays;
import java.util.List;

public class ListHandler<T> {

    private Context context;

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

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getListContext() {
        return context;
    }

    public String getToolbarText() {
        return "";
    }

}