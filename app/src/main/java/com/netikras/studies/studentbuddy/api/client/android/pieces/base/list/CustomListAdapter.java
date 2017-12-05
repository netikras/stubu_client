package com.netikras.studies.studentbuddy.api.client.android.pieces.base.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.netikras.studies.studentbuddy.core.data.api.dto.school.LectureDto;

/**
 * Created by netikras on 17.12.5.
 */

public class CustomListAdapter {

    public void bind(Context context, ListView listView, ListHandler handler) {
        ArrayAdapter<LectureDto> adapter = new ArrayAdapter<LectureDto>(context, handler.getRowLayout(), handler.getListData()) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ListRow listRow;

                if (convertView == null) {
                    convertView = LayoutInflater.from(this.getContext()).inflate(handler.getRowLayout(), parent, false);
                    listRow = handler.getNewRow(convertView);
                } else {
                    listRow = handler.extractExistingRow(convertView);
                }

                if (listRow != null) {
                    listRow.assign(getItem(position));
                }
                return convertView;
            }
        };

        handler.setContext(context);
        handler.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                handler.onRowClick(listView.getItemAtPosition(position));
            }
        });

        listView.setAdapter(adapter);

    }

}
