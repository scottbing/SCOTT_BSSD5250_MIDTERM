package com.example.sb_bssd5250_midterm;

import android.content.Context;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ItemsData {  //Singleton class of all not data

    public interface ItemsDataUpdatedListener {
        public void updateItemsDependents();
    }
    public ItemsDataUpdatedListener listener;

    private ArrayList<Item> mItems;

    private static ItemsData sItemsData;
    private Context mAppContext;

    private ItemsData(Context context) {
        mAppContext = context;
        mItems = new ArrayList<Item>();
        listener = null;
    }

    public ArrayList<Item> getItemList() {
        return mItems;
    }
    public void setListener(ItemsDataUpdatedListener itemsDataUpdatedListener ) {
        listener = itemsDataUpdatedListener;
    }

    public static ItemsData getInstance(@Nullable Context c) {
        if (sItemsData == null) {
            sItemsData = new ItemsData(c.getApplicationContext());
        }
        return sItemsData;
    }

    public void refreshItems() {
        if (listener != null)
            listener.updateItemsDependents();
    }
}
