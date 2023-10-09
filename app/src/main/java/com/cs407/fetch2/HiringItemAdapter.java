package com.cs407.fetch2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Intended to make a custom ViewHolder to store data specifically for the Hiring.json
 * @author  Jason Fu
 */
public class HiringItemAdapter extends RecyclerView.Adapter<HiringItemAdapter.ViewHolder> {

    // Create a list of hiringItems to display in app
    private List<HiringItem> hiringItems;

    // Constructor that holds the private variable hiringItems
    public HiringItemAdapter(List<HiringItem> hiringItems) {
        this.hiringItems = hiringItems;
    }

    /**
     * This method is used to create a new ViewHolder
     * @param parent parent is tied to the activity_main XML and inflates the layout
     * @param viewType view type of the new View
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Only displays the information of the list at the list
     * position and saves memory this way
     * @param holder ViewHolder reference
     * @param position position of the list being viewed
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HiringItem item = hiringItems.get(position);
        holder.textView.setText(item.getName());
    }

    /**
     * Returns the number of items
     * @return number of items in list
     */
    @Override
    public int getItemCount() {
        return hiringItems.size();
    }

    /**
     * Sets the text in the onBindViewHolder for the specific position in the list
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }
    }
}
