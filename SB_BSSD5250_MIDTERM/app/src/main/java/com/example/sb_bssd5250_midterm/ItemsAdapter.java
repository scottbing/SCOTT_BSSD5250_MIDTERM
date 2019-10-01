package com.example.sb_bssd5250_midterm;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class
ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    private static int EDITOR_REQUEST = 1;

    private static int CAPTION_ID = View.generateViewId();
    private static int IMAGE_ID = View.generateViewId();

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView captionView;
        ImageView imageView;

        int position = -1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            captionView = itemView.findViewById(CAPTION_ID);
            imageView = itemView.findViewById(IMAGE_ID);

        }

        public void showDialog() {
            Bundle arguments = new Bundle();
            arguments.putInt("position", position);

            FragmentManager fragmentManager = ((FragmentActivity)getmContext()).getSupportFragmentManager();
            ItemEditorDialog itemEditorDialog = new ItemEditorDialog();
            itemEditorDialog.setArguments(arguments);
            itemEditorDialog.show(fragmentManager, "DIALOG_NOTE_EDITOR");
        }

        private View.OnClickListener editClickedListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d("ItemFragment", "edit clicked");
                showDialog();
            }
        };

        private View.OnClickListener deleteClickedListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("ItemFragment", "delete clicked");
                ItemsData.getInstance(mContext).getItemList().remove(position);   // remove from list
                ItemsData.getInstance(null).refreshItems();
            }
        };
    }

    private Context mContext;

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout linearLayout = new LinearLayout (mContext);
        linearLayout.setOrientation (LinearLayout. VERTICAL);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        linearLayout.setBackgroundColor(Color.WHITE);

        int size = 20;
        TextView captionView = new TextView(mContext);
        captionView.setTextSize(TypedValue. COMPLEX_UNIT_SP, size);
        ImageView imageView = new ImageView(mContext);

        captionView.setId(CAPTION_ID);
        imageView.setId(IMAGE_ID);

        Button editButton = new Button(mContext);
        editButton.setText("Edit");

        Button deleteButton = new Button(mContext);
        deleteButton.setText("Delete");

        LinearLayout buttonLL =  new  LinearLayout(getmContext());
        buttonLL.setOrientation(LinearLayout.HORIZONTAL);
        buttonLL.addView(editButton);
        buttonLL.addView(deleteButton);
        linearLayout.addView(captionView);
        linearLayout.addView(imageView);
        linearLayout.addView(buttonLL);

        ViewHolder vh = new ViewHolder(linearLayout);
        editButton.setOnClickListener(vh.editClickedListener);
        deleteButton.setOnClickListener(vh.deleteClickedListener);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //get the singleton of all items data, get teh array in it, get the item at position
        Item item = ItemsData.getInstance(mContext).getItemList().get(position);
        //the holder the adapter already made for this item is now populated
        holder.captionView.setText(item.getCaption());
        if (item.getItem()==0) {
            File imageFile = new File(item.getImageFileName());
            if(imageFile.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                holder.imageView.setImageBitmap(myBitmap);

            }
        } else {
            holder.imageView.setImageResource(item.getItem());
        }
        holder.position = position;

    }

    @Override
    public int getItemCount() {
        //return number of items in the ItemsData list so the adapter can create its internal for loop
        return ItemsData.getInstance(mContext).getItemList().size();
    }
}
