package com.lolodev.fancydialogslib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;

public class FancyProgressView extends FrameLayout {

    private CardView progressPrimaryBackground;
    private CardView progressSecondaryBackground;
    private AppCompatImageView progressLoadImage;
    private ProgressBar progressLoadBar;
    private AppCompatTextView progressLoadMessage;
    private TypedArray typedArray;

    @SuppressLint("InflateParams")
    public FancyProgressView(Context context) {
        super(context);
        addView(LayoutInflater.from(context).inflate(R.layout.progress_dialog_content, null));
        addInitItems();
    }

    @SuppressLint("InflateParams")
    public FancyProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        addView(LayoutInflater.from(context).inflate(R.layout.progress_dialog_content, null));
        this.typedArray = context.obtainStyledAttributes(attrs, R.styleable.FancyProgressView, 0, 0);
        addInitItems();
    }

    @SuppressLint("InflateParams")
    public FancyProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        addView(LayoutInflater.from(context).inflate(R.layout.progress_dialog_content, null));
        this.typedArray = context.obtainStyledAttributes(attrs, R.styleable.FancyProgressView, 0, 0);
        addInitItems();
    }

    @SuppressLint("InflateParams")
    public FancyProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        addView(LayoutInflater.from(context).inflate(R.layout.progress_dialog_content, null));
        this.typedArray = context.obtainStyledAttributes(attrs, R.styleable.FancyProgressView, 0, 0);
        addInitItems();
    }

    private void addInitItems() {
        this.progressPrimaryBackground = findViewById(R.id.progress_primary_background);
        this.progressSecondaryBackground = findViewById(R.id.progress_secondary_background);
        this.progressLoadImage = findViewById(R.id.progress_load_image);
        this.progressLoadBar = findViewById(R.id.progress_load_bar);
        this.progressLoadMessage = findViewById(R.id.progress_load_message);

        if (this.typedArray != null) {
            setPrimaryColor(this.typedArray.getColor(R.styleable.FancyProgressView_progressPrimaryBackground, Color.WHITE));
            setSecondaryColor(this.typedArray.getColor(R.styleable.FancyProgressView_progressSecondaryBackground, Color.WHITE));

            setResIcon(this.typedArray.getResourceId(R.styleable.FancyProgressView_progressLoadImage, 0));

            setMessage(this.typedArray.getString(R.styleable.FancyProgressView_progressLoadMessage));
            setMessageColor(this.typedArray.getColor(R.styleable.FancyProgressView_progressLoadMessageColor, Color.WHITE));
            setMessageTxtSize((int) this.typedArray.getDimension(R.styleable.FancyProgressView_progressLoadMessageSize, 14));

            setIndeterminateColor(this.typedArray.getColor(R.styleable.FancyProgressView_progressLoadBar, Color.BLUE));
        }
    }

    //-----------Backgrounds Part-------------
    public FancyProgressView setPrimaryColor(@ColorInt int primaryColor) {
        this.progressPrimaryBackground.setCardBackgroundColor(primaryColor);
        return this;
    }

    public FancyProgressView setSecondaryColor(@ColorInt int secondaryColor) {
        this.progressSecondaryBackground.setCardBackgroundColor(secondaryColor);
        return this;
    }


    //-----------Icon Part-------------
    public FancyProgressView setResIcon(@DrawableRes int resIcon) {
        this.progressLoadImage.setImageResource(resIcon);
        return this;
    }

    public FancyProgressView setResIcon(Bitmap resIcon) {
        this.progressLoadImage.setImageBitmap(resIcon);
        return this;
    }

    public FancyProgressView setResIcon(Drawable resIcon) {
        this.progressLoadImage.setImageDrawable(resIcon);
        return this;
    }

    //-----------Message Part-------------
    public FancyProgressView setMessage(String message) {
        this.progressLoadMessage.setText(message);
        return this;
    }

    public FancyProgressView setMessageColor(@ColorInt int messageColor) {
        this.progressLoadMessage.setTextColor(messageColor);
        return this;
    }

    public FancyProgressView setMessageTxtSize(int messageTxtSize) {
        this.progressLoadMessage.setTextSize(messageTxtSize);
        return this;
    }

    //-----------ProgressBar Part-------------
    public FancyProgressView setIndeterminateColor(@ColorInt int indeterminateColor) {
        ColorStateList colorStateList = ColorStateList.valueOf(indeterminateColor);
        progressLoadBar.setProgressTintList(colorStateList);
        progressLoadBar.setSecondaryProgressTintList(colorStateList);
        progressLoadBar.setIndeterminateTintList(colorStateList);
        return this;
    }
}
