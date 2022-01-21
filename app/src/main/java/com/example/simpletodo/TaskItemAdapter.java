package com.example.simpletodo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * A bridge that tells the RecyclerView how to display the data we give it
 */
public class TaskItemAdapter extends
        RecyclerView.Adapter<TaskItemAdapter.ViewHolder> {

    List<String> listOfItems = new ArrayList<>();
    OnLongClickListener alongClickListener;

    // constructor
    public TaskItemAdapter(List<String> listOftasks, OnLongClickListener lcl) {
        listOfItems = listOftasks;
        alongClickListener = lcl;
    }

    interface OnLongClickListener {
        void onItemLongClicked(int adapterPosition);
    }

    @NonNull

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // Get the data model based on position
        String item = listOfItems.get(position);

        holder.textView.setText(item); // a bit iffy
    }

    @Override
    public int getItemCount() {

        return listOfItems.size();
    }


    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Store references to elements in our layout view

        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView textView;
        public Button messageButton;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            textView = (TextView) itemView.findViewById(android.R.id.text1);
            messageButton = (Button) itemView.findViewById(R.id.button);

            itemView.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View v) {
                    alongClickListener.onItemLongClicked(getAdapterPosition());
                    return true;
                }
            });
        }
    }
}
