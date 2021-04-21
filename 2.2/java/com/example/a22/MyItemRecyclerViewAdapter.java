package com.example.a22;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PrimeModel}.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {
    private final List<PrimeModel> primeModels;

    /**
     * Constructor that takes an array of models that should be mapped to our adapter.
     */
    public MyItemRecyclerViewAdapter(List<PrimeModel> items) {
        primeModels = items;
    }

    /**
     * Called when we create the ViewHolder.
     * We return a ViewHolder that has our fragment_item XML inflated.
     * @Override We override to add specific behavior.
     * @param parent The parent ViewGroup.
     * @param viewType The most recent saved instance.
     * @return ViewHolder
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Called when we want to bind items to the view holder.
     * @Override We override to add specific behavior.
     * @param holder the holder that will hold item data.
     * @param position the position of specific item.
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        PrimeModel item = primeModels.get(position);
        String prime = String.valueOf(item.getPrime());
        String date = item.getDate();

        holder.mItem = item;
        holder.mPrime.setText(prime);
        holder.mDate.setText(date);
    }

    /**
     * Returns the list size.
     * @Override We override to add specific behavior.
     * @return int
     */
    @Override
    public int getItemCount() {
        return primeModels.size();
    }

    /**
     * This class represent all data that each item should contain.
     * We use this class to contain all of our data of primes and date!
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mPrime;
        public final TextView mDate;
        public PrimeModel mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mPrime = view.findViewById(R.id.prime);
            mDate = view.findViewById(R.id.date);
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + mPrime.getText() + "'" + mDate;
        }
    }
}