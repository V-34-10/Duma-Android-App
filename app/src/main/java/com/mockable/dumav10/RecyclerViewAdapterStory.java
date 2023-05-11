package com.mockable.dumav10;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapterStory extends RecyclerView.Adapter<RecyclerViewAdapterStory.ViewHolder> {

    private final List<Integer> mViewImage;
    private final List<String> mStory;
    private final LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    RecyclerViewAdapterStory(Context context, List<Integer> images, List<String> stores) {
        this.mInflater = LayoutInflater.from(context);
        mViewImage = images;
        this.mStory = stores;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int image = mViewImage.get(position);
        String stores = mStory.get(position);
        holder.myImageView.setImageResource(image);
        holder.myTextView.setText(stores);
    }

    @Override
    public int getItemCount() {
        return mStory.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView myImageView;
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myImageView = itemView.findViewById(R.id.preview_story);
            myTextView = itemView.findViewById(R.id.storyName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public String getItem(int id) {
        return mStory.get(id);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
