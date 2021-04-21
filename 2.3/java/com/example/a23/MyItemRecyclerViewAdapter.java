package com.example.a23;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Contact}.
 * @author Viggo Lagerstedt Ekholm
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private List<Contact> contacts;

    public MyItemRecyclerViewAdapter() { }

    /**
     * Constructor that takes an array of models that should be mapped to our adapter.
     */
    public void addItems(List<Contact> items) {
        contacts = items;
    }

    /**
     * Called when we create the ViewHolder.
     * We return a ViewHolder that has our fragment_contacts XML inflated.
     * @param parent The parent ViewGroup.
     * @param viewType The most recent saved instance.
     * @return ViewHolder
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_contacts, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Called when we want to bind items to the view holder.
     * @param holder the holder that will hold item data.
     * @param position the position of specific item.
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Contact aContact = contacts.get(position);
        String Name = aContact.getName();
        String phoneNr = aContact.getPhoneNumber();

        holder.mItem = aContact;
        holder.firstAndLastName.setText(Name);
        holder.phoneNumber.setText(phoneNr);
    }

    /**
     * Returns the list size.
     * @return int
     */
    @Override
    public int getItemCount() {
        return contacts.size();
    }

    /**
     * This class represent all data that each item should contain.
     * We use this class to contain all of our data of contacts.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView phoneNumber;
        public final TextView firstAndLastName;
        public Contact mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            phoneNumber = view.findViewById(R.id.phone_number);
            firstAndLastName = view.findViewById(R.id.first_and_last_name);
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + firstAndLastName.getText() + "'";
        }
    }
}