package com.example.zhi.view;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Author: Eron
 * Date: 2016/3/17
 * Time: 22:05
 */
public class QuickToast extends Toast {
    /**
     * Construct an empty Toast object.  You must call {@link #setView} before you
     * can call {@link #show}.
     *
     * @param context The context to use.  Usually your {@link Application}
     *                or {@link Activity} object.
     */
    public QuickToast(Context context) {
        super(context);
    }

    private void showMyToast(Toast toast, int cnt) {
        if (cnt < 0)
            return;
        toast.show();
        execToast(toast, cnt);
    }

    private void execToast(final Toast toast, final int cnt) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                showMyToast(toast, cnt - 1);
            }
        }, 2000);
    }
}
