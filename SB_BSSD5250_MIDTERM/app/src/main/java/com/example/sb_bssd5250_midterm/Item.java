package com.example.sb_bssd5250_midterm;

import android.media.Image;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.Date;

public class Item {

    private String caption;
    private ImageView item;

    public Item() {
    }

    public String getCaption() {
        return caption;
    }

    public ImageView getItem() {
        return item;
    }

    public void setItem(ImageView item) {
        this.item = item;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }


    public JSONObject toJSON()
            throws JSONException {
        JSONObject jsonObject	= new JSONObject();
        jsonObject.put( "caption",  caption);
        jsonObject.put("item", item);
        return jsonObject;
    }

}


