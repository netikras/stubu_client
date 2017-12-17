package com.netikras.studies.studentbuddy.api.client.android.pieces.base;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.netikras.studies.studentbuddy.api.client.android.util.AppConstants.DATE_FORMAT;
import static com.netikras.studies.studentbuddy.api.client.android.util.AppConstants.TIME_FORMAT;
import static com.netikras.studies.studentbuddy.api.client.android.util.CommonUtils.parseDatetime;

/**
 * Created by netikras on 17.10.31.
 */

public abstract class BaseViewFields {

    protected static final String ZERO = "Ø";

    private Context context;

    protected String getString(TextView view) {
        if (view == null) {
            return null;
        }
        return view.getText().toString();
    }

    protected void setString(TextView view, String s) {
        if (view == null) {
            return;
        }
        if (s == null) {
            s = "";
        }
        view.setText(s);
    }

    protected Object getTag(View fromView) {
        if (fromView == null) {
            return null;
        }
        return fromView.getTag();
    }

    protected Object setTag(View toView, Object tag) {
        if (toView == null) {
            return null;
        }

        toView.setTag(tag);
        return tag;
    }

    protected Date getDatetime(String date, String time) {
        return parseDatetime(DATE_FORMAT + "." + TIME_FORMAT, date + "." + time);
    }

    protected abstract Collection<TextView> getAllFields();

    public void clean() {
        getAllFields().forEach(f -> {
            setString(f, "");
            setEditable(f, false);
        });
    }

    public void reset() {
        enableEdit(false);
        clean();
    }

    protected Map<TextView, Integer> getEditableFields() {
        return new HashMap<>();
    }

    public void enableEdit(boolean enable) {
        if (getEditableFields() != null) {
            for (TextView textView : getEditableFields().keySet()) {
                setEditable(textView, enable);
            }
        }
    }

    public void setEditable(TextView view, boolean editable) {

        view.setCursorVisible(editable);
        view.setFocusableInTouchMode(editable);
        view.setFocusable(editable);

//        if (editable) {
//            Integer originalType = InputType.TYPE_CLASS_TEXT;
//            Map<TextView, Integer> types = getEditableFields();
//            if (!isNullOrEmpty(types)) {
//                originalType = ensureValue(types.get(view), InputType.TYPE_CLASS_TEXT);
//            }
//            view.setInputType(originalType);
//        } else {
//            view.setInputType(InputType.TYPE_NULL);
//        }
    }

    public void setVisible(TextView view, Boolean visible) {
        if (visible == null) {
            view.setVisibility(View.GONE);
            return;
        }
        if (visible) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.INVISIBLE);
        }
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void postInit() {

    }

}
