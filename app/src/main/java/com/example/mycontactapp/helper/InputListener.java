package com.example.mycontactapp.helper;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.example.mycontactapp.R;
import com.google.android.material.textfield.TextInputEditText;

public abstract class InputListener implements TextWatcher {
    private final TextInputEditText editText;

    public InputListener(TextInputEditText editText) {
        this.editText = editText;
    }

    public abstract void validate(TextInputEditText editText, String text);

    @Override
    final public void afterTextChanged(Editable edit) {
        String text = editText.getText().toString();
        validate(editText, text);
    }

    @Override
    final public void beforeTextChanged(CharSequence s, int start, int count, int after) { /* Don't care */ }

    @Override
    final public void onTextChanged(CharSequence s, int start, int before, int count) { /* Don't care */ }
}