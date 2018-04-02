package com.linkedin.android.learning_android_accessibility.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.linkedin.android.learning_android_accessibility.R;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<String> mData;
    private ItemClickListener mClickListener;

    public ListAdapter(List<String> data) {
        mData = data;
    }

    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.simple_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.mTextView.setText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public String getItem(int position) {
        return mData.get(position);
    }

    public void setClickListener(ItemClickListener itemClickListeners) {
        this.mClickListener = itemClickListeners;
    }

    // parent activity will implement these methods for click events
    public interface ItemClickListener {
        void onItemClicked(View view, int position);
    }

    /* ----------------------------------------------------------------
     * View holder
     * ---------------------------------------------------------------- */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mTextView;

        ViewHolder(View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.simple_list_item_textview);
            mTextView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener == null) return;
            int position = getAdapterPosition();
            mClickListener.onItemClicked(view, position);
        }
    }
}
