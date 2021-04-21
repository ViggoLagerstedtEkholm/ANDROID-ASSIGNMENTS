package com.example.a61;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * This class holds our adapter and will handle all of the displaying of the {@HistoryModel} items.
 */
public class HistoryLogAdapter extends RecyclerView.Adapter<HistoryLogAdapter.MyViewHolder> {
    private final Context context;
    private final ArrayList<HistoryModel> callingHistory;

    /**
     * This is our constructor and will take the parameter values and assign these to the class list and context.
     * @param context the context of the activity.
     * @param callingHistory The array of HistoryModel that we want to display in our adapter.
     */
    public HistoryLogAdapter(Context context, ArrayList<HistoryModel> callingHistory) {
        Log.d("CREATED_ADAPTER", "...");

        this.context = context;
        this.callingHistory = callingHistory;
    }

    /**
     * This method will return a object of MyViewHolder, this class is our holder of values we want to display onto the screen.
     * The activity_call_history.XML will be our view that should be inflated onto our screen.
     * @param parent our ViewGroup parent
     * @param viewType the viewType
     * @return HistoryLogAdapter.MyViewHolder
     */
    @NonNull
    @Override
    public HistoryLogAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("CALL_HISTORY_INFLATED", "...");
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_call_history, parent, false));
    }

    /**
     * This method will enable us to put all of our values from our HistoryModel into the holder class fields.
     * For every model we will add a OnClickListener to the call button, we can therefore click call and the app will start the ACTION_CALL intent.
     * @param holder our instance of the MyViewHolder class, this will hold our values to be displayed.
     * @param position the index of the item displayed.
     */
    @Override
    public void onBindViewHolder(@NonNull HistoryLogAdapter.MyViewHolder holder, int position) {
        Log.d("BINDING", "...");
        HistoryModel currentHistoryModel = callingHistory.get(position);
        holder.textViewDate.setText(currentHistoryModel.getDate());
        holder.textViewTime.setText(currentHistoryModel.getTime());
        holder.textViewNr.setText(currentHistoryModel.getNumber());
        holder.type.setText(currentHistoryModel.getType());
        holder.callButton.setOnClickListener(v -> {
            Log.d("INDEX:" ,"Clicked index: " + position);
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + currentHistoryModel.getNumber()));
            context.startActivity(intent);
        });
    }

    /**
     * This method returns the amount of items in our call history.
     * @return int
     */
    @Override
    public int getItemCount() {
        return callingHistory == null ? 0 : callingHistory.size();
    }

    /**
     * This class will represent our holder of values passed from our custom HistoryModel.
     */
    public static class  MyViewHolder extends RecyclerView.ViewHolder{
        private final TextView textViewNr;
        private final TextView textViewDate;
        private final TextView textViewTime;
        private final TextView type;
        private final Button callButton;

        /**
         * We fetch all the UI components from the view.
         * @param itemView a view object representing 1 of the items in the adapter.
         */
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewTime = itemView.findViewById(R.id.textViewTime);
            textViewNr = itemView.findViewById(R.id.textViewNr);
            callButton = itemView.findViewById(R.id.callBtn);
            type = itemView.findViewById(R.id.valueType);
        }
    }
}
