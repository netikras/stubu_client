package com.netikras.studies.studentbuddy.api.client.android.pieces.base;

import android.content.Intent;
import android.text.InputType;
import android.view.View;
import android.widget.TextView;

import com.netikras.studies.studentbuddy.api.client.android.R;

import java.util.Collection;
import java.util.Date;

import static com.netikras.studies.studentbuddy.api.client.android.util.AppConstants.DATE_FORMAT;
import static com.netikras.studies.studentbuddy.api.client.android.util.AppConstants.TIME_FORMAT;
import static com.netikras.studies.studentbuddy.api.client.android.util.CommonUtils.parseDatetime;

/**
 * Created by netikras on 17.10.31.
 */

public abstract class BaseViewFields {

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
        getAllFields().forEach(f -> setString(f, ""));
    }

    public void reset() {
        enableEdit(false);
        clean();
    }

    protected Collection<TextView> getEditableFields() {
        return getAllFields();
    }

    public void enableEdit(boolean enable) {
        if (getEditableFields() != null) {
            for (TextView textView : getEditableFields()) {
                setEditable(textView, enable);
            }
        }
    }

    public void setEditable(TextView view, boolean editable) {
        if (editable) {
            view.setCursorVisible(true);
            view.setFocusableInTouchMode(true);

            Integer oldType = (Integer) view.getTag(R.id.TAG_ONLINE_ID);
            view.setInputType(oldType == null ? InputType.TYPE_CLASS_TEXT : oldType);
        } else {
            view.setCursorVisible(false);
            view.setFocusableInTouchMode(false);

            view.setTag(R.id.TAG_ONLINE_ID, view.getInputType());
            view.setInputType(InputType.TYPE_NULL);
        }
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

}
