package com.lolodev.fancydialogslib;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppProgressDialogTheme);
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
        FancyProgressView fancyProgressView = view.findViewById(R.id.fancy_progress_view);

        //backgrounds part
        fancyProgressView.setPrimaryColor(this.primaryColor);
        fancyProgressView.setSecondaryColor(this.secondaryColor);

        //icon part
        if (this.resIconAsBitmap != null) {
            fancyProgressView.setResIcon(this.resIconAsBitmap);

        } else if (this.resIcon != 0) {
            fancyProgressView.setResIcon(this.resIcon);
        }

        //progress bar
        fancyProgressView.setIndeterminateColor(this.indeterminateColor);

        //message part
        fancyProgressView.setMessage(this.message);
        fancyProgressView.setMessageColor(this.messageColor);
        fancyProgressView.setMessageTxtSize(this.messageTxtSize);
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