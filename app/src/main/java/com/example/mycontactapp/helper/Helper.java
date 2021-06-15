package com.example.mycontactapp.helper;

import android.view.View;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;

public class Helper {

    public static boolean isFilled(TextInputEditText... editTexts) {

        boolean result = true;

        for (EditText text : editTexts) {

            if (text.getText().toString().isEmpty()) {

//                final View focusView = text;
                text.setError("required, just fill the blank");
//                focusView.requestFocus();
                result = false;
            }
        }

        return result;
    }

    public static boolean isAlphabet(TextInputEditText... editTexts) {

        boolean result = true;

        for (EditText text : editTexts) {

            if (!text.getText().toString().matches("[a-zA-Z]+") && !text.getText().toString().isEmpty()) {

                final View focusView = text;
                text.setError("Letters only please");
                focusView.requestFocus();
                result = false;
            }
        }

        return result;
    }

    public static String nullifier(TextInputEditText editText) {

        String text = editText.toString();
        if (text.equals(""))
            return "null";

        return text;
    }
}
