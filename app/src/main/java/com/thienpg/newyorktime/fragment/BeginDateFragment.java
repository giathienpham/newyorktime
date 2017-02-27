package com.thienpg.newyorktime.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.thienpg.newyorktime.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Thien on 2/27/2017.
 */

public class BeginDateFragment extends DialogFragment  {

    @BindView(R.id.datePicker) DatePicker datePicker;

    public BeginDateFragment() {
    }

    public static BeginDateFragment newInstance(String title) {
        BeginDateFragment frag = new BeginDateFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    public interface BeginDateDialogListener {
        void onFinishPickDialog(String date);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_time_picker, container, false);


        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
                Log.d("Date", "Year=" + year + " Month=" + (month + 1) + " day=" + dayOfMonth);

                String yearString = String.valueOf(year);
                String monthString = String.valueOf(month);
                String dayString = String.valueOf(dayOfMonth);

                if (month < 10){
                    monthString = "0" + String.valueOf(month);
                }
                String date = yearString  + monthString + dayString ;

                BeginDateDialogListener listener = (BeginDateDialogListener) getTargetFragment();
                listener.onFinishPickDialog(date);
                dismiss();
            }
        });

    }



}
