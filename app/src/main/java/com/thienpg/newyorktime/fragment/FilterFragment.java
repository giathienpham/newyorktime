package com.thienpg.newyorktime.fragment;

import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.thienpg.newyorktime.R;
import com.thienpg.newyorktime.model.Filter;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Thien on 2/27/2017.
 */

public class FilterFragment extends DialogFragment  implements BeginDateFragment.BeginDateDialogListener {

    @BindView(R.id.begin_date) TextView beginDate;
    @BindView(R.id.spnSort) Spinner spinnerSort;
    @BindView(R.id.cbArts) CheckBox cbArts;
    @BindView(R.id.cbFashion) CheckBox cbFashion;
    @BindView(R.id.cbSports) CheckBox cbSports;
    @BindView(R.id.btnSaveFilter) Button btnSave;


    private EditText mEditText;
    Filter filter = new Filter();


    public FilterFragment() {
    }



//    @Override
//    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//            return true;
//    }

    // 1. Defines the listener interface with a method passing back data result.
    public interface FilterFragmentListener {
        void onFinishFilterFragment(Filter filter);
    }


    public static FilterFragment newInstance(String title) {
        FilterFragment frag = new FilterFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter, container, false);
        ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);


        beginDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                if (spinnerSort.getSelectedItemPosition() == 0){
                    filter.setSort("Newest");
                }else {
                    filter.setSort("Oldest");
                }


                String desk = "news_desk:(";
                if (cbArts.isChecked()){
                    desk = desk.concat("'Arts' ");
                }
                if (cbFashion.isChecked()){
                    desk = desk.concat("'Fashion & Style' ");
                }
                if (cbSports.isChecked()){
                    desk = desk.concat("'Sports' ");
                }
                desk = desk.concat(")");

                filter.setDesk(desk);




                // Return input text back to activity through the implemented listener
                FilterFragmentListener listener = (FilterFragmentListener) getActivity();
                listener.onFinishFilterFragment(filter);
                // Close the dialog and return back to the parent activity
                dismiss();
            }
        });
    }


    private void showDatePickerDialog() {
        FragmentManager fm = getFragmentManager();
        BeginDateFragment filterFragment = BeginDateFragment.newInstance("Some Title");
        filterFragment.setTargetFragment(FilterFragment.this, 300);

        filterFragment.show(fm, "dialog_time_picker");
    }

    @Override
    public void onFinishPickDialog(String date) {
        filter.setBeginDate(date);
        beginDate.setText(date);
        Toast.makeText(getActivity(), date, Toast.LENGTH_SHORT).show();
    }

}
