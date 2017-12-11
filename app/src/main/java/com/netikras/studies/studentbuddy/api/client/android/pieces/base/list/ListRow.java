package com.netikras.studies.studentbuddy.api.client.android.pieces.base.list;

import android.graphics.Rect;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import static com.netikras.tools.common.security.IntegrityUtils.isNullOrEmpty;

public class ListRow<T> {

    private TextView viewText;

    private Map<TextView, Rect> boundsMap;


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


    private Rect getBounds(TextView view) {
        if (isNullOrEmpty(boundsMap)) {
            boundsMap = new HashMap<>();
        }

        Rect bounds = boundsMap.get(view);
        if (bounds == null) {
            bounds = new Rect();
            view.getPaint().getTextBounds(view.getText().toString(), 0, view.getText().length(), bounds);
            boundsMap.put(view, bounds);
        }
        return bounds;
    }

    protected int getWidth(TextView text) {
        return getBounds(text).width();
    }

    protected int getHeight(TextView text) {
        return getBounds(text).height();
    }

}