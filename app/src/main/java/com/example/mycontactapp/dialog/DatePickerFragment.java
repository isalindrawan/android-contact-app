package com.example.mycontactapp.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import com.example.mycontactapp.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    private TextInputEditText editText;

    public DatePickerFragment(TextInputEditText editText) {

        this.editText = editText;
    }

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
        editText.setText("");
        editText.setText(String.valueOf(year));
        editText.setText(editText.getText().toString() + "-" + String.valueOf(month + 1));
        editText.setText(editText.getText().toString() + "-" + String.valueOf(day));
    }
}