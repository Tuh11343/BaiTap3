package com.example.android_baitap3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecylerViewAdapter recylerViewAdapter;
    private List<String> mDefaultList;

    public static String URL_KEY="URL_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDefaultList=new ArrayList<>();
        mDefaultList.add("Pet Food Ingredients");
        mDefaultList.add("Pet Food Palatability");
        mDefaultList.add("Natural / Organic Pet Food");

        recyclerView=findViewById(R.id.lvFoodType);
        recylerViewAdapter=new RecylerViewAdapter(mDefaultList, new IItemClickListener() {
            @Override
            public void onItemClickHandle(String itemName) {
                if(!networkChecker())
                {
                    Toast.makeText(MainActivity.this, "Không có kết nối mạng", Toast.LENGTH_SHORT).show();
                    return;
                }
                String url=getItemClickURL(itemName);
                Intent intent=new Intent(MainActivity.this,ItemChooseActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString(URL_KEY,url);
                intent.putExtras(bundle);
                startActivity(intent);
            }
            @Override
            public void onItemChooseClickHandle(Item item) {

            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        DividerItemDecoration decoration= new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        decoration.setDrawable(getResources().getDrawable(R.drawable.divider));
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(recylerViewAdapter);

    }

    public String getItemClickURL(String itemName)
    {
        switch(itemName)
        {
            case "Pet Food Ingredients":
                try {
                    return "https://www.petfoodindustry.com/rss/topic/212-pet-food-ingredients";
                } catch (Exception e) {
                    Log.e("loi",e.toString());
                }
                break;
            case "Pet Food Palatability":
                try {
                    return "https://www.petfoodindustry.com/rss/topic/251-pet-food-palatability";
                } catch (Exception e) {
                    Log.e("loi",e.toString());
                }
                break;
            case "Natural / Organic Pet Food":
                try {
                    return "https://www.petfoodindustry.com/rss/topic/226-natural-organic-pet-food";
                } catch (Exception e) {
                    Log.e("loi",e.toString());
                }
                break;
        }
        return null;
    }

    public boolean networkChecker()
    {
        ConnectivityManager cm= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if(cm==null)
            return false;
        NetworkInfo networkInfo=cm.getActiveNetworkInfo();
        return networkInfo!=null&&networkInfo.isConnected();
    }



}