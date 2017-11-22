package com.netikras.studies.studentbuddy.api.client.android.pieces.base;

import android.text.InputType;
import android.view.View;
import android.widget.TextView;

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
            if (enable) {
                for (TextView textView : getEditableFields()) {
                    textView.setCursorVisible(true);
                    textView.setFocusableInTouchMode(true);
                    textView.setInputType(InputType.TYPE_CLASS_TEXT);
//                textView.requestFocus(); //to trigger the soft input
                }
            } else {
                for (TextView textView : getEditableFields()) {
                    textView.setCursorVisible(false);
                    textView.setFocusableInTouchMode(false);
                    textView.setInputType(InputType.TYPE_NULL);
                }
            }
        }
    }

    public void hideField(TextView view, boolean hide) {
        if (hide) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }

}
