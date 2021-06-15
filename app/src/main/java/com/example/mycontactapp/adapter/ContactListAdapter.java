package com.example.mycontactapp.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycontactapp.R;
import com.example.mycontactapp.datamodel.Contact;

import java.util.ArrayList;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {

    private ArrayList<Contact> contacts;
    private OnItemClickCallback onItemClickCallback;

    // Initialize the dataset of the Adapter.
    public ContactListAdapter(ArrayList<Contact> contacts) {

        this.contacts = contacts;
    }

    public interface OnItemClickCallback {

        void onItemClicked(Contact data);
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {

        this.onItemClickCallback = onItemClickCallback;
    }

    // BEGIN_INCLUDE(recyclerViewSampleViewHolder)
    public class ViewHolder extends RecyclerView.ViewHolder {

        private  TextView textViewFirstName;

        public ViewHolder(@NonNull View view) {
            super(view);

            // Set click listener for ViewHolder's View
            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                }
            });

            textViewFirstName = (TextView) view.findViewById(R.id.first_name);
        }

        public TextView getTextViewFirstName() {

            return textViewFirstName;
        }
    }
    // END_INCLUDE(recyclerViewSampleViewHolder)

    // BEGIN_INCLUDE(recyclerViewOnCreateViewHolder)
    // Create new views for every items(invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        // Create a new view.
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item, viewGroup, false);

        return new ViewHolder(view);
    }
    // END_INCLUDE(recyclerViewOnCreateViewHolder)

    // BEGIN_INCLUDE(recyclerViewOnBindViewHolder)
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        // Get element from your dataset at this position and replace the contents of the view
        // with that element

        viewHolder.getTextViewFirstName().setText(contacts.get(position).getFirst_name());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                onItemClickCallback.onItemClicked(contacts.get(viewHolder.getAdapterPosition()));
            }
        });
    }
    // END_INCLUDE(recyclerViewOnBindViewHolder)

    @Override
    public int getItemCount() {

        return contacts.size();
    }
}
