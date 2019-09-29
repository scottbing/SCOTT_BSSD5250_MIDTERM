package com.example.sb_bssd5250_midterm;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.Date;

public class Item {

    private String caption;
    private int item;

    public Item() {
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


    public JSONObject toJSON()
            throws JSONException {
        JSONObject jsonObject	= new JSONObject();
        jsonObject.put( "caption",  caption);
        jsonObject.put("item", item);
        return jsonObject;
    }

}


