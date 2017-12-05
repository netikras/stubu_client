package com.netikras.studies.studentbuddy.api.client.android.pieces.main.ui.impl.fragments;

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

public class StudentLectures extends LecturesListFragment {




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_student, container, false);

        return view;

//        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public ListView getListView() {
        return getView().findViewById(R.id.list_main_student);
    }

    @Override
    public String getName() {
        return "Studentui";
    }
}
