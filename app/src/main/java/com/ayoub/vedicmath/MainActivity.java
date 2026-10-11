package com.ayoub.vedicmath;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.ValueCallback;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.webkit.WebViewAssetLoader;
import androidx.webkit.WebViewClientCompat;

public class MainActivity extends AppCompatActivity {

    private WebView mWebView;
    private WebViewAssetLoader mAssetLoader;
    private long mLastBackPressTime = 0;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Configure Immersive Dark System Bars
        setupWindowDecor();

        // Initialize WebView
        mWebView = new WebView(this);
        mWebView.setBackgroundColor(Color.parseColor("#09090b"));
        setContentView(mWebView);

        // Asset Loader: Serves assets securely over https://appassets.androidplatform.net/assets/
        mAssetLoader = new WebViewAssetLoader.Builder()
                .setDomain("appassets.androidplatform.net")
                .addPathHandler("/assets/", new WebViewAssetLoader.AssetsPathHandler(this))
                .build();

        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setAllowFileAccess(false);
        settings.setAllowContentAccess(false);
        settings.setMediaPlaybackRequiresUserGesture(false);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setMixedContentMode(WebSettings.MIXED_CONTENT_NEVER_ALLOW);

        // Native Bridge
        mWebView.addJavascriptInterface(new WebAppInterface(this), "AndroidBridge");

        mWebView.setWebViewClient(new WebViewClientCompat() {
            @Nullable
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                return mAssetLoader.shouldInterceptRequest(request.getUrl());
            }
        });

        // Load entry point
        mWebView.loadUrl("https://appassets.androidplatform.net/assets/index.html");
    }

    private void setupWindowDecor() {
        Window window = getWindow();
        int darkBg = Color.parseColor("#09090b");
        window.setStatusBarColor(darkBg);
        window.setNavigationBarColor(darkBg);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(true);
        } else {
            View decorView = window.getDecorView();
            int flags = decorView.getSystemUiVisibility();
            // Clear light bar flags to keep status bar text light (white) on dark background
            flags &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                flags &= ~View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
            }
            decorView.setSystemUiVisibility(flags);
        }
    }

    @Override
    public void onBackPressed() {
        if (mWebView != null) {
            mWebView.evaluateJavascript("window.handleAndroidBack ? window.handleAndroidBack() : 'exit'", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    if ("\"handled\"".equals(value)) {
                        // JavaScript handled navigating back inside the app
                        return;
                    }

                    // Otherwise, double press to exit
                    long now = System.currentTimeMillis();
                    if (now - mLastBackPressTime < 2000) {
                        MainActivity.super.onBackPressed();
                    } else {
                        mLastBackPressTime = now;
                        Toast.makeText(MainActivity.this, "Press back again to exit", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mWebView != null) mWebView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mWebView != null) mWebView.onPause();
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }
}
