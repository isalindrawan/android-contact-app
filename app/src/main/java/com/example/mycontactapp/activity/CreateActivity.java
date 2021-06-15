package com.example.mycontactapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mycontactapp.R;
import com.example.mycontactapp.dialog.DatePickerFragment;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import static com.example.mycontactapp.helper.Helper.isAlphabet;
import static com.example.mycontactapp.helper.Helper.isFilled;

public class CreateActivity extends AppCompatActivity {

    private TextView name_id, contact_id, address_id, personal_id;
    private TextInputEditText prefix, first, mid, last, suffix, nickname, email, phone,
            mobile, street, street_2, city, province, country, postal, birthday, job, department, company,
            website, note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_detail);

        // initialize variables
        init();

        // initialize listener
        initListener();

        // set Title
        setActionBarTitle("Create Contact");

        // hide keyboard temporarily
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.create_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.save: {

                if (isFilled(first)) {

                    if (isAlphabet(prefix, first, mid, last, suffix, nickname,
                            job, department, company, note)) {

                        createContact();
                    }
                }

                break;
            }
            case R.id.cancel:

                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void init() {

        prefix = findViewById(R.id.prefix);
        first = findViewById(R.id.first_name);
        mid = findViewById(R.id.mid_name);
        last = findViewById(R.id.last_name);
        suffix = findViewById(R.id.suffix);
        nickname = findViewById(R.id.nickname);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        mobile = findViewById(R.id.mobile);
        street = findViewById(R.id.street);
        street_2 = findViewById(R.id.street_2);
        city = findViewById(R.id.city);
        province = findViewById(R.id.province);
        country = findViewById(R.id.country);
        postal = findViewById(R.id.postal);
        birthday = findViewById(R.id.birthday);
        job = findViewById(R.id.job);
        department = findViewById(R.id.department);
        company = findViewById(R.id.company);
        website = findViewById(R.id.website);
        note = findViewById(R.id.note);
        name_id = findViewById(R.id.name_id);
        contact_id = findViewById(R.id.contact_id);
        address_id = findViewById(R.id.address_id);
        personal_id = findViewById(R.id.personal_id);

    }

    private void initListener() {

//        prefix.addTextChangedListener(new Validator(prefix) {
//            @Override
//            public void validate(TextInputEditText editText, String text) {
//
//                if (!isAlphabet(editText)) {
//
//                    prefix.requestFocus();
//                    prefix.setError("Only letters allowed");
//                }
//            }
//        });

        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment newFragment = new DatePickerFragment(birthday);
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
    }
    
    private void createContact() {

        JSONObject object = new JSONObject();

        try {

            object.put("address_id", address_id.getText().toString());
            object.put("name_id", name_id.getText().toString());
            object.put("personal_id", personal_id.getText().toString());
            object.put("contact_id", contact_id.getText().toString());
            object.put("street", street.getText().toString());
            object.put("street_2", street_2.getText().toString());
            object.put("city", city.getText().toString());
            object.put("province", province.getText().toString());
            object.put("country", country.getText().toString());
            object.put("postal", postal.getText().toString());
            object.put("prefix", prefix.getText().toString());
            object.put("first_name", first.getText().toString());
            object.put("mid_name", mid.getText().toString());
            object.put("last_name", last.getText().toString());
            object.put("suffix", suffix.getText().toString());
            object.put("nickname", nickname.getText().toString());
            object.put("birthday", birthday.getText().toString());
            object.put("job", job.getText().toString());
            object.put("department", department.getText().toString());
            object.put("company", company.getText().toString());
            object.put("website", website.getText().toString());
            object.put("note", note.getText().toString());
            object.put("email", email.getText().toString());
            object.put("phone", phone.getText().toString());
            object.put("mobile", mobile.getText().toString());

            final String requestBody = object.toString();
            Log.d("JSON", requestBody);

            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            String url = "http://10.10.13.87/contact-api/api/create/";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {

                    // Checking request result
                    if (response.toString().equals("success")) {

                        Toast.makeText(getApplicationContext(), "Yay, new friend added", Toast.LENGTH_SHORT).show();

                        finish();

                    } else {

                        Toast.makeText(getApplicationContext(), "Failed, i think your new friend isn't really your friend", Toast.LENGTH_SHORT).show();
                    }

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(getApplicationContext(), "Arghhhh ... ! Something isn't right ...", Toast.LENGTH_SHORT).show();
                }

            }) {

                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {

                        return requestBody == null ? null : requestBody.getBytes("utf-8");

                    } catch (UnsupportedEncodingException uee) {

                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }
            };

            queue.add(stringRequest);

        } catch (JSONException exception) {

            exception.printStackTrace();
        }
    }

    private void setActionBarTitle(String title) {

        if(getSupportActionBar() != null) {

            getSupportActionBar().setTitle(title);
        }
    }
}