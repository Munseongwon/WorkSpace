package com.ioa.msw.rss;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    Context mContext;
    ArrayList<Item> mNewsList;
    OnItemClickListener mListener;

    public NewsAdapter(Context context, ArrayList<Item> newsList){
        this.mContext = context;
        this.mNewsList = newsList;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = mNewsList.get(position);
        holder.onBind(item);
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleTv;
        TextView authorTv;
        Item mItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTv = itemView.findViewById(R.id.title_tv);
            authorTv = itemView.findViewById(R.id.author_tv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemClick(mItem);
                }
            });
        }

        public void onBind(Item item){
            mItem = item;
            titleTv.setText(item.getTitle());
            authorTv.setText(item.getAuthor());
        }
    }

    interface OnItemClickListener{
        void onItemClick(Item item);
    }
}
