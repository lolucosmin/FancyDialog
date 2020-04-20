package com.lolodev.fancydialogslib;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

@SuppressWarnings({"ConstantConditions", "unused"})
public class FancyProgressDialog extends DialogFragment {

    private int primaryColor = Color.WHITE;
    private int secondaryColor = Color.WHITE;
    private int resIcon = 0;
    private Bitmap resIconAsBitmap;
    private String message;
    private int messageColor = Color.WHITE;
    private int messageTxtSize = 16;
    private int indeterminateColor = Color.MAGENTA;
    private static FancyProgressDialog currentInstance;

    public static FancyProgressDialog getInstance() {
        if (currentInstance == null) {
            currentInstance = new FancyProgressDialog();
            return currentInstance;
        } else {
            return currentInstance;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppProgressDialogheme);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.progress_dialog_content, container);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        view.setMinimumWidth(width);
        view.setMinimumHeight(height);
        setCancelable(false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CardView progress_primary_background = view.findViewById(R.id.progress_primary_background);
        CardView progress_secondary_background = view.findViewById(R.id.progress_secondary_background);
        AppCompatImageView progress_load_image = view.findViewById(R.id.progress_load_image);
        ProgressBar progress_load_bar = view.findViewById(R.id.progress_load_bar);
        AppCompatTextView progress_load_message = view.findViewById(R.id.progress_load_message);

        //backgrounds part
        progress_primary_background.setCardBackgroundColor(this.primaryColor);
        progress_secondary_background.setCardBackgroundColor(this.secondaryColor);

        //icon part
        if (this.resIconAsBitmap != null) {
            progress_load_image.setImageBitmap(this.resIconAsBitmap);

        } else if (this.resIcon != 0) {
            progress_load_image.setImageResource(this.resIcon);
        }

        //progress bar
        ColorStateList colorStateList = ColorStateList.valueOf(this.indeterminateColor);
        progress_load_bar.setProgressTintList(colorStateList);
        progress_load_bar.setSecondaryProgressTintList(colorStateList);
        progress_load_bar.setIndeterminateTintList(colorStateList);

        //message part
        if (!TextUtils.isEmpty(this.message)) {
            progress_load_message.setText(String.valueOf(this.message));
            progress_load_message.setTextColor(this.messageColor);
            progress_load_message.setVisibility(View.VISIBLE);
            progress_load_message.setTextSize(this.messageTxtSize > 0 ? this.messageTxtSize : 16);
        } else {
            progress_load_message.setVisibility(View.GONE);
        }
    }

    private String universalTag = FancyProgressDialog.this.getClass().getSimpleName();

    @Override
    public void show(@NonNull FragmentManager manager, @Nullable String tag) {
        try {
            Fragment fragment = manager.findFragmentByTag(this.universalTag);
            if (fragment == null) {
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                fragmentTransaction.add(this, this.universalTag);
                fragmentTransaction.commitAllowingStateLoss();
            }
        } catch (Exception ex) {
            Log.e(FancyProgressDialog.this.getClass().getSimpleName(), ex.getMessage());
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        try {
            dismissAllowingStateLoss();
        } catch (Exception ex) {
            Log.e(FancyProgressDialog.this.getClass().getSimpleName(), ex.getMessage());
        }
        currentInstance = null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            dismissAllowingStateLoss();
        } catch (Exception ex) {
            Log.e(FancyProgressDialog.this.getClass().getSimpleName(), ex.getMessage());
        }
        currentInstance = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            dismissAllowingStateLoss();
        } catch (Exception ex) {
            Log.e(FancyProgressDialog.this.getClass().getSimpleName(), ex.getMessage());
        }
        currentInstance = null;
    }

    public FancyProgressDialog setPrimaryColor(@ColorInt int primaryColor) {
        this.primaryColor = primaryColor;
        return this;
    }

    public FancyProgressDialog setSecondaryColor(@ColorInt int secondaryColor) {
        this.secondaryColor = secondaryColor;
        return this;
    }

    public FancyProgressDialog setResIcon(int resIcon) {
        this.resIcon = resIcon;
        return this;
    }

    public FancyProgressDialog setResIconAsBitmap(Bitmap resIconAsBitmap) {
        this.resIconAsBitmap = resIconAsBitmap;
        return this;
    }

    public FancyProgressDialog setMessage(String message) {
        this.message = message;
        return this;
    }

    public FancyProgressDialog setMessageColor(@ColorInt int messageColor) {
        this.messageColor = messageColor;
        return this;
    }

    public FancyProgressDialog setMessageTxtSize(int messageTxtSize) {
        this.messageTxtSize = messageTxtSize;
        return this;
    }

    public FancyProgressDialog setIndeterminateColor(@ColorInt int indeterminateColor) {
        this.indeterminateColor = indeterminateColor;
        return this;
    }
}