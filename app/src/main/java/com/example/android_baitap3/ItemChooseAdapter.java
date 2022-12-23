package com.example.android_baitap3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemChooseAdapter extends RecyclerView.Adapter<ItemChooseAdapter.ItemChooseViewHolder> {

    private List<Item> mItemList;
    private IItemClickListener mListener;

    public ItemChooseAdapter(List<Item> mItemList, IItemClickListener mListener) {
        this.mItemList = mItemList;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ItemChooseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent,false);
        return new ItemChooseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemChooseViewHolder holder, int position) {
        Item item=mItemList.get(position);
        holder.tv1.setText(item.getItemName());
        holder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemChooseClickHandle(item);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    public class ItemChooseViewHolder extends RecyclerView.ViewHolder{

        private TextView tv1;
        private View mLayout;
        public ItemChooseViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1=itemView.findViewById(R.id.txtFoodName);
            mLayout=itemView.findViewById(R.id.mItemLayout);
        }
    }
}
