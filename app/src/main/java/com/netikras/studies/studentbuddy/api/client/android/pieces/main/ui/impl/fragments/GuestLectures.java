package com.netikras.studies.studentbuddy.api.client.android.pieces.main.ui.impl.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.netikras.studies.studentbuddy.api.client.android.R;

/**
 * Created by netikras on 17.12.5.
 */

public class GuestLectures extends LecturesListFragment {

    @Override
    public ListView getListView() {
        return getView().findViewById(R.id.list_main_guest);
    }

    @Override
    public String getName() {
        return "Svečiui";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_guest, container, false);

        return view;

//        return super.onCreateView(inflater, container, savedInstanceState);
    }


    private static LecturesListHandler lastHandler;
    private static Context lastContext;

    @Override
    public void onCreate(Bundle saved) {
        super.onCreate(saved);
        if (handler == null) {
            handler = lastHandler;
        }
        if (context == null) {
            context = lastContext;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        lastContext = context;
        lastHandler = handler;
    }
}
