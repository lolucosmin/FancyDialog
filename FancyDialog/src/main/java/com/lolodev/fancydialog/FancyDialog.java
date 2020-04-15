package com.lolodev.fancydialog;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

@SuppressWarnings({"UnusedReturnValue", "unused", "FieldCanBeLocal", "ConstantConditions"})
public class FancyDialog extends BottomSheetDialogFragment {

    private int backgroundNavigationBarColor = Color.BLACK;
    private int backgroundColor = Color.WHITE;
    private boolean backgroundCorners = true;

    private int iconRes;
    private Drawable iconDrawable;
    private Bitmap iconBitmap;
    private int iconBackground = Color.WHITE;

    private String dialogTitleValue;
    private int dialogTitleTextColor = Color.BLACK;

    private String dialogMessageValue;
    private int dialogMessageColor = Color.BLACK;

    private String dialogPositiveButtonValue;
    private String dialogNeutralButtonValue;
    private String dialogNegativeButtonValue;

    private boolean buttonsTextAllCaps;
    private int buttonPositiveTxtColor = Color.BLACK;
    private int buttonNeutralTxtColor = Color.BLACK;
    private int buttonNegativeTxtColor = Color.BLACK;

    private OnPositiveClickListener dialogPositiveButtonCallback;
    private OnNeutralClickListener dialogNeutralButtonCallback;
    private OnNegativeClickListener dialogNegativeButtonCallback;

    private boolean isCancelable;

    private View customView;

    private CardView dialogIconParent;
    private AppCompatImageView dialogIcon;
    private AppCompatTextView dialogTitle;
    private LinearLayoutCompat dialogMessageParent;
    private AppCompatTextView dialogMessage;
    private AppCompatButton dialogNeutralButton;
    private AppCompatButton dialogNegativeButton;
    private AppCompatButton dialogPositiveButton;
    private LinearLayoutCompat dialogCustomViewContainer;

    public static FancyDialog newInstance() {
        return new FancyDialog();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogSheetTheme);
    }

    @Override
    public void setupDialog(@NonNull Dialog dialog, int style) {
        final View rootView = View.inflate(getContext(), R.layout.dialog_content, null);
        addInitItems(rootView);
        addOnPrepareItems(dialog);


        dialog.setContentView(rootView);
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) rootView.getParent()).getLayoutParams();
                CoordinatorLayout.Behavior behavior = params.getBehavior();
                if (behavior instanceof BottomSheetBehavior) {
                    ((BottomSheetBehavior) behavior).setPeekHeight(rootView.getHeight());
                }
            }
        });

        //background
        if (dialog.getWindow() != null) {
            FrameLayout bottomSheet = dialog.getWindow().findViewById(com.google.android.material.R.id.design_bottom_sheet);
            if (this.backgroundCorners) {
                bottomSheet.setBackgroundResource(R.drawable.dialog_sheet_round_background);
            } else {
                bottomSheet.setBackgroundResource(R.drawable.dialog_sheet_background);
            }
            dialog.getWindow().setNavigationBarColor(this.backgroundNavigationBarColor);
        }

        //others
        setCancelable(this.isCancelable);
    }

    @Override
    public void show(@NonNull FragmentManager manager, @Nullable String tag) {
        try {
            FragmentTransaction fragmentTransaction = manager.beginTransaction();
            fragmentTransaction.add(this, tag);
            fragmentTransaction.commitAllowingStateLoss();
        } catch (Exception ex) {
            Log.e(FancyDialog.this.getClass().getSimpleName(), ex.getMessage());
        }
    }

    private void addInitItems(@NonNull View view) {
        this.dialogIconParent = view.findViewById(R.id.dialog_icon_parent);
        this.dialogIcon = view.findViewById(R.id.dialog_icon);

        this.dialogTitle = view.findViewById(R.id.dialog_title);

        this.dialogMessageParent = view.findViewById(R.id.dialog_message_parent);
        this.dialogMessage = view.findViewById(R.id.dialog_message);

        this.dialogNeutralButton = view.findViewById(R.id.dialog_neutral_button);
        this.dialogNegativeButton = view.findViewById(R.id.dialog_negative_button);
        this.dialogPositiveButton = view.findViewById(R.id.dialog_positive_button);

        this.dialogCustomViewContainer = view.findViewById(R.id.dialog_custom_view_container);
    }

    private void addOnPrepareItems(Dialog dialog) {

        //icon
        if (this.iconRes != 0 || this.iconBitmap != null || this.iconDrawable != null) {
            this.dialogIconParent.setVisibility(View.VISIBLE);
            this.dialogIconParent.setCardBackgroundColor(this.iconBackground);
            if (this.iconDrawable != null) {
                this.dialogIcon.setImageDrawable(this.iconDrawable);

            } else if (this.iconBitmap != null) {
                this.dialogIcon.setImageBitmap(this.iconBitmap);

            } else {
                this.dialogIcon.setImageResource(this.iconRes);
            }
        } else {
            this.dialogIconParent.setVisibility(View.GONE);
        }

        //title
        if (TextUtils.isEmpty(this.dialogTitleValue)) {
            this.dialogTitle.setVisibility(View.GONE);
        } else {
            this.dialogTitle.setVisibility(View.VISIBLE);
            this.dialogTitle.setText(this.dialogTitleValue);
            this.dialogTitle.setTextColor(this.dialogTitleTextColor);
        }

        //message
        if (TextUtils.isEmpty(this.dialogMessageValue)) {
            this.dialogMessage.setVisibility(View.GONE);
        } else {
            this.dialogMessage.setVisibility(View.VISIBLE);
            this.dialogMessage.setText(this.dialogMessageValue);
            this.dialogMessage.setTextColor(this.dialogMessageColor);
        }

        //positive button
        this.dialogPositiveButton.setText(this.dialogPositiveButtonValue != null ? this.dialogPositiveButtonValue : "");
        this.dialogPositiveButton.setVisibility(!TextUtils.isEmpty(this.dialogPositiveButtonValue) ? View.VISIBLE : View.GONE);
        this.dialogPositiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissAllowingStateLoss();
                if (dialogPositiveButtonCallback != null) {
                    dialogPositiveButtonCallback.onClick(v);
                }
            }
        });


        //neutral button
        this.dialogNeutralButton.setText(this.dialogNeutralButtonValue != null ? this.dialogNeutralButtonValue : "");
        this.dialogNeutralButton.setVisibility(!TextUtils.isEmpty(this.dialogNeutralButtonValue) ? View.VISIBLE : View.GONE);
        this.dialogNeutralButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissAllowingStateLoss();
                if (dialogNeutralButtonCallback != null) {
                    dialogNeutralButtonCallback.onClick(v);
                }
            }
        });

        //negative button
        this.dialogNegativeButton.setText(this.dialogNegativeButtonValue != null ? this.dialogNegativeButtonValue : "");
        this.dialogNegativeButton.setVisibility(!TextUtils.isEmpty(this.dialogNegativeButtonValue) ? View.VISIBLE : View.GONE);
        this.dialogNegativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissAllowingStateLoss();
                if (dialogNegativeButtonCallback != null) {
                    dialogNegativeButtonCallback.onClick(v);
                }
            }
        });

        //custom view
        if (this.customView != null) {
            this.dialogCustomViewContainer.removeAllViews();
            this.dialogCustomViewContainer.addView(this.customView);
            this.dialogCustomViewContainer.setVisibility(View.VISIBLE);
        } else {
            this.dialogCustomViewContainer.removeAllViews();
            this.dialogCustomViewContainer.setVisibility(View.VISIBLE);
        }
    }

    //----------------Icon Part--------------------

    public FancyDialog setIcon(@DrawableRes int icon, int color) {
        this.iconRes = icon;
        this.iconBackground = color;
        return this;
    }

    public FancyDialog setIcon(@Nullable Drawable icon, int color) {
        this.iconDrawable = icon;
        this.iconBackground = color;
        return this;
    }

    public FancyDialog setIcon(@Nullable Bitmap icon, int color) {
        this.iconBitmap = icon;
        this.iconBackground = color;
        return this;
    }

    //----------------Title Part--------------------

    public FancyDialog setTitle(String title) {
        this.dialogTitleValue = title;
        return this;
    }

    public FancyDialog setTitleTextColor(@ColorInt int titleTextColor) {
        this.dialogTitleTextColor = titleTextColor;
        return this;
    }

    //----------------Message Part--------------------

    public FancyDialog setMessage(String message) {
        this.dialogMessageValue = message;
        return this;
    }

    public FancyDialog setMessageTextColor(@ColorInt int messageTextColor) {
        this.dialogMessageColor = messageTextColor;
        return this;
    }

    //----------------Positive Button Part--------------------

    public FancyDialog setPositiveButton(String text, OnPositiveClickListener onPositiveClickListener) {
        this.dialogPositiveButtonValue = text;
        this.dialogPositiveButtonCallback = onPositiveClickListener;
        return this;
    }

    //----------------Neutral Button Part--------------------

    public FancyDialog setNeutralButton(String text, OnNeutralClickListener onNegativeClickListener) {
        this.dialogNeutralButtonValue = text;
        this.dialogNeutralButtonCallback = onNegativeClickListener;
        return this;
    }

    //----------------Negative Button Part--------------------

    public FancyDialog setNegativeButton(String text, OnNegativeClickListener onNegativeClickListener) {
        this.dialogNegativeButtonValue = text;
        this.dialogNegativeButtonCallback = onNegativeClickListener;
        return this;
    }

    //----------------Buttons Callbacks-------------------------

    public interface OnPositiveClickListener {
        void onClick(View v);
    }

    public interface OnNegativeClickListener {
        void onClick(View v);
    }

    public interface OnNeutralClickListener {
        void onClick(View v);
    }

    //----------------Buttons Functions Part--------------------

    public FancyDialog setButtonsTextAllCaps(boolean textAllCaps) {
        this.buttonsTextAllCaps = textAllCaps;
        return this;
    }

    public FancyDialog setButtonPositiveTxtColor(@ColorInt int color) {
        this.buttonPositiveTxtColor = color;
        return this;
    }

    public FancyDialog setButtonNeutralTxtColor(@ColorInt int color) {
        this.buttonNeutralTxtColor = color;
        return this;
    }

    public FancyDialog setButtonNegativeTxtColor(@ColorInt int color) {
        this.buttonNegativeTxtColor = color;
        return this;
    }

    //----------------Background Functions Part--------------------

    public FancyDialog setBackgroundColor(@ColorInt int backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public FancyDialog setBackgroundRoundCorners(boolean backgroundCorners) {
        this.backgroundCorners = backgroundCorners;
        return this;
    }

    //----------------Navigation Bar Functions Part--------------------

    public FancyDialog setBackgroundNavigationBar(@ColorInt int backgroundNavigationBarColor) {
        this.backgroundNavigationBarColor = backgroundNavigationBarColor;
        return this;
    }

    //----------------Custom View Part--------------------

    public FancyDialog setView(View view) {
        this.customView = view;
        return this;
    }

    //----------------Others Functions Part--------------------

    public FancyDialog setIsCancelable(boolean cancelable) {
        this.isCancelable = cancelable;
        return this;
    }
}