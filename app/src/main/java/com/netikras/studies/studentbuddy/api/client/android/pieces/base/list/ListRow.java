package com.netikras.studies.studentbuddy.api.client.android.pieces.base.list;

import android.view.View;
import android.widget.TextView;

public class ListRow<T> {

    private TextView viewText;

    public ListRow(View rowView) {
        if (rowView == null) {
            return;
        }
        viewText = rowView.findViewById(android.R.id.text1);
        rowView.setTag(this);
    }

    public void assign(T item) {
        if (item == null) {
            return;
        }
        viewText.setText(item.toString());
    }

    protected int getDefaultListEntryViewId() {
        return android.R.id.text1;
    }

    protected TextView getDefaultListTextView(View rowView) {
        return rowView.findViewById(getDefaultListEntryViewId());
    }
}