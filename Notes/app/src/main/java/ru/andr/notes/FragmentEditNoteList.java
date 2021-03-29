package ru.andr.notes;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class FragmentEditNoteList extends Fragment {

    public static final String ARG_NOTE = "FragmentEditNoteList.note";
    private Note mNote;
    private View.OnClickListener onClickListener;
    private static FragmentManager mFragmentManager;

    public FragmentEditNoteList() {
        // Required empty public constructor
    }

    public static FragmentEditNoteList newInstance(Note note) {
        FragmentEditNoteList fragment = new FragmentEditNoteList();
        mFragmentManager = fragment.getFragmentManager();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, note);
        fragment.setArguments(args);
     //   fragment.setRetainInstance(true);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNote = getArguments().getParcelable(ARG_NOTE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_note_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tvTaskNote = view.findViewById(R.id.item_list_note_task);
        TextInputEditText tietNameNote = view.findViewById(R.id.item_list_note_name);
        TextView tvDateNote = view.findViewById(R.id.item_list_note_date);
        TextView tvTimeNote = view.findViewById(R.id.item_list_note_time);
        TextView tvDescriptionNote = view.findViewById(R.id.item_list_description);
        CheckBox checkBoxCompleted = view.findViewById(R.id.item_list_note_completed);
        SwitchMaterial switchCanceled = view.findViewById(R.id.item_list_note_canceled);

        tvTaskNote.setText(mNote.getTask());
        tietNameNote.setText(mNote.getName());
        tvDateNote.setText(mNote.getDateCreate());
        tvTimeNote.setText(mNote.getTimeCreate());
        tvDescriptionNote.setText(mNote.getDescription());
        checkBoxCompleted.setChecked(mNote.isCompleted());
        switchCanceled.setChecked(mNote.isCanceled());

        Button btnSave = view.findViewById(R.id.btn_save_note);
        AppCompatSpinner spinnerTask = view.findViewById(R.id.spinner_tasks);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "SAVE", Toast.LENGTH_SHORT).show();
            }
        });

        spinnerTask.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tvTaskNote.setText(spinnerTask.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Button btn_set_date = view.findViewById(R.id.set_note_date);
        Button btn_set_time = view.findViewById(R.id.set_note_time);

        btn_set_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(mFragmentManager, "datePicker");
            }
        });

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle bundle){
        super.onSaveInstanceState(bundle);

        bundle.putParcelable(FragmentEditNoteList.ARG_NOTE, mNote);
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
        }
    }

//    public void showTimePickerDialog(View v) {
//        DialogFragment newFragment = new TimePickerFragment();
//        newFragment.show(getSupportFragmentManager(), "timePicker");
//    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
        }
    }

}