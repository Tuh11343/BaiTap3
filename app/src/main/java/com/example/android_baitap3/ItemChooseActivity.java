package com.example.android_baitap3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class ItemChooseActivity extends AppCompatActivity {

    private RecyclerView rclItemChoose;
    private ItemChooseAdapter itemChooseAdapter;
    private ArrayList<Item> itemList;
    private CompletableFuture<ArrayList<Item>> completableFuture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_choose);

        setUpComponents();
    }

    public void setUpComponents()
    {
        rclItemChoose=findViewById(R.id.rclItemChoose);
        itemList=new ArrayList<>();

        itemChooseAdapter=new ItemChooseAdapter(itemList, new IItemClickListener() {
            @Override
            public void onItemClickHandle(String itemName) {
            }
            @Override
            public void onItemChooseClickHandle(Item item) {
                openDialog(Gravity.CENTER,item);
            }
        });
        rclItemChoose.setLayoutManager(new LinearLayoutManager(ItemChooseActivity.this, RecyclerView.VERTICAL, false));
        DividerItemDecoration decoration = new DividerItemDecoration(ItemChooseActivity.this, DividerItemDecoration.VERTICAL);
        decoration.setDrawable(getResources().getDrawable(R.drawable.divider));
        rclItemChoose.addItemDecoration(decoration);
        rclItemChoose.setAdapter(itemChooseAdapter);

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        String url=bundle.getString(MainActivity.URL_KEY);

        ProgressDialog progressDialog=new ProgressDialog(ItemChooseActivity.this);
        progressDialog.setMessage("Loading");
        progressDialog.show();

        try {
            completableFuture = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                completableFuture=new CompletableFuture<>();
                completableFuture.supplyAsync(new ItemLoader(url)).thenAccept(new Consumer<ArrayList<Item>>() {
                    @Override
                    public void accept(ArrayList<Item> items) {
                        itemList.clear();
                        itemList.addAll(items);
                        if (itemList != null && itemList.size() != 0)
                        {
                            progressDialog.dismiss();
                            itemChooseAdapter.notifyDataSetChanged();
                        }
                    }
                }).whenComplete(new BiConsumer<Void, Throwable>() {
                    @Override
                    public void accept(Void unused, Throwable throwable) {
                        rclItemChoose.smoothScrollToPosition(0);
                    }
                });
            }
        } catch (Exception e) {
            Log.e("Loi","Lỗi thread trong itemchoose:"+e.toString());
        }

    }

    public void openDialog(int gravity,Item item)
    {
        final Dialog dialog=new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        Window window=dialog.getWindow();
        if(window==null)
            return;
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes=window.getAttributes();
        windowAttributes.gravity=gravity;
        window.setAttributes(windowAttributes);

        TextView itemName=dialog.findViewById(R.id.itemName);
        TextView itemDescription=dialog.findViewById(R.id.itemDescription);
        ImageView itemImage=dialog.findViewById(R.id.itemImage);

        itemName.setText(item.getItemName());
        itemDescription.setText(item.getItemDescription());
        if(item.getItemIcon()!=null&&item.getItemIcon().length()!=0)
            Picasso.get().load(Uri.parse(item.getItemIcon())).resize(500,500).onlyScaleDown().into(itemImage);

        Button btnMoreInfo=dialog.findViewById(R.id.itemMoreInfo);
        Button btnCancel=dialog.findViewById(R.id.itemClose);

        btnMoreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse(item.getItemUrl()));
                startActivity(intent);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}