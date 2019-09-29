package com.example.sb_bssd5250_midterm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class ItemEditorDialog extends DialogFragment {

    public static final String EXTRA_NAME = "com.example.sb_bssd5250_midterm";

    private EditText captionText;
    private ImageView itemImage;

    private int position;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);

        position = getArguments().getInt( "position");
        Item item = ItemsData.getInstance(null).getItemList().get(position);

        captionText = new EditText(getActivity());
        if (item.getCaption() == "Caption" ) {
            captionText.setHint("Enter a Caption");
            captionText.setHintTextColor(Color.parseColor("#03fcf0"));
        }

        itemImage = new ImageView(getActivity());

        // this is taken from: https://stackoverflow.com/questions/12876624/multiple-edittext-objects-in-alertdialog
        LinearLayout alertLayout = new LinearLayout(getContext());
        alertLayout.setOrientation(LinearLayout.VERTICAL);
        alertLayout.setId(View.generateViewId());

        alertLayout.addView(captionText);

        return new AlertDialog.Builder(getActivity())
                .setTitle("Modify Caption")
                .setView(alertLayout)
                .setPositiveButton("Done", doneClickedListener)
                .setNegativeButton("Cancel", null)
                .create();

    }

    private void sendResult(int resultCode) {
        if (getTargetFragment() == null) {
            // Target not set up, do nothing
        } else {
            Intent i = new Intent();
            i.putExtra( EXTRA_NAME, captionText.getText().toString());
            getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
        }
    }

    private DialogInterface.OnClickListener doneClickedListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            //Log.d("itemEditorDialog", captionText.getText().toString());
            ItemsData.getInstance(null).getItemList().get(position).setCaption(captionText.getText().toString());
            ItemsData.getInstance(null).getItemList().get(position).setItem(itemImage);
            ItemsData.getInstance(null).refreshItems();
        }
    };
}
