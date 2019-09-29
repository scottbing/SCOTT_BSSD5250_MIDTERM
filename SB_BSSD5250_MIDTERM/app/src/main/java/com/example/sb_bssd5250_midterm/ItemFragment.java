package com.example.sb_bssd5250_midterm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class ItemFragment extends Fragment {

    private static int EDITOR_REQUEST = 1;

    private static int NAME_ID = View.generateViewId();
    private static int DATE_ID = View.generateViewId();
    private static int DESC_ID = View.generateViewId();

    private TextView nameView;
    private TextView dateView;
    private TextView descView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        Context mContext= getActivity();
        LinearLayout linearLayout = new LinearLayout (mContext);
        linearLayout.setOrientation (LinearLayout. VERTICAL);
        linearLayout.setBackgroundColor(Color.WHITE);

        int size = 20;
        TextView nameView = new TextView(mContext);
        nameView.setTextSize(TypedValue. COMPLEX_UNIT_SP, size);
        TextView dateView = new TextView(mContext);
        dateView.setTextSize(TypedValue. COMPLEX_UNIT_SP, size);
        TextView descView = new TextView(mContext);
        descView.setTextSize (TypedValue. COMPLEX_UNIT_SP, size);

        nameView.setId(NAME_ID);
        dateView.setId(DATE_ID);
        descView.setId(DESC_ID);

        Button editButton = new Button(mContext);
        editButton.setText("Edit");
        //editButton.setOnClickListener(editClickedListener);

        linearLayout.addView(nameView);
        linearLayout.addView(dateView);
        linearLayout.addView(descView);
        linearLayout.addView(editButton);

        return linearLayout;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == EDITOR_REQUEST){
            if(resultCode == Activity.RESULT_OK) {
                nameView.setText((data.getStringExtra(ItemEditorDialog.EXTRA_NAME)));
            }
        }
    }

    public void showDialog() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        ItemEditorDialog itemEditorDialog = new ItemEditorDialog();
        itemEditorDialog.setTargetFragment(this, EDITOR_REQUEST);
        itemEditorDialog.show(fragmentManager, "DIALOG_NOT_EDITOR");
    }

    private View.OnClickListener editClickedListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //Log.d("ItemFragment", "edit clicked");
            /*FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            ItemEditorDialog itemEditorDialog = new ItemEditorDialog();
            itemEditorDialog.show(fragmentManager, "DIALOG_NOT_EDITOR");*/
            showDialog();
        }
    };
}