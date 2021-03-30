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

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class FragmentEditNoteList extends Fragment {

    public static final String ARG_NOTE_EDIT = "FragmentEditNoteList.note";
    private Note mNote;
    private TextView mTvDateNote;
    private TextView mTvTimeNote;
    private boolean FIRST_LOAD = true;

    public FragmentEditNoteList() {
        // Required empty public constructor
    }

    public static FragmentEditNoteList newInstance(Note note) {
        FragmentEditNoteList fragment = new FragmentEditNoteList();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE_EDIT, note);
        fragment.setArguments(args);
     //   fragment.setRetainInstance(true);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNote = getArguments().getParcelable(ARG_NOTE_EDIT);
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
        mTvDateNote = view.findViewById(R.id.item_list_note_date);
        mTvTimeNote = view.findViewById(R.id.item_list_note_time);
        TextView tvDescriptionNote = view.findViewById(R.id.item_list_description);
        CheckBox checkBoxCompleted = view.findViewById(R.id.item_list_note_completed);
        SwitchMaterial switchCanceled = view.findViewById(R.id.item_list_note_canceled);

        tvTaskNote.setText(mNote.getTask());
        tietNameNote.setText(mNote.getName());
        mTvDateNote.setText(mNote.getDateCreate());
        mTvTimeNote.setText(mNote.getTimeCreate());
        tvDescriptionNote.setText(mNote.getDescription());
        checkBoxCompleted.setChecked(mNote.isCompleted());
        switchCanceled.setChecked(mNote.isCanceled());

        Button btnSave = view.findViewById(R.id.btn_save_note);
        AppCompatSpinner spinnerTask = view.findViewById(R.id.spinner_tasks);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "SAVE", Toast.LENGTH_SHORT).show();
                mNote.setTask(tvTaskNote.getText().toString());
                mNote.setName(tietNameNote.getText().toString());
                mNote.setDateCreate(mTvDateNote.getText().toString());
                mNote.setTimeCreate(mTvTimeNote.getText().toString());
                mNote.setIsCanceled(switchCanceled.isChecked());
                mNote.setIsCompleted(checkBoxCompleted.isChecked());
                mNote.setDescription(tvDescriptionNote.getText().toString());

                getFragmentManager().popBackStack();
            }
        });

        spinnerTask.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(FIRST_LOAD){
                    FIRST_LOAD = false;
                    return;
                }
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
                DialogFragment newFragment = new DatePickerFragment(mTvDateNote);
                newFragment.show(requireFragmentManager(), "datePicker");
            }
        });

        btn_set_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment(mTvTimeNote);
                newFragment.show(requireFragmentManager(), "timePicker");
            }
        });

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle bundle){
        super.onSaveInstanceState(bundle);
        bundle.putParcelable(FragmentEditNoteList.ARG_NOTE_EDIT, mNote);
    }



    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        private TextView mTvTimeNoteTimePickerFragment;

        public TimePickerFragment(TextView tvTimeNote){
            mTvTimeNoteTimePickerFragment = tvTimeNote;
        }
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
            mTvTimeNoteTimePickerFragment.setText(hourOfDay + ":" + minute);
        }
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        private TextView mTvDateNoteDatePickerFragment;

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

        public DatePickerFragment(TextView tvDateNote){
            mTvDateNoteDatePickerFragment = tvDateNote;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            mTvDateNoteDatePickerFragment.setText(day + "." + month + "." + year);
        }
    }

}