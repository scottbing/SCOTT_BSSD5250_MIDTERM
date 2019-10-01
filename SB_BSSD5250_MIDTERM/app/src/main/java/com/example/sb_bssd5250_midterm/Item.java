package com.example.sb_bssd5250_midterm;

import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;


public class Item {

    private String caption;
    private int item;
    private String imageFileName;

    public Item() {
        // Initialize item
        this.item = 0;
    }

    public String getCaption() {
        return caption;
    }

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public JSONObject toJSON()
            throws JSONException {
        JSONObject jsonObject	= new JSONObject();
        jsonObject.put( "caption",  caption);
        jsonObject.put("item", item);
        return jsonObject;
    }

}


