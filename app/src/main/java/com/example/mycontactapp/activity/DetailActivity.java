package com.example.mycontactapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.mycontactapp.datamodel.Contact;
import com.example.mycontactapp.dialog.DatePickerFragment;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class DetailActivity extends AppCompatActivity {

    private Intent intent;
    private Contact contact;
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

        // initialize intent
        intent = getIntent();
        this.contact = intent.getParcelableExtra("contact");

        // set Title
        setActionBarTitle(this.contact.getFirst_name() + " " + contact.getLast_name());

        // fill contact data to detail
        showData();

        // hide keyboard temporarily
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.edit:

                enableEdit();
                break;

            case R.id.delete:

                confirmDeletion();
                break;

            case R.id.update:

                updateContact();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void confirmDeletion() {

        new AlertDialog.Builder(this)
                .setTitle("Whoa, hold on dude ... ")
                .setMessage("Do ya really want to delete this contact ?")
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        deleteContact(contact.getContact_id());
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
        .show();
//                .setIcon(R.drawable.ic_dialog_alert)
    }

    private void deleteContact(String id) {

        // show spinner dialog
//        Dialog progress = ProgressDialog.show(getApplicationContext(), "Deleting ... ", "Hold on, we're trying to delete this dude from your life", true);

        // Instantiate request
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://10.10.13.87/contact-api/delete/" + id;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {

                    try {

                        // Checking request result
                        if (response.toString().equals("success")) {

//                            progress.dismiss();

                            Toast.makeText(getApplicationContext(), "Yay, you just getting more lonely", Toast.LENGTH_SHORT).show();

                            finish();

                        } else {

//                            progress.dismiss();
                            Toast.makeText(getApplicationContext(), "So you afraid to be lonely ? What a shame ...", Toast.LENGTH_SHORT).show();
                        }

                    } catch (NullPointerException exception) {

                        exception.printStackTrace();
//                        progress.dismiss();
                        Toast.makeText(getApplicationContext(), "Arghhhh ... ! Something isn't right ...", Toast.LENGTH_SHORT).show();
                    }
                }, error -> {

            Toast.makeText(getApplicationContext(), "Arghhhh ... !", Toast.LENGTH_SHORT).show();
            //progress.dismiss();
        });

        queue.add(stringRequest);
    }

    private void updateContact() {

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

            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            String url = "http://10.10.13.87/contact-api/api/update/";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {

                    // Checking request result
                    if (response.toString().equals("success")) {

                        Toast.makeText(getApplicationContext(), "Yay, friend is updated", Toast.LENGTH_SHORT).show();

                        finish();

                    } else {

                        Toast.makeText(getApplicationContext(), "Failed, too afraid of changes huh ?", Toast.LENGTH_SHORT).show();
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

//                @Override
//                protected Response<String> parseNetworkResponse(NetworkResponse response) {
//
//                    String responseString = "";
//
//                    if (response != null) {
//
//                        responseString = String.valueOf(response.statusCode);
//                    }
//
//                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
//                }
            };

            queue.add(stringRequest);

        } catch (JSONException exception) {

            exception.printStackTrace();
        }
    }

    private void enableEdit() {

        prefix.setEnabled(true);
        first.setEnabled(true);
        mid.setEnabled(true);
        last.setEnabled(true);
        suffix.setEnabled(true);
        nickname.setEnabled(true);
        email.setEnabled(true);
        phone.setEnabled(true);
        mobile.setEnabled(true);
        street.setEnabled(true);
        street_2.setEnabled(true);
        city.setEnabled(true);
        province.setEnabled(true);
        country.setEnabled(true);
        postal.setEnabled(true);
        birthday.setEnabled(true);
        job.setEnabled(true);
        department.setEnabled(true);
        company.setEnabled(true);
        website.setEnabled(true);
        note.setEnabled(true);
    }

    private void init() {

        prefix = findViewById(R.id.prefix);
        prefix.setEnabled(false);

        first = findViewById(R.id.first_name);
        first.setEnabled(false);

        mid = findViewById(R.id.mid_name);
        mid.setEnabled(false);

        last = findViewById(R.id.last_name);
        last.setEnabled(false);

        suffix = findViewById(R.id.suffix);
        suffix.setEnabled(false);

        nickname = findViewById(R.id.nickname);
        nickname.setEnabled(false);

        email = findViewById(R.id.email);
        email.setEnabled(false);

        phone = findViewById(R.id.phone);
        phone.setEnabled(false);

        mobile = findViewById(R.id.mobile);
        mobile.setEnabled(false);

        street = findViewById(R.id.street);
        street.setEnabled(false);

        street_2 = findViewById(R.id.street_2);
        street_2.setEnabled(false);

        city = findViewById(R.id.city);
        city.setEnabled(false);

        province = findViewById(R.id.province);
        province.setEnabled(false);

        country = findViewById(R.id.country);
        country.setEnabled(false);

        postal = findViewById(R.id.postal);
        postal.setEnabled(false);

        birthday = findViewById(R.id.birthday);
        birthday.setEnabled(false);

        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment newFragment = new DatePickerFragment(birthday);
                newFragment.show(getSupportFragmentManager(), "datePicker");

            }
        });

        job = findViewById(R.id.job);
        job.setEnabled(false);

        department = findViewById(R.id.department);
        department.setEnabled(false);

        company = findViewById(R.id.company);
        company.setEnabled(false);

        website = findViewById(R.id.website);
        website.setEnabled(false);

        note = findViewById(R.id.note);
        note.setEnabled(false);

        name_id = findViewById(R.id.name_id);
        contact_id = findViewById(R.id.contact_id);
        address_id = findViewById(R.id.address_id);
        personal_id = findViewById(R.id.personal_id);

    }

    private void showData() {

        name_id.setText(contact.getName_id());
        contact_id.setText(contact.getContact_id());
        address_id.setText(contact.getAddress_id());
        personal_id.setText(contact.getPersonal_id());

        prefix.setText(contact.getPrefix());
        first.setText(contact.getFirst_name());
        mid.setText(contact.getMid_name());
        last.setText(contact.getLast_name());
        suffix.setText(contact.getSuffix());
        nickname.setText(contact.getNickname());
        email.setText(contact.getEmail());
        phone.setText(contact.getPhone());
        mobile.setText(contact.getMobile());
        street.setText(contact.getStreet());
        street_2.setText(contact.getStreet_2());
        city.setText(contact.getCity());
        province.setText(contact.getProvince());
        country.setText(contact.getCountry());
        postal.setText(contact.getPostal());
        birthday.setText(contact.getBirthday());
        job.setText(contact.getJob_title());
        department.setText(contact.getDepartment());
        company.setText(contact.getCompany());
        website.setText(contact.getWebsite());
        note.setText(contact.getNote());
    }

    private void setActionBarTitle(String title) {

        if(getSupportActionBar() != null) {

            getSupportActionBar().setTitle(title);
        }
    }
}