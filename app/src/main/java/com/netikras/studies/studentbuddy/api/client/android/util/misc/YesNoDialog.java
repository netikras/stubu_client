package com.netikras.studies.studentbuddy.api.client.android.util.misc;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by netikras on 17.11.5.
 */

public class YesNoDialog {

    private String yesName = "Yes";
    private String noName = "No";

    private String message = "Are you sure?";

    private OnClick onYes = null;
    private OnClick onNo = null;

    public YesNoDialog text(String text) {
        message = text;
        return this;
    }

    public YesNoDialog yes(String name, OnClick onClick) {
        yesName = name;
        onYes = onClick;
        return this;
    }

    public YesNoDialog no(String name, OnClick onClick) {
        noName = name;
        onNo = onClick;
        return this;
    }

    public void show(Context fromContext) {
        if (onYes == null) {
            onYes = new OnClick();
        }
        if (onNo == null) {
            onNo = new OnClick();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(fromContext);
        builder
                .setMessage(message)
                .setPositiveButton(yesName, onYes)
                .setNegativeButton(noName, onNo)
                .show();
    }

    public static class OnClick implements DialogInterface.OnClickListener {
        protected void onClick(DialogInterface dialog) {
            dialog.cancel();
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            onClick(dialog);
        }
    }
}
