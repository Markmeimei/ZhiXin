package com.example.zhi.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ProgressBar;

/**
 * 带简单进度条的 WebView
 *
 * Author:Eron
 * Date: 2016/4/27 0027
 * Time: 14:38
 */
public class ProgressWebView extends WebView {

    private ProgressBar progressBar;

    public ProgressWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        progressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
        progressBar.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 15, 0, 0));
        addView(progressBar);
        setWebChromeClient(new WebChromeClient());
    }

    public class WebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                progressBar.setVisibility(GONE);
            } else {
                if (progressBar.getVisibility() == GONE)
                    progressBar.setVisibility(VISIBLE);
                progressBar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        LayoutParams lp = (LayoutParams) progressBar.getLayoutParams();
        lp.x = l;
        lp.y = t;
        progressBar.setLayoutParams(lp);
        super.onScrollChanged(l, t, oldl, oldt);
    }
}
