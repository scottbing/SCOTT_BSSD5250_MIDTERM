package com.example.sb_bssd5250_midterm;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.graphics.drawable.Drawable;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  implements ItemsData.ItemsDataUpdatedListener {

    private RecyclerView itemsRV;
    private ItemsAdapter itemsAdapter;
    private int i = 1;
    private static String LOGID = "MainActivity";
    private static int RESULT_LOAD_IMAGE = 1;

    @Override
    public void updateItemsDependents() {
        ItemsAdapter itemsAdapter = new ItemsAdapter();
        itemsAdapter.setmContext(this);
        itemsRV.swapAdapter(itemsAdapter, true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)	{
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        // setup an array of items
        ArrayList<Item> itemsArray = ItemsData.getInstance(this).getItemList();

        ItemsData.getInstance(this).setListener(this);
        makeDummyData(3);
        itemsRV	= new RecyclerView(  this);
        itemsRV.setBackgroundColor(Color.RED);

        itemsAdapter = new ItemsAdapter();
        itemsAdapter.setmContext(this);
        itemsRV.setAdapter(itemsAdapter);
        itemsRV.setLayoutManager(new LinearLayoutManager( this));

        Button addItemButton = new Button( this);
        addItemButton.setText("+");
        addItemButton.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,  ViewGroup.LayoutParams.WRAP_CONTENT));
        //make this button add a new item when clicked
        addItemButton.setOnClickListener(addClickedListener);

        Button saveItemButton = new Button( this);
        saveItemButton.setText("Save");
        saveItemButton.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,  ViewGroup.LayoutParams.WRAP_CONTENT));
        //make this button save a new item when clicked
        saveItemButton.setOnClickListener(saveClickedListener);

        // got this from the instructor
        LinearLayout linearLayout = new LinearLayout( this);
        LinearLayout buttonLL =  new  LinearLayout(this);
        buttonLL.setOrientation(LinearLayout.HORIZONTAL);
        buttonLL.addView(addItemButton);
        buttonLL.addView(saveItemButton);

        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,  ViewGroup.LayoutParams.MATCH_PARENT));
        linearLayout.addView(buttonLL);
        linearLayout.addView(itemsRV);

        setContentView(linearLayout);

    }

    private void makeDummyData(int number) {
        // set up array for images
        int aDrawable[] = new int[3];
        aDrawable[0] = R.drawable.image01;
        aDrawable[1] = R.drawable.image02;
        aDrawable[2] = R.drawable.image03;

        // Load item contents into the items
        for (int j=0; j<aDrawable.length; j++) {
            Item item = new Item();
            item.setCaption("Caption " + String.valueOf(j+1));
            item.setItem(aDrawable[j]);
            ItemsData.getInstance(this).getItemList().add(item);
        }
    }

    private View.OnClickListener addClickedListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.d("addClick Listener", "add clicked");
            Item item = new Item();
            item.setCaption("Caption");

            // get image
            Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, RESULT_LOAD_IMAGE);
            //item.setItem("Item");
            ItemsData.getInstance(MainActivity.this).getItemList().add(item);
            ItemsData.getInstance(null).refreshItems();


        }
    };

    // load the image
    // https://stackoverflow.com/questions/27810945/select-video-from-gallery-cannot-find-symbol-result-load-image
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            // String picturePath contains the path of selected Image

            ImageView imageView = new ImageView(this);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }


    private View.OnClickListener saveClickedListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.d("saveClick Listener", "add clicked");
            //makeData();
            //writeDataFile();
            ItemJSONSerializer itemJSONSerializer = new ItemJSONSerializer( MainActivity.this, "items.json" );
            try {
                itemJSONSerializer.saveItems (ItemsData.getInstance(MainActivity.this).getItemList());
            }  catch (Exception e) {
                Log.d(LOGID, e.toString());
            }
            readDataFile("items json");


        }
    };

    private void makeData() {
        ArrayList<Item> items = ItemsData.getInstance(this).getItemList();
        for(int i=0;  i<10;  i++) {
            Item item = new Item();
            item.setCaption("Item	#" + i);
            //item.setItem("Item" + i);
            items.add(item);
        }
    }


    private void writeDataFile() {
        ArrayList<Item> items = ItemsData.getInstance(this).getItemList();
        String filename = "items.txt";
        File file = new File(getApplicationContext().getFilesDir(), filename);
        FileOutputStream fileOutputStream;
        try {    //try to open the  file for writing
            fileOutputStream = new FileOutputStream(file);
            //Turn the first item's name to bytes   in utf8 format and put in file
            fileOutputStream.write(items.get(0).toString().getBytes("UTF-8"));
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            //catch any errors that occur from try and throw them back to whoever called this
            Log.d(LOGID, e.toString());
        }
    }

    private void readDataFile(String filename) {

        File file = new File(getApplicationContext().getFilesDir(), filename);
        int length = (int) file.length();
        Log.d(LOGID, "File is bytes: " + String.valueOf(length));

        byte[] bytes = new byte[length]; //byte array to hold all read bytes

        FileInputStream fileInputStream;
        try { //try to open the file for reading
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytes);
            fileInputStream.close();
        } catch (Exception e) {//handle exception arising from above
            Log.d(LOGID, e.toString());
        }

        //Now try something different that also requires eception handling
        try {
            String s = new String(bytes, "UTF-8");
            Log.d(LOGID, s);
        } catch (Exception e) { //handle exception from string creation
            Log.d(LOGID, e.toString());
        }
    }

}
