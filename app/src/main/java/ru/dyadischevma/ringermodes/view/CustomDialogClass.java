package ru.dyadischevma.ringermodes.view;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import ru.dyadischevma.ringermodes.R;

public class CustomDialogClass extends Dialog implements
        android.view.View.OnClickListener  {
    public Activity c;
    public Dialog d;

    public CustomDialogClass(Activity a) {
        super(a);
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.choose_time_item);
    }

    @Override
    public void onClick(View v) {
        dismiss();
    }
}
