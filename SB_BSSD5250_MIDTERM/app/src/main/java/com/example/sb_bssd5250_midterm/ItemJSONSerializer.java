package com.example.sb_bssd5250_midterm;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ItemJSONSerializer {

    private Context mContext;
    private String mFilename;

    // ItemJSONSerializer Constructor
    public ItemJSONSerializer(Context c, String filename) {
        mContext = c;
        mFilename = filename;
    }

    public void saveItems(ArrayList<Item> items)
            throws JSONException, IOException {
        //Build an array in JSON
        JSONArray jsonArray = new JSONArray();
        for (Item n : items) {
            //use  the toJSON function we wrote on each item
            jsonArray.put(n.toJSON());
        }
        //JSONArray of all items built
        try {
            writeDataFile(jsonArray);
        } catch (Exception e) {
            //catch any errors that occur from try and throw them back to whoever called this
            throw e;
        }
    }

    private void writeDataFile(JSONArray jsonArray)
            throws JSONException, IOException {
        ArrayList<Item> items = ItemsData.getInstance(mContext).getItemList();
        File file = new File(mContext.getFilesDir(), mFilename);
        FileOutputStream fileOutputStream;

        try {    //try to open the  file for writing
            fileOutputStream = new FileOutputStream(file);
            //Turn the first item's name to bytes   in utf8 format and put in file
            fileOutputStream.write(jsonArray.toString().getBytes("UTF-8"));
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            //catch any errors that occur from try and throw them back to whoever called this
            throw e;
        }
    }

}
