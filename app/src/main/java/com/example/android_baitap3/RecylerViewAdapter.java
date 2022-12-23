package com.example.android_baitap3;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecylerViewAdapter extends RecyclerView.Adapter<RecylerViewAdapter.RecyclerViewHolder>{

    private List<String> mItemList;
    private IItemClickListener mListener;

    public RecylerViewAdapter(List<String> mItemList,IItemClickListener mListener) {
        this.mListener=mListener;
        this.mItemList = mItemList;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food,parent,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        String mItem=mItemList.get(position);
        if(mItem!=null)
        {
            holder.tvFood.setText(mItem);
        }

        holder.mlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClickHandle(mItem);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
       private TextView tvFood;
       private View mlayout;
       public RecyclerViewHolder(@NonNull View itemView) {
           super(itemView);
           tvFood=itemView.findViewById(R.id.txtFoodName);
           mlayout=itemView.findViewById(R.id.mItemLayout);
       }
   }


}
