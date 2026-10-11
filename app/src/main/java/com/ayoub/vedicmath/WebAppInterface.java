package com.ayoub.vedicmath;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

public class WebAppInterface {
    private final Context mContext;
    private final Vibrator mVibrator;

    public WebAppInterface(Context context) {
        this.mContext = context;
        this.mVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    @JavascriptInterface
    public void vibrate(long milliseconds) {
        if (mVibrator == null || !mVibrator.hasVibrator()) return;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mVibrator.vibrate(VibrationEffect.createOneShot(milliseconds, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                mVibrator.vibrate(milliseconds);
            }
        } catch (Exception ignored) {}
    }

    @JavascriptInterface
    public void showToast(String message) {
        if (message == null || message.trim().isEmpty()) return;
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public String getAppVersion() {
        return "1.0.0";
    }
}
