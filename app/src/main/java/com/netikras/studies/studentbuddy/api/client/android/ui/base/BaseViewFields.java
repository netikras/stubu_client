package com.netikras.studies.studentbuddy.api.client.android.ui.base;

import android.text.InputType;
import android.view.View;
import android.widget.TextView;

import java.util.Collection;

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

    protected abstract Collection<TextView> getAllFields();

    public void clean() {
        getAllFields().forEach(f -> setString(f, ""));
    }

    protected Collection<TextView> getEditableFields() {
        return getAllFields();
    }

    public void enableEdit(boolean enable) {
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

    public void hideField(TextView view, boolean hide) {
        if (hide) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }

}
