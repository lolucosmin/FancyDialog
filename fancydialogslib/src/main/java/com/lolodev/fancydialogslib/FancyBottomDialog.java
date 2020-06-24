package com.lolodev.fancydialogslib;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

@SuppressWarnings({"UnusedReturnValue", "unused", "FieldCanBeLocal", "ConstantConditions", "rawtypes"})
public class FancyBottomDialog extends BottomSheetDialogFragment {

    private int backgroundNavigationBarColor = Color.BLACK;
    private int backgroundColor = Color.WHITE;
    private boolean backgroundCorners = true;

    private int iconRes;
    private Drawable iconDrawable;
    private Bitmap iconBitmap;
    private int iconBackground = Color.WHITE;

    private String dialogTitleValue;
    private int dialogTitleTextColor = Color.BLACK;
    private int dialogTitleTxtSize = 18;

    private String dialogMessageValue;
    private int dialogMessageColor = Color.BLACK;
    private int dialogMessageTxtSize = 14;

    private String dialogPositiveButtonValue;
    private String dialogNeutralButtonValue;
    private String dialogNegativeButtonValue;

    private boolean buttonsTextAllCaps;
    private int buttonPositiveTxtColor = Color.BLACK;
    private int buttonNeutralTxtColor = Color.BLACK;
    private int buttonNegativeTxtColor = Color.BLACK;
    private int buttonsTxtSize = 16;

    private OnPositiveClickListener dialogPositiveButtonCallback;
    private OnNeutralClickListener dialogNeutralButtonCallback;
    private OnNegativeClickListener dialogNegativeButtonCallback;

    private boolean isCancelable = true;
    private boolean dismissAfterActionWasPress = true;
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
    private LinearLayoutCompat dialogParent;

    public static FancyBottomDialog newInstance() {
        return new FancyBottomDialog();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.AppDialogSheetTheme);
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
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setNavigationBarColor(this.backgroundNavigationBarColor);
        }

        //others
        setCancelable(this.isCancelable);
    }

    private String universalTag = FancyBottomDialog.this.getClass().getSimpleName();

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
            Log.e(FancyBottomDialog.this.getClass().getSimpleName(), ex.getMessage());
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        try {
            dismissAllowingStateLoss();
        } catch (Exception ex) {
            Log.e(FancyBottomDialog.this.getClass().getSimpleName(), ex.getMessage());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            dismissAllowingStateLoss();
        } catch (Exception ex) {
            Log.e(FancyBottomDialog.this.getClass().getSimpleName(), ex.getMessage());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            dismissAllowingStateLoss();
        } catch (Exception ex) {
            Log.e(FancyBottomDialog.this.getClass().getSimpleName(), ex.getMessage());
        }
    }

    private void addInitItems(@NonNull View view) {
        this.dialogParent = view.findViewById(R.id.dialog_parent);

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
        //background
        if (this.backgroundCorners) {
            Drawable unwrappedDrawable = AppCompatResources.getDrawable(getContext(), R.drawable.dialog_sheet_round_background);
            Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
            DrawableCompat.setTint(wrappedDrawable, this.backgroundColor);
            this.dialogParent.setBackground(wrappedDrawable);
        } else {
            Drawable unwrappedDrawable = AppCompatResources.getDrawable(getContext(), R.drawable.dialog_sheet_background);
            Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
            DrawableCompat.setTint(wrappedDrawable, this.backgroundColor);
            this.dialogParent.setBackground(wrappedDrawable);
        }

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
        this.dialogTitle.setTextSize(this.dialogTitleTxtSize > 0 ? this.dialogTitleTxtSize : 18);

        //message
        if (TextUtils.isEmpty(this.dialogMessageValue)) {
            this.dialogMessage.setVisibility(View.GONE);
        } else {
            this.dialogMessage.setVisibility(View.VISIBLE);
            this.dialogMessage.setText(this.dialogMessageValue);
            this.dialogMessage.setTextColor(this.dialogMessageColor);
        }
        this.dialogMessage.setTextSize(this.dialogMessageTxtSize > 0 ? this.dialogMessageTxtSize : 14);

        //positive button
        this.dialogPositiveButton.setText(this.dialogPositiveButtonValue != null ? this.dialogPositiveButtonValue : "");
        this.dialogPositiveButton.setVisibility(!TextUtils.isEmpty(this.dialogPositiveButtonValue) ? View.VISIBLE : View.GONE);
        this.dialogPositiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dismissAfterActionWasPress) {
                    dismissAllowingStateLoss();
                }

                if (dialogPositiveButtonCallback != null) {
                    dialogPositiveButtonCallback.onClick(v);
                }
            }
        });
        this.dialogPositiveButton.setTextColor(this.buttonPositiveTxtColor);


        //neutral button
        this.dialogNeutralButton.setText(this.dialogNeutralButtonValue != null ? this.dialogNeutralButtonValue : "");
        this.dialogNeutralButton.setVisibility(!TextUtils.isEmpty(this.dialogNeutralButtonValue) ? View.VISIBLE : View.GONE);
        this.dialogNeutralButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dismissAfterActionWasPress) {
                    dismissAllowingStateLoss();
                }

                if (dialogNeutralButtonCallback != null) {
                    dialogNeutralButtonCallback.onClick(v);
                }
            }
        });
        this.dialogNeutralButton.setTextColor(this.buttonNeutralTxtColor);

        //negative button
        this.dialogNegativeButton.setText(this.dialogNegativeButtonValue != null ? this.dialogNegativeButtonValue : "");
        this.dialogNegativeButton.setVisibility(!TextUtils.isEmpty(this.dialogNegativeButtonValue) ? View.VISIBLE : View.GONE);
        this.dialogNegativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dismissAfterActionWasPress) {
                    dismissAllowingStateLoss();
                }

                if (dialogNegativeButtonCallback != null) {
                    dialogNegativeButtonCallback.onClick(v);
                }
            }
        });
        this.dialogNegativeButton.setTextColor(this.buttonNegativeTxtColor);

        this.dialogPositiveButton.setTextSize(this.buttonsTxtSize > 0 ? this.buttonsTxtSize : 16);
        this.dialogNeutralButton.setTextSize(this.buttonsTxtSize > 0 ? this.buttonsTxtSize : 16);
        this.dialogNegativeButton.setTextSize(this.buttonsTxtSize > 0 ? this.buttonsTxtSize : 16);

        if (this.customView != null) {
            if (this.customView.getParent() != null) {
                ((ViewGroup) this.customView.getParent()).removeView(this.customView);
            }
            this.dialogCustomViewContainer.removeAllViews();
            this.dialogCustomViewContainer.addView(this.customView);
            this.dialogCustomViewContainer.setVisibility(View.VISIBLE);
        } else {
            this.dialogCustomViewContainer.removeAllViews();
            this.dialogCustomViewContainer.setVisibility(View.VISIBLE);
        }
    }

    public void setDismissAfterActionWasPress(boolean dismissAfterActionWasPress) {
        this.dismissAfterActionWasPress = dismissAfterActionWasPress;
    }

    //----------------Icon Part--------------------

    public FancyBottomDialog setIcon(@DrawableRes int icon, int color) {
        this.iconRes = icon;
        this.iconBackground = color;
        return this;
    }

    public FancyBottomDialog setIcon(@Nullable Drawable icon, int color) {
        this.iconDrawable = icon;
        this.iconBackground = color;
        return this;
    }

    public FancyBottomDialog setIcon(@Nullable Bitmap icon, int color) {
        this.iconBitmap = icon;
        this.iconBackground = color;
        return this;
    }

    //----------------Title Part--------------------

    public FancyBottomDialog setTitle(String title) {
        this.dialogTitleValue = title;
        return this;
    }

    public FancyBottomDialog setTitleTextColor(@ColorInt int titleTextColor) {
        this.dialogTitleTextColor = titleTextColor;
        return this;
    }

    public FancyBottomDialog setTitleTxtSize(int size) {
        this.dialogTitleTxtSize = size;
        return this;
    }

    //----------------Message Part--------------------

    public FancyBottomDialog setMessage(String message) {
        this.dialogMessageValue = message;
        return this;
    }

    public FancyBottomDialog setMessageTextColor(@ColorInt int messageTextColor) {
        this.dialogMessageColor = messageTextColor;
        return this;
    }

    public FancyBottomDialog setMessageTxtSize(int size) {
        this.dialogMessageTxtSize = size;
        return this;
    }

    //----------------Positive Button Part--------------------

    public FancyBottomDialog setPositiveButton(String text, OnPositiveClickListener onPositiveClickListener) {
        this.dialogPositiveButtonValue = text;
        this.dialogPositiveButtonCallback = onPositiveClickListener;
        return this;
    }

    //----------------Neutral Button Part--------------------

    public FancyBottomDialog setNeutralButton(String text, OnNeutralClickListener onNegativeClickListener) {
        this.dialogNeutralButtonValue = text;
        this.dialogNeutralButtonCallback = onNegativeClickListener;
        return this;
    }

    //----------------Negative Button Part--------------------

    public FancyBottomDialog setNegativeButton(String text, OnNegativeClickListener onNegativeClickListener) {
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

    public FancyBottomDialog setButtonsTextAllCaps(boolean textAllCaps) {
        this.buttonsTextAllCaps = textAllCaps;
        return this;
    }

    public FancyBottomDialog setButtonPositiveTxtColor(@ColorInt int color) {
        this.buttonPositiveTxtColor = color;
        return this;
    }

    public FancyBottomDialog setButtonNeutralTxtColor(@ColorInt int color) {
        this.buttonNeutralTxtColor = color;
        return this;
    }

    public FancyBottomDialog setButtonNegativeTxtColor(@ColorInt int color) {
        this.buttonNegativeTxtColor = color;
        return this;
    }

    public FancyBottomDialog setButtonsTxtSize(int size) {
        this.buttonsTxtSize = size;
        return this;
    }

    //----------------Background Functions Part--------------------

    public FancyBottomDialog setBackgroundColor(@ColorInt int backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public FancyBottomDialog setBackgroundRoundCorners(boolean backgroundCorners) {
        this.backgroundCorners = backgroundCorners;
        return this;
    }

    //----------------Navigation Bar Functions Part--------------------

    public FancyBottomDialog setBackgroundNavigationBar(@ColorInt int backgroundNavigationBarColor) {
        this.backgroundNavigationBarColor = backgroundNavigationBarColor;
        return this;
    }

    //----------------Custom View Part--------------------

    public FancyBottomDialog setView(View view) {
        this.customView = view;
        return this;
    }

    //----------------Others Functions Part--------------------

    public FancyBottomDialog setIsCancelable(boolean cancelable) {
        this.isCancelable = cancelable;
        return this;
    }

    //-----------Getters-------------


    public View getCustomView() {
        return customView;
    }

    public CardView getDialogIconParent() {
        return dialogIconParent;
    }

    public AppCompatImageView getDialogIcon() {
        return dialogIcon;
    }

    public AppCompatTextView getDialogTitle() {
        return dialogTitle;
    }

    public LinearLayoutCompat getDialogMessageParent() {
        return dialogMessageParent;
    }

    public AppCompatTextView getDialogMessage() {
        return dialogMessage;
    }

    public AppCompatButton getDialogNeutralButton() {
        return dialogNeutralButton;
    }

    public AppCompatButton getDialogNegativeButton() {
        return dialogNegativeButton;
    }

    public AppCompatButton getDialogPositiveButton() {
        return dialogPositiveButton;
    }

    public LinearLayoutCompat getDialogCustomViewContainer() {
        return dialogCustomViewContainer;
    }

    public LinearLayoutCompat getDialogParent() {
        return dialogParent;
    }
}