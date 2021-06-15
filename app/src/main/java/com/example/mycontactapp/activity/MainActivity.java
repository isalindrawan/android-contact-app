package com.example.mycontactapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mycontactapp.R;
import com.example.mycontactapp.adapter.ContactListAdapter;
import com.example.mycontactapp.datamodel.Contact;
import com.example.mycontactapp.helper.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Contact> contacts = new ArrayList<>();
    private ProgressDialog dialog;
    private Toast toast;
    private ContactListAdapter contactListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize Recycler View
        recyclerView = findViewById(R.id.recycle_view_contact);
        recyclerView.setHasFixedSize(true);

        // checking for permission
        if (!Constant.checkPermission(this, MainActivity.this, Manifest.permission.INTERNET))
            return;

        // showing Recycler View
        showRecyclerList();

    }

    @Override
    protected void onStart() {
        super.onStart();

        // clear current data
        contacts.clear();

        // populating data
        getUserData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // initiate option menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contact_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        // handle selection on option menu
        switch (item.getItemId()) {

            case R.id.new_contact: {

                Intent intent = new Intent(getBaseContext(), CreateActivity.class);
                startActivity(intent);

                break;
            }
            case R.id.exit_app:

                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    //initiate recycler list component
    private void showRecyclerList() {

        // set linear layout to recycler view layout
        // instantiate contact adapter to handle contacts data
        // add on click action to all contact adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        contactListAdapter = new ContactListAdapter(contacts);
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(contactListAdapter);

        contactListAdapter.setOnItemClickCallback(new ContactListAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Contact data) {

                showDetail(data);
            }
        });
    }

    private void showDetail(Contact contact) {

        //Toast.makeText(this, contact.getName(), Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getBaseContext(), DetailActivity.class);
        intent.putExtra("contact", contact);
        startActivity(intent);
    }

    private void getUserData() {

        // show spinner dialog
        dialog = ProgressDialog.show(this, "Requesting data", "Please wait, don't lose hope ...", true);

        // Instantiate request
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://10.10.13.87/contact-api/";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {

                    try {

                        // Casting response data to JSONObject
                        JSONArray object = new JSONArray(response);

                        // Checking request result
                        if (object.length() > 0) {

                            // Casting JSONObject to Contact object model
                            toContactArray(object);

                            dialog.dismiss();

                            // Tell the adapter that data has changed
                            contactListAdapter.notifyDataSetChanged();

                            showToast("Here you go ...");

                        } else {

                            dialog.dismiss();

                            showToast("Seems like we can't find any data ... ");

                        }

                        // dialog.dismiss();

                    } catch (JSONException exception) {

                        exception.printStackTrace();
                        dialog.dismiss();

                        showToast("Arghhhh ... ! Something isn't right ...");
                    }
                }, error -> {

            showToast("Arghhhh ... ! Something isn't right ...");
            dialog.dismiss();
        });

        queue.add(stringRequest);
    }

    private void toContactArray(JSONArray object) {

        char alphabet = 'A';

        try {

            contacts.add(new Contact(String.valueOf(alphabet)));

            for(int index = 0; index < object.length(); index++) {

                JSONObject temp = object.getJSONObject(index);

                if (temp.getString("first_name").length() != 0) {
                    if (alphabet != temp.getString("first_name").toUpperCase().charAt(0)) {

                        alphabet = temp.getString("first_name").toUpperCase().charAt(0);

                        contacts.add(new Contact(String.valueOf(alphabet)));
                    }
                }

                contacts.add(new Contact(temp.getString("contact_id"),
                        temp.getString("address_id"),
                        temp.getString("name_id"),
                        temp.getString("personal_id"),
                        temp.getString("email"),
                        temp.getString("phone"),
                        temp.getString("mobile"),
                        temp.getString("prefix"),
                        temp.getString("first_name"),
                        temp.getString("mid_name"),
                        temp.getString("last_name"),
                        temp.getString("suffix"),
                        temp.getString("nickname"),
                        temp.getString("street"),
                        temp.getString("street_2"),
                        temp.getString("city"),
                        temp.getString("province"),
                        temp.getString("country"),
                        temp.getString("postal"),
                        temp.getString("birthday"),
                        temp.getString("job_title"),
                        temp.getString("department"),
                        temp.getString("company"),
                        temp.getString("website"),
                        temp.getString("note")
                ));
            }

        } catch (JSONException event) {

            // TODO Auto-generated catch block
            event.printStackTrace();
            Log.d("ERROR", event.getMessage());
        }
    }

    private void showToast(String message) {

        toast = Toast.makeText(this, message,Toast.LENGTH_SHORT);
        toast.show();
    }
}