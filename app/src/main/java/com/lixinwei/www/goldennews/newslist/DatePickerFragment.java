package com.lixinwei.www.goldennews.newslist;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import com.lixinwei.www.goldennews.DateNewsList.DateNewsListActivity;
import com.lixinwei.www.goldennews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by welding on 2017/7/7.
 */

public class DatePickerFragment extends DialogFragment {

    @BindView(R.id.dialog_date_picker)
    DatePicker mDatePicker;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_date_picker, null);

        ButterKnife.bind(this, v);

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                //.setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String year = String.valueOf(mDatePicker.getYear());
                                String month =String.valueOf(mDatePicker.getMonth());
                                String day =String.valueOf(mDatePicker.getDayOfMonth());
                                if(month.length() < 2) {
                                    month = "0" + month;
                                }
                                if(day.length() < 2) {
                                    day = "0" + day;
                                }

                                String date = year + month + day;

                                Intent intent = DateNewsListActivity.newIntent(getActivity(), date);
                                startActivity(intent);
                            }
                        })
                .create();
    }
}
