package com.example.android_baitap3;

import android.graphics.drawable.Icon;

import java.io.Serializable;
import java.net.URL;

public class Item implements Serializable {
    private String itemName;
    private String itemDescription;
    private String itemIcon;
    private String itemUrl;

    public Item()
    {

    }

    public Item(String itemName, String itemDescription, String itemIcon, String itemUrl) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemIcon = itemIcon;
        this.itemUrl = itemUrl;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemIcon() {
        return itemIcon;
    }

    public void setItemIcon(String itemIcon) {
        this.itemIcon = itemIcon;
    }

    public String getItemUrl() {
        return itemUrl;
    }

    public void setItemUrl(String itemUrl) {
        this.itemUrl = itemUrl;
    }
}
