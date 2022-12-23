package com.example.android_baitap3;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

@RequiresApi(api = Build.VERSION_CODES.N)
public class ItemLoader implements Supplier<ArrayList<Item>> {

    private String URL;

    public ItemLoader(String URL)
    {
        this.URL=URL;
    }

    public InputStream getInputStream(URL url)
    {
        try{
            return url.openConnection().getInputStream();
        }catch(Exception er)
        {
            Log.e("Loi",er.toString());
        }
        return null;
    }

    @Override
    public ArrayList<Item> get() {
        try{
            ArrayList<Item> itemList=new ArrayList<>();
            Item item=new Item();
            URL url=new URL(URL);
            XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(false);
            XmlPullParser xpp=factory.newPullParser();
            xpp.setInput(getInputStream(url),"UTF_8");
            boolean insideItem=false;
            int eventType=xpp.getEventType();
            while(eventType!= XmlPullParser.END_DOCUMENT)
            {
                if(eventType==XmlPullParser.START_TAG)
                {
                    if(xpp.getName().equalsIgnoreCase("item"))
                    {
                        insideItem=true;
                        item=new Item();
                    }
                    else if(xpp.getName().equalsIgnoreCase("title"))
                    {
                        if(insideItem)
                            item.setItemName(xpp.nextText());
                    }
                    else if(xpp.getName().equalsIgnoreCase("description"))
                    {
                        if(insideItem)
                        {
                            String result = xpp.nextText().replaceAll("<[^>]*>", "");
                            item.setItemDescription(result);
                        }
                    }
                    else if(xpp.getName().equalsIgnoreCase("link"))
                    {
                        if(insideItem)
                            item.setItemUrl(xpp.nextText());
                    }
                    else if(xpp.getName().equalsIgnoreCase("media:content"))
                    {
                        if(insideItem)
                            item.setItemIcon(xpp.getAttributeValue(null,"url"));
                    }
                }
                else if(eventType==XmlPullParser.END_TAG&&xpp.getName().equalsIgnoreCase("item"))
                {
                    insideItem=false;
                    itemList.add(item);
                }
                eventType=xpp.next();
            }
            return itemList;
        }catch (Exception er)
        {
            Log.e("Loi","Loi xu ly background:"+er.toString());
        }
        return null;
    }
}
